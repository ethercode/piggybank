package com.piggybank.android.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.piggybank.android.R;
import com.piggybank.android.activities.base.BaseActivity;
import com.piggybank.android.brokers.Brokers;
import com.piggybank.android.events.LaunchActivityLogInEvent;
import com.piggybank.android.events.LaunchActivityTransactionsEvent;
import com.piggybank.android.future.FutureOnUiThread;
import com.piggybank.android.models.service.AccountModel;
import com.piggybank.android.models.service.UserModel;
import com.piggybank.android.util.AmountUtil;
import com.piggybank.android.util.SecureUtil;

import java.util.HashMap;

import de.greenrobot.event.EventBus;

public class AccountsActivity extends BaseActivity implements View.OnClickListener {
    private UserModel currentUserModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.layout_account);

        assignClickListeners();
    }

    @Override
    protected void onResume() {
        super.onResume();

        getUserData();
        getAccountsData();
    }

    @Override
    public void onClick(View v) {
        if (v == findViewById(R.id.accounts_delete)) {
            deleteAccount();
        } else if (v == findViewById(R.id.accounts_log_out)) {
            logOut();
        } else if (v == findViewById(R.id.accounts_new)) {
            promptCreateAccount();
        } else if (v == findViewById(R.id.accounts_changename)) {
            promptChangeName();
        } else if (v == findViewById(R.id.accounts_refresh)) {
            getUserData();
            getAccountsData();
        }
    }

    private void promptChangeName() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        alert.setTitle("User name");
        alert.setMessage("Enter your new user name:");

        final EditText input = new EditText(this);
        alert.setView(input);

        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                changeName(input.getText().toString());
            }
        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
            }
        });

        alert.show();

    }

    private void changeName(String name) {
        currentUserModel.setName(name);
        Brokers.getInstance().getUserBroker().putUser(currentUserModel, new FutureOnUiThread<UserModel>() {
            @Override
            public void onPromiseFulfilled(UserModel value) {
                currentUserModel = value;
                updateUserModelUi();
            }
        });
    }

    private void assignClickListeners() {
        Button deleteUserButton = (Button) findViewById(R.id.accounts_delete);
        Button logOutButton = (Button) findViewById(R.id.accounts_log_out);
        Button newAccountButton = (Button) findViewById(R.id.accounts_new);
        Button changeNameButton = (Button) findViewById(R.id.accounts_changename);
        Button refreshButton = (Button) findViewById(R.id.accounts_refresh);

        deleteUserButton.setOnClickListener(this);
        logOutButton.setOnClickListener(this);
        newAccountButton.setOnClickListener(this);
        changeNameButton.setOnClickListener(this);
        refreshButton.setOnClickListener(this);

        ListView listView = (ListView) findViewById((R.id.accounts_list));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AccountModel account = (AccountModel) view.getTag();
                EventBus.getDefault().post(new LaunchActivityTransactionsEvent(account.getKey(), null));
            }
        });
    }

    private void getAccountsData() {
        Brokers.getInstance().getAccountBroker().getAccounts(new FutureOnUiThread<HashMap<String, AccountModel>>() {
            @Override
            public void onPromiseFulfilled(HashMap<String, AccountModel> value) {
                TextView accountsTextView = (TextView) findViewById(R.id.accounts_accounts);
                accountsTextView.setText(AccountsActivity.this.getString(R.string.accounts_accounts, value.size()));
                populateAccountList(value);
            }
        });
    }

    private void populateAccountList(final HashMap<String, AccountModel> value) {
        ListView listView = (ListView) findViewById(R.id.accounts_list);
        listView.setAdapter(new AccountListAdapter(value));
    }

    private void getUserData() {
        Brokers.getInstance().getUserBroker().getUser(new FutureOnUiThread<UserModel>() {
            @Override
            public void onPromiseFulfilled(UserModel value) {
                currentUserModel = value;

                updateUserModelUi();
            }
        });
    }

    private void updateUserModelUi() {
        TextView nameTextView = (TextView) findViewById(R.id.accounts_name);
        TextView createdTextView = (TextView) findViewById(R.id.accounts_created);
        TextView modifiedTextView = (TextView) findViewById(R.id.accounts_modified);

        nameTextView.setText(currentUserModel.getName());
        createdTextView.setText(currentUserModel.getCreateTime().toString());
        modifiedTextView.setText(currentUserModel.getModifiedTime().toString());
    }

    private void deleteAccount() {
        Brokers.getInstance().getUserBroker().deleteUser(new FutureOnUiThread<Boolean>() {
            @Override
            public void onPromiseFulfilled(Boolean value) {
                logOut();
            }
        });
    }

    private void logOut() {
        SecureUtil.setUserKeySecret(null, null);
        EventBus.getDefault().post(new LaunchActivityLogInEvent(this));
    }

    private void promptCreateAccount() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        alert.setTitle("Account number");
        alert.setMessage("Enter your new account number:");

        final EditText input = new EditText(this);
        alert.setView(input);

        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                createAccount(input.getText().toString());
            }
        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
            }
        });

        alert.show();
    }

    private void createAccount(String friendlyName) {
        AccountModel newAccount = new AccountModel(friendlyName);

        Brokers.getInstance().getAccountBroker().postAccount(newAccount, new FutureOnUiThread<AccountModel>() {
            @Override
            public void onPromiseFulfilled(AccountModel value) {
                getAccountsData();
            }
        });
    }

    private class AccountListAdapter extends BaseAdapter {
        private final HashMap<String, AccountModel> value;
        private String[] keys = new String[32];

        public AccountListAdapter(HashMap<String, AccountModel> value) {
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
            AccountModel accountModel = (AccountModel) getItem(position);

            if (convertView == null) {
                convertView = LayoutInflater.from(AccountsActivity.this).inflate(R.layout.view_account, parent, false);
            }

            TextView accountNameTextView = (TextView) convertView.findViewById(R.id.view_account_name);
            TextView accountBalanceTextView = (TextView) convertView.findViewById(R.id.view_account_balance);

            String amount = AmountUtil.amountToString(accountModel.getBalance());

            accountNameTextView.setText(accountModel.getFriendlyName());
            accountBalanceTextView.setText(amount);

            convertView.setTag(accountModel);

            return convertView;
        }
    }
}
