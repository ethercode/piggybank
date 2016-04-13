package com.piggybank.android.jobs.implementations.transaction;

import com.path.android.jobqueue.Params;
import com.piggybank.android.future.Future;
import com.piggybank.android.jobs.Priority;
import com.piggybank.android.jobs.base.PromiseJob;
import com.piggybank.android.models.service.TransactionModel;
import com.piggybank.android.services.Services;
import com.piggybank.android.services.implementations.RetrofitTransactionService;
import com.piggybank.android.util.TagUtil;

import java.util.HashMap;

public class TransactionGetAllJob extends PromiseJob<HashMap<String, TransactionModel>> {
    private final String account;

    public TransactionGetAllJob(String account, Future<HashMap<String, TransactionModel>> future) {
        super(new Params(Priority.NORMAL).requireNetwork(), TagUtil.longFrom(TransactionGetAllJob.class), future);
        this.account = account;
    }

    @Override
    protected void execute() {
        RetrofitTransactionService.SecureEndpoint secureEndpoint = Services.getInstance().getTransactionService().getSecureEndpoint();
        this.promiseResult = secureEndpoint.getAllTransactions(account);
    }
}
