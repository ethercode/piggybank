package com.piggybank.android.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.piggybank.android.R;
import com.piggybank.android.activities.base.BaseActivity;
import com.piggybank.android.activities.launcher.ActivityLauncher;
import com.piggybank.android.brokers.Brokers;
import com.piggybank.android.events.LaunchActivityAccountsEvent;
import com.piggybank.android.events.LaunchActivityTransactionsEvent;
import com.piggybank.android.future.FutureOnUiThread;
import com.piggybank.android.models.service.AccountModel;
import com.piggybank.android.models.service.TransactionModel;
import com.piggybank.android.util.AmountUtil;

import java.util.HashMap;

import de.greenrobot.event.EventBus;

public class TransactionsActivity extends BaseActivity implements View.OnClickListener {
    private AccountModel currentAccountModel;
    private String account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LaunchActivityTransactionsEvent event = (LaunchActivityTransactionsEvent) getIntent().getSerializableExtra(ActivityLauncher.EVENT_BUNDLE_KEY);
        this.account = event.getAccount();

        setContentView(R.layout.layout_transactions);

        assignClickListeners();
    }

    @Override
    protected void onResume() {
        super.onResume();

        getAccountData();
        getTransactionData();
    }

    @Override
    public void onClick(View v) {
        if (v == findViewById(R.id.transactions_delete)) {
            deleteAccount();
        } else if (v == findViewById(R.id.transactions_new)) {
            promptNewTransactionAmount();
        } else if (v == findViewById(R.id.transactions_changename)) {
            promptRename();
        } else if (v == findViewById(R.id.transactions_refresh)) {
            getAccountData();
            getTransactionData();
        }
    }

    private void promptRename() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        alert.setTitle("Account number");
        alert.setMessage("Enter your new account number:");

        final EditText input = new EditText(this);
        alert.setView(input);

        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                rename(input.getText().toString());
            }
        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
            }
        });

        alert.show();

    }

    private void rename(String name) {
        currentAccountModel.setFriendlyName(name);
        Brokers.getInstance().getAccountBroker().putAccount(account , currentAccountModel, new FutureOnUiThread<AccountModel>() {
            @Override
            public void onPromiseFulfilled(AccountModel value) {
                currentAccountModel = value;
                updateAccountModelUi();
            }
        });
    }

    private void assignClickListeners() {
        Button deleteAccountButton = (Button) findViewById(R.id.transactions_delete);
        Button newAccountButton = (Button) findViewById(R.id.transactions_new);
        Button changeNameButton = (Button) findViewById(R.id.transactions_changename);
        Button refreshButton = (Button) findViewById(R.id.transactions_refresh);

        deleteAccountButton.setOnClickListener(this);
        newAccountButton.setOnClickListener(this);
        changeNameButton.setOnClickListener(this);
        refreshButton.setOnClickListener(this);
    }

    private void getTransactionData() {
        Brokers.getInstance().getTransactionBroker().getTransactions(account, new FutureOnUiThread<HashMap<String, TransactionModel>>() {
            @Override
            public void onPromiseFulfilled(HashMap<String, TransactionModel> value) {
                TextView transactionsTextView = (TextView) findViewById(R.id.transactions_accounts);
                transactionsTextView.setText(TransactionsActivity.this.getString(R.string.transactions_transactions, value.size()));
                populateTransactionList(value);
            }
        });
    }

    private void populateTransactionList(final HashMap<String, TransactionModel> value) {
        ListView listView = (ListView) findViewById(R.id.transactions_list);
        listView.setAdapter(new TransactionListAdapter(value));
    }

    private void getAccountData() {
        Brokers.getInstance().getAccountBroker().getAccount(account, new FutureOnUiThread<AccountModel>() {
            @Override
            public void onPromiseFulfilled(AccountModel value) {
                currentAccountModel = value;

                updateAccountModelUi();
            }
        });
    }

    private void updateAccountModelUi() {
        TextView nameTextView = (TextView) findViewById(R.id.transactions_number);
        TextView createdTextView = (TextView) findViewById(R.id.transactions_created);
        TextView modifiedTextView = (TextView) findViewById(R.id.transactions_modified);

        nameTextView.setText(currentAccountModel.getFriendlyName());
        createdTextView.setText(currentAccountModel.getCreateTime().toString());
        modifiedTextView.setText(currentAccountModel.getModifiedTime().toString());
    }

    private void deleteAccount() {
        Brokers.getInstance().getAccountBroker().deleteAccount(account, new FutureOnUiThread<Boolean>() {
            @Override
            public void onPromiseFulfilled(Boolean value) {
                EventBus.getDefault().post(new LaunchActivityAccountsEvent(TransactionsActivity.this));
            }
        });
    }

    private void promptNewTransactionAmount() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        alert.setTitle("Send money (1 / 2)");
        alert.setMessage("Enter amount:");

        final EditText input = new EditText(this);
        alert.setView(input);

        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                promptNewTransactionAccount(Float.parseFloat(input.getText().toString()));
            }
        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
            }
        });

        alert.show();
    }

    private void promptNewTransactionAccount(final float amount) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        alert.setTitle("Send money (2 / 2)");
        alert.setMessage("Enter destination account:");

        final EditText input = new EditText(this);
        alert.setView(input);

        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                createTransaction(amount, input.getText().toString());
            }
        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
            }
        });

        alert.show();
    }

    private void createTransaction(float amount, String account) {
        TransactionModel transactionModel = new TransactionModel(this.account, amount);

        Brokers.getInstance().getTransactionBroker().postTransaction(account, transactionModel, new FutureOnUiThread<TransactionModel>() {
            @Override
            public void onPromiseFulfilled(TransactionModel value) {
                getTransactionData();
            }
        });
    }

    private class TransactionListAdapter extends BaseAdapter {
        private final HashMap<String, TransactionModel> value;
        private String[] keys = new String[32];

        public TransactionListAdapter(HashMap<String, TransactionModel> value) {
            this.value = value;
            keys = value.keySet().toArray(keys);
        }

        @Override
        public int getCount() {
            return value.size();
        }

        @Override
        public Object getItem(int position) {
            return value.get(keys[position]);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TransactionModel transactionModel = (TransactionModel) getItem(position);

            if (convertView == null) {
                convertView = LayoutInflater.from(TransactionsActivity.this).inflate(R.layout.view_transaction, parent, false);
            }

            TextView accountDetailTextView = (TextView) convertView.findViewById(R.id.view_transaction_detail);
            TextView accountAmountTextView = (TextView) convertView.findViewById(R.id.view_transaction_amount);

            String amount = AmountUtil.amountToString(transactionModel.getAmount());

            boolean isDebit = transactionModel.getAccount1Key().equals(account);
            accountDetailTextView.setText(isDebit ? "Debit" : "Credit");
            String sign = isDebit ? "-" : "";
            accountAmountTextView.setText(sign + amount);

            return convertView;
        }
    }
}
