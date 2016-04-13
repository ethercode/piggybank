package com.piggybank.android.events;

import android.app.Activity;

import com.piggybank.android.activities.ExistingAccountActivity;
import com.piggybank.android.events.base.BaseLaunchActivityEvent;

import java.io.Serializable;

public class LaunchActivityExistingAccount extends BaseLaunchActivityEvent implements Serializable {
    public LaunchActivityExistingAccount(Activity activityToFinish) {
        super(activityToFinish);
    }

    @Override
    public Class getLaunchClass() {
        return ExistingAccountActivity.class;
    }
}

