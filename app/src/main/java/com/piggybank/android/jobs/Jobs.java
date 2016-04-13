package com.piggybank.android.jobs;

import android.content.Context;

import com.path.android.jobqueue.JobManager;
import com.path.android.jobqueue.config.Configuration;
import com.piggybank.android.application.PiggyBankApplication;

public class Jobs {
    private static final Jobs ourInstance = new Jobs();
    private final JobManager jobManager;

    private Jobs() {
        Context context = PiggyBankApplication.getInstance();

        JobCustomLogger customLogger = new JobCustomLogger();

        Configuration configuration = new Configuration.Builder(context)
                .customLogger(customLogger)
                .minConsumerCount(8)
                .maxConsumerCount(16)
                .loadFactor(2)
                .consumerKeepAlive(120).
                        build();

        jobManager = new JobManager(context, configuration);
    }

    public static Jobs getInstance() {
        return ourInstance;
    }

    public JobManager getJobManager() {
        return jobManager;
    }

}
