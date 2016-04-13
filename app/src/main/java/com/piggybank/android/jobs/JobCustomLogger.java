package com.piggybank.android.jobs;

import com.path.android.jobqueue.log.CustomLogger;
import com.piggybank.android.configuration.PiggyBankConfiguration;
import com.piggybank.android.logging.PiggyBankLog;

class JobCustomLogger implements CustomLogger {
    @Override
    public boolean isDebugEnabled() {
        return PiggyBankConfiguration.getInstance().getLogDebug();
    }

    @Override
    public void d(String text, Object... args) {
        PiggyBankLog.d(this.getClass().getSimpleName(), String.format(text, args));
    }

    @Override
    public void e(Throwable t, String text, Object... args) {
        PiggyBankLog.e(this.getClass().getSimpleName(), String.format(text, args), t);
    }

    @Override
    public void e(String text, Object... args) {
        PiggyBankLog.e(this.getClass().getSimpleName(), String.format(text, args));
    }
}
