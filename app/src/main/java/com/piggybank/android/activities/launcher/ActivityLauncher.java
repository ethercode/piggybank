package com.piggybank.android.activities.launcher;

import android.content.Intent;

import com.piggybank.android.activities.base.BaseActivity;
import com.piggybank.android.application.PiggyBankApplication;
import com.piggybank.android.events.LaunchActivityAccountsEvent;
import com.piggybank.android.events.LaunchActivityExistingAccount;
import com.piggybank.android.events.LaunchActivityLogInEvent;
import com.piggybank.android.events.LaunchActivityTransactionsEvent;
import com.piggybank.android.events.base.BaseLaunchActivityEvent;
import com.piggybank.android.exceptions.NoForegroundActivityException;
import com.piggybank.android.exceptions.NoLaunchClassException;
import com.piggybank.android.logging.PiggyBankLog;

import de.greenrobot.event.EventBus;

public class ActivityLauncher {

    public static final String EVENT_BUNDLE_KEY = "com.piggybank.android.event";

    public ActivityLauncher() {
        EventBus.getDefault().register(this);
    }

    public void onEventMainThread(LaunchActivityLogInEvent event) {
        handleLaunchForEvent(event);
    }

    public void onEventMainThread(LaunchActivityExistingAccount event) {
        handleLaunchForEvent(event);
    }

    public void onEventMainThread(LaunchActivityAccountsEvent event) {
        handleLaunchForEvent(event);
    }

    public void onEventMainThread(LaunchActivityTransactionsEvent event) {
        handleLaunchForEvent(event);
    }

    private void handleLaunchForEvent(BaseLaunchActivityEvent event) {
        try {
            BaseActivity foregroundActivity = PiggyBankApplication.getInstance().getForegroundActivity();
            if (foregroundActivity == null) {
                throw new NoForegroundActivityException();
            }
            if (event.getLaunchClass() == null) {
                throw new NoLaunchClassException(event);
            }
            Intent intent = new Intent(foregroundActivity, event.getLaunchClass());
            intent.putExtra(EVENT_BUNDLE_KEY, event);
            foregroundActivity.startActivity(intent);
            if (event.getActivityToFinish() != null) {
                event.getActivityToFinish().finish();
            }
        } catch (NoLaunchClassException | NoForegroundActivityException e) {
            PiggyBankLog.e(getClass().getName(), "Could not launch activity.", e);
        }
    }
}

