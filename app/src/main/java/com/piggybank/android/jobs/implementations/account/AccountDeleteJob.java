package com.piggybank.android.jobs.implementations.account;

import com.path.android.jobqueue.Params;
import com.piggybank.android.future.Future;
import com.piggybank.android.jobs.Priority;
import com.piggybank.android.jobs.base.PromiseJob;
import com.piggybank.android.services.Services;
import com.piggybank.android.services.implementations.RetrofitAccountService;
import com.piggybank.android.util.StatusUtil;
import com.piggybank.android.util.TagUtil;

import retrofit.client.Response;

public class AccountDeleteJob extends PromiseJob<Boolean> {
    private String account;

    public AccountDeleteJob(String account, Future<Boolean> future) {
        super(new Params(Priority.NORMAL).requireNetwork(), TagUtil.longFrom(AccountDeleteJob.class), future);
        this.account = account;
    }

    @Override
    protected void execute() {
        RetrofitAccountService.SecureEndpoint secureEndpoint = Services.getInstance().getAccountService().getSecureEndpoint();
        Response response = secureEndpoint.deleteAccount(account);
        this.promiseResult = StatusUtil.isSuccess(response.getStatus());
    }
}
