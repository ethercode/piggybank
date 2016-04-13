package com.piggybank.android.logging;

import android.util.Log;

import com.piggybank.android.application.PiggyBankApplication;

import java.io.PrintWriter;
import java.io.StringWriter;

public class PiggyBankLog {

    public static void v(String tag, String msg) {
        if (PiggyBankApplication.getConfiguration().getLogVerbose()) {
            Log.v(tag, msg);
        }
    }

    public static void v(String tag, String msg, Throwable tr) {
        if (PiggyBankApplication.getConfiguration().getLogVerbose()) {
            Log.v(tag, msg + '\n' + getStackTraceString(tr));
        }
    }

    public static void d(String tag, String msg) {
        if (PiggyBankApplication.getConfiguration().getLogDebug()) {
            Log.d(tag, msg);
        }
    }

    public static void d(String tag, String msg, Throwable tr) {
        if (PiggyBankApplication.getConfiguration().getLogDebug()) {
            Log.d(tag, msg + '\n' + getStackTraceString(tr));
        }
    }

    public static void i(String tag, String msg) {
        if (PiggyBankApplication.getConfiguration().getLogInfo()) {
            Log.i(tag, msg);
        }
    }

    public static void i(String tag, String msg, Throwable tr) {
        if (PiggyBankApplication.getConfiguration().getLogInfo()) {
            Log.i(tag, msg + '\n' + getStackTraceString(tr));
        }
    }

    public static void w(String tag, String msg) {
        if (PiggyBankApplication.getConfiguration().getLogWarn()) {
            Log.w(tag, msg);
        }
    }

    public static void w(String tag, String msg, Object... varArgs) {
        if (PiggyBankApplication.getConfiguration().getLogWarn()) {
            Log.w(tag, String.format(msg, varArgs));
        }
    }

    public static void w(String tag, String msg, Throwable tr) {
        if (PiggyBankApplication.getConfiguration().getLogWarn()) {
            Log.w(tag, msg + '\n' + getStackTraceString(tr));
        }
    }

    public static void e(String tag, String msg) {
        if (PiggyBankApplication.getConfiguration().getLogError()) {
            Log.e(tag, msg);
        }
    }

    public static void e(String tag, String msg, Throwable tr) {
        if (PiggyBankApplication.getConfiguration().getLogError()) {
            Log.e(tag, msg + '\n' + getStackTraceString(tr));
        }
    }

    public static void wtf(String tag, String msg) {
        if (PiggyBankApplication.getConfiguration().getLogWtf()) {
            Log.wtf(tag, msg);
        }
    }

    public static void wtf(String tag, Throwable tr) {
        if (PiggyBankApplication.getConfiguration().getLogWtf()) {
            Log.wtf(tag, '\n' + getStackTraceString(tr));
        }
    }

    public static void wtf(String tag, String msg, Throwable tr) {
        if (PiggyBankApplication.getConfiguration().getLogWtf()) {
            Log.wtf(tag, msg + '\n' + getStackTraceString(tr));
        }
    }

    public static String getStackTraceString(Throwable tr) {
        //this is a terrible kludge to working around a hack in the android sdk
        //the default android getStackTraceString in the Log class filters out unknown host exceptions.
        //this is to apparently reduce spam, butw totally useless when you actually would like
        //to see the unknown host exception!

        if (tr == null) {
            return "";
        }

        Throwable t = tr;
        while (t != null) {
            t = t.getCause();
        }

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        tr.printStackTrace(pw);
        pw.flush();
        return sw.toString();
    }
}