package com.piggybank.android.jobs.implementations.transaction;

import com.path.android.jobqueue.Params;
import com.piggybank.android.future.Future;
import com.piggybank.android.jobs.Priority;
import com.piggybank.android.jobs.base.PromiseJob;
import com.piggybank.android.models.service.TransactionModel;
import com.piggybank.android.services.Services;
import com.piggybank.android.services.implementations.RetrofitTransactionService;
import com.piggybank.android.util.TagUtil;

public class TransactionPostJob extends PromiseJob<TransactionModel> {
    private final String account;
    private final TransactionModel model;

    public TransactionPostJob(String account, TransactionModel model, Future<TransactionModel> future) {
        super(new Params(Priority.NORMAL).requireNetwork(), TagUtil.longFrom(TransactionPostJob.class), future);
        this.account = account;
        this.model = model;
    }

    @Override
    protected void execute() {
        RetrofitTransactionService.SecureEndpoint secureEndpoint = Services.getInstance().getTransactionService().getSecureEndpoint();
        this.promiseResult = secureEndpoint.postTransaction(account, model);
    }
}
