package com.piggybank.android.events;

import android.app.Activity;

import com.piggybank.android.activities.TransactionsActivity;
import com.piggybank.android.events.base.BaseLaunchActivityEvent;

import java.io.Serializable;

public class LaunchActivityTransactionsEvent extends BaseLaunchActivityEvent implements Serializable {
    private String account;

    public LaunchActivityTransactionsEvent(String account, Activity activityToFinish) {
        super(activityToFinish);
        this.account = account;
    }

    @Override
    public Class getLaunchClass() {
        return TransactionsActivity.class;
    }

    public String getAccount() {
        return account;
    }
}
