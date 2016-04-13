package com.piggybank.android.services.types;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class IsoDate extends Date {
    public IsoDate(long milliseconds) {
        super(milliseconds);
    }

    public static IsoDate convert(String dateString) {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault());
        format.setTimeZone(TimeZone.getTimeZone("GMT"));
        long milliseconds;
        try {
            milliseconds = format.parse(dateString).getTime();
        } catch (ParseException e) {
            milliseconds = 0;
        }

        return new IsoDate(milliseconds);
    }

    public static long convert(Date date) {
        return date == null ? 0 : date.getTime();
    }
}
