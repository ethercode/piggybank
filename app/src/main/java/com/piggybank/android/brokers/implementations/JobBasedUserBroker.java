package com.piggybank.android.brokers.implementations;

import com.piggybank.android.brokers.interfaces.UserBroker;
import com.piggybank.android.future.Future;
import com.piggybank.android.jobs.Jobs;
import com.piggybank.android.jobs.implementations.user.UserDeleteJob;
import com.piggybank.android.jobs.implementations.user.UserGetJob;
import com.piggybank.android.jobs.implementations.user.UserPostJob;
import com.piggybank.android.jobs.implementations.user.UserPutJob;
import com.piggybank.android.models.service.UserModel;

public class JobBasedUserBroker implements UserBroker {

    @Override
    public void getUser(Future<UserModel> future) {
        Jobs.getInstance().getJobManager().addJob(new UserGetJob(future));
    }

    @Override
    public void postUser(UserModel model, Future<UserModel> future) {
        Jobs.getInstance().getJobManager().addJob(new UserPostJob(model, future));
    }

    @Override
    public void putUser(UserModel model, Future<UserModel> future) {
        Jobs.getInstance().getJobManager().addJob(new UserPutJob(model, future));
    }

    @Override
    public void deleteUser(Future<Boolean> future) {
        Jobs.getInstance().getJobManager().addJob(new UserDeleteJob(future));
    }
}
