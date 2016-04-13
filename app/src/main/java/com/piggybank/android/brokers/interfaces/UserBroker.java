package com.piggybank.android.brokers.interfaces;

import com.piggybank.android.brokers.Broker;
import com.piggybank.android.future.Future;
import com.piggybank.android.models.service.UserModel;

public interface UserBroker extends Broker {
    void getUser(Future<UserModel> future);

    void postUser(UserModel model, Future<UserModel> future);

    void putUser(UserModel model, Future<UserModel> future);

    void deleteUser(Future<Boolean> future);
}