package com.piggybank.android.events.base;

import android.app.Activity;

import java.io.Serializable;

public abstract class BaseLaunchActivityEvent implements Serializable {
    private transient Activity activityToFinish;

    private BaseLaunchActivityEvent() {

    }

    protected BaseLaunchActivityEvent(Activity activityToFinish) {
        this.activityToFinish = activityToFinish;
    }

    public Activity getActivityToFinish() {
        return activityToFinish;
    }

    public abstract Class getLaunchClass();

}
