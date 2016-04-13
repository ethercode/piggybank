package com.piggybank.android.util;

public class TagUtil {
    public static String longFrom(Class klass) {
        return klass.getCanonicalName();
    }

    public static String longFrom(Object instance) {
        return longFrom(instance.getClass());
    }

    public static String shortFrom(Class klass) {
        return klass.getSimpleName();
    }

    public static String shortFrom(Object instance) {
        return shortFrom(instance.getClass());
    }
}
