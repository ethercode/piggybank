package com.piggybank.android.jobs.implementations.user;

import com.path.android.jobqueue.Params;
import com.piggybank.android.future.Future;
import com.piggybank.android.jobs.Priority;
import com.piggybank.android.jobs.base.PromiseJob;
import com.piggybank.android.services.Services;
import com.piggybank.android.services.implementations.RetrofitUserService;
import com.piggybank.android.util.StatusUtil;
import com.piggybank.android.util.TagUtil;

import retrofit.client.Response;

public class UserDeleteJob extends PromiseJob<Boolean> {
    public UserDeleteJob(Future<Boolean> future) {
        super(new Params(Priority.NORMAL).requireNetwork(), TagUtil.longFrom(UserDeleteJob.class), future);
    }

    @Override
    protected void execute() {
        RetrofitUserService.SecureEndpoint secureEndpoint = Services.getInstance().getUserService().getSecureEndpoint();
        Response response = secureEndpoint.deleteUser();
        this.promiseResult = StatusUtil.isSuccess(response.getStatus());
    }
}
