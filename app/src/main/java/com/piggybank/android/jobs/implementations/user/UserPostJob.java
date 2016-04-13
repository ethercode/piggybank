package com.piggybank.android.jobs.implementations.user;

import com.path.android.jobqueue.Params;
import com.piggybank.android.future.Future;
import com.piggybank.android.jobs.Priority;
import com.piggybank.android.jobs.base.PromiseJob;
import com.piggybank.android.models.service.UserModel;
import com.piggybank.android.services.Services;
import com.piggybank.android.services.implementations.RetrofitUserService;
import com.piggybank.android.util.TagUtil;

public class UserPostJob extends PromiseJob<UserModel> {
    private final UserModel model;

    public UserPostJob(UserModel model, Future<UserModel> future) {
        super(new Params(Priority.NORMAL).requireNetwork(), TagUtil.longFrom(UserPostJob.class), future);
        this.model = model;
    }

    @Override
    protected void execute() {
        RetrofitUserService.InsecureEndpoint insecureEndpoint = Services.getInstance().getUserService().getInsecureEndpoint();
        this.promiseResult = insecureEndpoint.postUser(model);
    }
}
