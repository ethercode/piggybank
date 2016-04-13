package com.piggybank.android.brokers.interfaces;

import com.piggybank.android.brokers.Broker;
import com.piggybank.android.future.Future;
import com.piggybank.android.models.service.TransactionModel;

import java.util.HashMap;

public interface TransactionBroker extends Broker {
    void getTransactions(String account, Future<HashMap<String, TransactionModel>> future);

    void postTransaction(String account, TransactionModel model, Future<TransactionModel> future);
}