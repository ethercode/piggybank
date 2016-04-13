package com.piggybank.android.exceptions;

import com.piggybank.android.events.base.BaseLaunchActivityEvent;

public class NoLaunchClassException extends Exception {
    public NoLaunchClassException(BaseLaunchActivityEvent event) {
        super("Cannot determine launch class for event: " + event.getClass().getCanonicalName());
    }
}
