package com.piggybank.android.brokers.implementations;

import com.piggybank.android.brokers.interfaces.AccountBroker;
import com.piggybank.android.future.Future;
import com.piggybank.android.jobs.Jobs;
import com.piggybank.android.jobs.implementations.account.AccountDeleteJob;
import com.piggybank.android.jobs.implementations.account.AccountGetAllJob;
import com.piggybank.android.jobs.implementations.account.AccountGetJob;
import com.piggybank.android.jobs.implementations.account.AccountPostJob;
import com.piggybank.android.jobs.implementations.account.AccountPutJob;
import com.piggybank.android.models.service.AccountModel;

import java.util.HashMap;

public class JobBasedAccountBroker implements AccountBroker {

    @Override
    public void getAccounts(Future<HashMap<String, AccountModel>> future) {
        Jobs.getInstance().getJobManager().addJob(new AccountGetAllJob(future));
    }

    @Override
    public void getAccount(String account, Future<AccountModel> future) {
        Jobs.getInstance().getJobManager().addJob(new AccountGetJob(account, future));
    }

    @Override
    public void postAccount(AccountModel model, Future<AccountModel> future) {
        Jobs.getInstance().getJobManager().addJob(new AccountPostJob(model, future));
    }

    @Override
    public void putAccount(String account, AccountModel model, Future<AccountModel> future) {
        Jobs.getInstance().getJobManager().addJob(new AccountPutJob(account, model, future));
    }

    @Override
    public void deleteAccount(String account, Future<Boolean> future) {
        Jobs.getInstance().getJobManager().addJob(new AccountDeleteJob(account, future));
    }
}
