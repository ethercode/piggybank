package com.piggybank.android.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.piggybank.android.activities.base.BaseActivity;
import com.piggybank.android.application.PiggyBankApplication;

public class SecureUtil {
    private static final String PREFS_KEY_KEY = "UserKey";
    private static final String PREFS_KEY_SECRET = "UserSecret";

    private static SharedPreferences getSharedPreferences() {
        BaseActivity foregroundActivity = PiggyBankApplication.getInstance().getForegroundActivity();
        return foregroundActivity.getSharedPreferences("com.piggybank.android", Context.MODE_PRIVATE);
    }

    public static void setUserKeySecret(String key, String secret) {
        SharedPreferences preferences = getSharedPreferences();
        preferences.edit().putString(PREFS_KEY_KEY, key).putString(PREFS_KEY_SECRET, secret).apply();
    }

    public static String getUserKey() {
        SharedPreferences preferences = getSharedPreferences();
        return preferences.getString(PREFS_KEY_KEY, null);
    }

    public static String getUserSecret() {
        SharedPreferences preferences = getSharedPreferences();
        return preferences.getString(PREFS_KEY_SECRET, null);
    }

}
