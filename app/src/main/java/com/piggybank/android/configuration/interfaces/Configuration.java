package com.piggybank.android.configuration.interfaces;

public interface Configuration {
    String getFlavour();

    String getApiEndpoint();

    boolean getLogVerbose();

    boolean getLogDebug();

    boolean getLogWarn();

    boolean getLogError();

    boolean getLogInfo();

    boolean getLogWtf();
}
