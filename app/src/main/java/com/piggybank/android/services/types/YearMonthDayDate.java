package com.piggybank.android.services.types;

import com.piggybank.android.logging.PiggyBankLog;
import com.piggybank.android.util.TagUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class YearMonthDayDate extends Date {
    public YearMonthDayDate(String yearMonthDay) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

        Date date;

        try {
            date = format.parse(yearMonthDay);
        } catch (ParseException e) {
            PiggyBankLog.w(TagUtil.shortFrom(this), "Could not parse date: " + yearMonthDay);
            date = new Date(0);
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        this.setTime(calendar.getTimeInMillis());
    }
}
