package com.piggybank.android.application;

import android.app.Application;

import com.piggybank.android.activities.base.BaseActivity;
import com.piggybank.android.activities.launcher.ActivityLauncher;
import com.piggybank.android.configuration.PiggyBankConfiguration;
import com.piggybank.android.configuration.interfaces.Configuration;

import java.lang.ref.WeakReference;

public class PiggyBankApplication extends Application {
    private static WeakReference<PiggyBankApplication> application;
    private WeakReference<BaseActivity> foregroundActivity;
    private Configuration configuration;
    private ActivityLauncher activityLauncher;

    public static PiggyBankApplication getInstance() {
        return application.get();
    }

    public static Configuration getConfiguration() {
        return getInstance().configuration;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        PiggyBankApplication.application = new WeakReference<>(this);
        this.activityLauncher = new ActivityLauncher();
        this.configuration = PiggyBankConfiguration.getInstance();
    }

    public BaseActivity getForegroundActivity() {
        return foregroundActivity != null ? foregroundActivity.get() : null;
    }

    public void setForegroundActivity(BaseActivity foregroundActivity) {
        this.foregroundActivity = new WeakReference<>(foregroundActivity);
    }

    public ActivityLauncher getActivityLauncher() {
        return activityLauncher;
    }
}
