package com.piggybank.android.brokers;

import com.piggybank.android.brokers.implementations.JobBasedAccountBroker;
import com.piggybank.android.brokers.implementations.JobBasedTransactionBroker;
import com.piggybank.android.brokers.implementations.JobBasedUserBroker;
import com.piggybank.android.brokers.interfaces.AccountBroker;
import com.piggybank.android.brokers.interfaces.TransactionBroker;
import com.piggybank.android.brokers.interfaces.UserBroker;

public class Brokers {
    private static final Brokers ourInstance = new Brokers();
    private final UserBroker userBroker;
    private final AccountBroker accountBroker;
    private final TransactionBroker transactionBroker;

    private Brokers() {
        userBroker = new JobBasedUserBroker();
        accountBroker = new JobBasedAccountBroker();
        transactionBroker = new JobBasedTransactionBroker();
    }

    public static Brokers getInstance() {
        return ourInstance;
    }

    public UserBroker getUserBroker() {
        return userBroker;
    }

    public AccountBroker getAccountBroker() {
        return accountBroker;
    }

    public TransactionBroker getTransactionBroker() {
        return transactionBroker;
    }
}
