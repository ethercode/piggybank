package com.piggybank.android.util;

public class StatusUtil {
    public static boolean isSuccess(int statusCode) {
        return statusCode >= 200 && statusCode < 300;
    }
}
