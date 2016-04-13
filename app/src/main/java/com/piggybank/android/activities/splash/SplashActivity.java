package com.piggybank.android.activities.splash;

import android.os.Bundle;

import com.piggybank.android.R;
import com.piggybank.android.activities.base.BaseActivity;
import com.piggybank.android.events.LaunchActivityAccountsEvent;
import com.piggybank.android.events.LaunchActivityLogInEvent;
import com.piggybank.android.util.SecureUtil;

import de.greenrobot.event.EventBus;

public class SplashActivity extends BaseActivity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.layout_activity_splash);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (SecureUtil.getUserKey() == null || SecureUtil.getUserSecret() == null) {
            EventBus.getDefault().post(new LaunchActivityLogInEvent(this));
        } else {
            EventBus.getDefault().post(new LaunchActivityAccountsEvent(this));
        }
    }
}
