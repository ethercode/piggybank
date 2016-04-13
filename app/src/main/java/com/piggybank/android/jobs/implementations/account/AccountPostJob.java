package com.piggybank.android.jobs.implementations.account;

import com.path.android.jobqueue.Params;
import com.piggybank.android.future.Future;
import com.piggybank.android.jobs.Priority;
import com.piggybank.android.jobs.base.PromiseJob;
import com.piggybank.android.models.service.AccountModel;
import com.piggybank.android.services.Services;
import com.piggybank.android.services.implementations.RetrofitAccountService;
import com.piggybank.android.util.TagUtil;

public class AccountPostJob extends PromiseJob<AccountModel> {

    private final AccountModel model;

    public AccountPostJob(AccountModel model, Future<AccountModel> future) {
        super(new Params(Priority.NORMAL).requireNetwork(), TagUtil.longFrom(AccountPostJob.class), future);
        this.model = model;
    }

    @Override
    protected void execute() {
        RetrofitAccountService.SecureEndpoint secureEndpoint = Services.getInstance().getAccountService().getSecureEndpoint();
        this.promiseResult = secureEndpoint.postAccount(model);
    }
}
