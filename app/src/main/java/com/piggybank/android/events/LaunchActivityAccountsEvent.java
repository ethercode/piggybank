package com.piggybank.android.events;

import android.app.Activity;

import com.piggybank.android.activities.AccountsActivity;
import com.piggybank.android.events.base.BaseLaunchActivityEvent;

import java.io.Serializable;

public class LaunchActivityAccountsEvent extends BaseLaunchActivityEvent implements Serializable {

    public LaunchActivityAccountsEvent(Activity activityToFinish) {
        super(activityToFinish);
    }

    @Override
    public Class getLaunchClass() {
        return AccountsActivity.class;
    }
}
