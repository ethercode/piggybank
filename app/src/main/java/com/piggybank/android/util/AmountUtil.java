package com.piggybank.android.util;

import java.text.NumberFormat;
import java.util.Locale;

public class AmountUtil {
    private static final NumberFormat ZAR = NumberFormat.getCurrencyInstance(new Locale("en", "ZA"));

    public static String amountToString(float value) {
        return ZAR.format(value);
    }
}
