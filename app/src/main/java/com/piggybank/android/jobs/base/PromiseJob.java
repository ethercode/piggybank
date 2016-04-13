package com.piggybank.android.jobs.base;

import com.path.android.jobqueue.Job;
import com.path.android.jobqueue.Params;
import com.piggybank.android.application.PiggyBankApplication;
import com.piggybank.android.future.Future;
import com.piggybank.android.future.FutureOnUiThread;
import com.piggybank.android.logging.PiggyBankLog;
import com.piggybank.android.util.TagUtil;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import de.greenrobot.event.EventBus;

public abstract class PromiseJob<T> extends Job {

    private static final Object jobHashMapLockObject = new Object();
    private static final ConcurrentHashMap<String, WeakReference<PromiseJob>> jobHashMap = new ConcurrentHashMap<>();
    protected T promiseResult;
    private String key;
    private List<Future<T>> futureList = new ArrayList<>();
    private boolean jobAllowedToExecute;

    protected PromiseJob(Params params) {
        super(params);
        throw new IllegalArgumentException("Wrong super called for single instance job.");
    }

    protected PromiseJob(Params params, String key, Future<T> future) {
        super(params);

        if (key == null) {
            key = TagUtil.longFrom(this) + " " + UUID.randomUUID().toString();
        }

        PromiseJob existingJob = jobForKey(key);
        PromiseJob jobToUse = existingJob != null ? existingJob : this;

        this.key = key;
        if (future != null) {
            jobToUse.futureList.add(future);
        }

        if (jobToUse == this) {
            synchronized (jobHashMapLockObject) {
                jobHashMap.put(key, new WeakReference<PromiseJob>(this));
            }
        } else {
            PiggyBankLog.i(TagUtil.shortFrom(this), String.format(Locale.getDefault(), "Job [%s] skipped; already running.", key));
        }

        jobAllowedToExecute = jobToUse == this;
    }

    private static PromiseJob jobForKey(String key) {
        WeakReference<PromiseJob> jobWeak = PromiseJob.jobHashMap.get(key);
        return (jobWeak != null) ? jobWeak.get() : null;
    }

    @Override
    public void onAdded() {
    }

    @Override
    public void onRun() throws Throwable {
        if (!jobAllowedToExecute) return;

        PiggyBankLog.i(TagUtil.shortFrom(this), String.format(Locale.getDefault(), "Job [%s] starting.", key));
        long startTime = System.currentTimeMillis();
        execute();
        long endTime = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        float totalTimeSeconds = totalTime / 1000.0f;
        PiggyBankLog.i(TagUtil.shortFrom(this), String.format(Locale.getDefault(), "Job [%s] completed after %1.2f sec.", key, totalTimeSeconds));

        fulfillPromises();

        synchronized (jobHashMapLockObject) {
            PromiseJob.jobHashMap.remove(key);
        }
    }

    @Override
    protected void onCancel() {
        synchronized (jobHashMapLockObject) {
            PromiseJob.jobHashMap.remove(key);
        }
    }

    @Override
    protected boolean shouldReRunOnThrowable(Throwable throwable) {
        return false;
    }

    protected abstract void execute();


    protected void fulfillPromises() {
        if (promiseResult == null) {
            PiggyBankLog.e(TagUtil.shortFrom(this), String.format(Locale.getDefault(), "Job [%s] completed without assigning promiseResult", key));
            return;
        }

        EventBus.getDefault().post(promiseResult);

        for (final Future<T> future : futureList) {
            if (future instanceof FutureOnUiThread) {
                if (future.isPending()) {
                    PiggyBankApplication.getInstance().getForegroundActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (future.isPending()) {
                                future.onPromiseFulfilled(PromiseJob.this.promiseResult);
                            }
                        }
                    });
                }
            } else {
                if (future.isPending()) {
                    future.onPromiseFulfilled(this.promiseResult);
                }
            }
        }
    }
}
