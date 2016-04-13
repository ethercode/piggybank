package com.piggybank.android.activities.base;

import android.app.Activity;

import com.piggybank.android.R;
import com.piggybank.android.application.PiggyBankApplication;
import com.piggybank.android.events.InternetConnectivityLostEvent;
import com.piggybank.android.events.InternetConnectivityRestoredEvent;

import de.greenrobot.event.EventBus;

public abstract class BaseActivity extends Activity {

    public void onEventMainThread(InternetConnectivityLostEvent event) {

    }

    public void onEventMainThread(InternetConnectivityRestoredEvent event) {

    }

    @Override
    protected void onResume() {
        super.onResume();

        EventBus.getDefault().register(this);
        PiggyBankApplication.getInstance().setForegroundActivity(this);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    @Override
    protected void onPause() {
        super.onStop();

        EventBus.getDefault().unregister(this);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
}
