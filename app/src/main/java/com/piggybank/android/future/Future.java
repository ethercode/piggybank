package com.piggybank.android.future;

public abstract class Future<T> {
    private boolean pending = true;

    public abstract void onPromiseFulfilled(T value);

    public void cancel() {
        pending = false;
    }

    public boolean isPending() {
        return pending;
    }
}
