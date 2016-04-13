package com.piggybank.android.configuration;

import com.piggybank.android.R;
import com.piggybank.android.application.PiggyBankApplication;
import com.piggybank.android.configuration.interfaces.Configuration;

public class PiggyBankConfiguration implements Configuration {
    private static final PiggyBankConfiguration ourInstance = new PiggyBankConfiguration();

    private PiggyBankConfiguration() {
    }

    public static PiggyBankConfiguration getInstance() {
        return ourInstance;
    }

    @Override
    public String getFlavour() {
        return PiggyBankApplication.getInstance().getString(R.string.setting_flavour);
    }

    @Override
    public String getApiEndpoint() {
        return PiggyBankApplication.getInstance().getString(R.string.setting_api_endpoint);
    }

    @Override
    public boolean getLogVerbose() {
        return PiggyBankApplication.getInstance().getResources().getBoolean(R.bool.setting_log_verbose);
    }

    @Override
    public boolean getLogDebug() {
        return PiggyBankApplication.getInstance().getResources().getBoolean(R.bool.setting_log_debug);
    }

    @Override
    public boolean getLogWarn() {
        return PiggyBankApplication.getInstance().getResources().getBoolean(R.bool.setting_log_warn);
    }

    @Override
    public boolean getLogError() {
        return PiggyBankApplication.getInstance().getResources().getBoolean(R.bool.setting_log_error);
    }

    @Override
    public boolean getLogInfo() {
        return PiggyBankApplication.getInstance().getResources().getBoolean(R.bool.setting_log_info);
    }

    @Override
    public boolean getLogWtf() {
        return PiggyBankApplication.getInstance().getResources().getBoolean(R.bool.setting_log_wtf);
    }
}
