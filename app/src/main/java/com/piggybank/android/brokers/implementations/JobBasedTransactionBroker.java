package com.piggybank.android.brokers.implementations;

import com.piggybank.android.brokers.interfaces.TransactionBroker;
import com.piggybank.android.future.Future;
import com.piggybank.android.jobs.Jobs;
import com.piggybank.android.jobs.implementations.transaction.TransactionGetAllJob;
import com.piggybank.android.jobs.implementations.transaction.TransactionPostJob;
import com.piggybank.android.models.service.TransactionModel;

import java.util.HashMap;

public class JobBasedTransactionBroker implements TransactionBroker {
    @Override
    public void getTransactions(String account, Future<HashMap<String, TransactionModel>> future) {
        Jobs.getInstance().getJobManager().addJob(new TransactionGetAllJob(account, future));
    }

    @Override
    public void postTransaction(String account, TransactionModel model, Future<TransactionModel> future) {
        Jobs.getInstance().getJobManager().addJob(new TransactionPostJob(account, model, future));
    }
}
