package com.piggybank.android.exceptions;

public class NoForegroundActivityException extends Exception {
    public NoForegroundActivityException() {
        super("No foreground activity to launch from.");
    }

}
