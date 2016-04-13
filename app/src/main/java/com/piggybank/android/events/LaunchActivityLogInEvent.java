package com.piggybank.android.events;

import android.app.Activity;

import com.piggybank.android.activities.NoAccountActivity;
import com.piggybank.android.events.base.BaseLaunchActivityEvent;

import java.io.Serializable;

public class LaunchActivityLogInEvent extends BaseLaunchActivityEvent implements Serializable {
    public LaunchActivityLogInEvent(Activity activityToFinish) {
        super(activityToFinish);
    }

    @Override
    public Class getLaunchClass() {
        return NoAccountActivity.class;
    }
}
