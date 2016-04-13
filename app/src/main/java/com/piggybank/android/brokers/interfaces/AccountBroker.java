package com.piggybank.android.brokers.interfaces;

import com.piggybank.android.brokers.Broker;
import com.piggybank.android.future.Future;
import com.piggybank.android.models.service.AccountModel;

import java.util.HashMap;

public interface AccountBroker extends Broker {
    void getAccounts(Future<HashMap<String, AccountModel>> future);

    void getAccount(String account, Future<AccountModel> future);

    void postAccount(AccountModel model, Future<AccountModel> future);

    void putAccount(String account, AccountModel model, Future<AccountModel> future);

    void deleteAccount(String account, Future<Boolean> future);
}
