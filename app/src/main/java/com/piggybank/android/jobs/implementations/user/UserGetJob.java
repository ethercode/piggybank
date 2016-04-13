package com.piggybank.android.jobs.implementations.user;

import com.path.android.jobqueue.Params;
import com.piggybank.android.future.Future;
import com.piggybank.android.jobs.Priority;
import com.piggybank.android.jobs.base.PromiseJob;
import com.piggybank.android.models.service.UserModel;
import com.piggybank.android.services.Services;
import com.piggybank.android.services.implementations.RetrofitUserService;
import com.piggybank.android.util.TagUtil;

public class UserGetJob extends PromiseJob<UserModel> {
    public UserGetJob(Future<UserModel> future) {
        super(new Params(Priority.NORMAL).requireNetwork(), TagUtil.longFrom(UserGetJob.class), future);
    }

    @Override
    protected void execute() {
        RetrofitUserService.SecureEndpoint secureEndpoint = Services.getInstance().getUserService().getSecureEndpoint();
        this.promiseResult = secureEndpoint.getUser();
    }
}
