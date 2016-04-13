package com.piggybank.android.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.piggybank.android.R;
import com.piggybank.android.activities.base.BaseActivity;
import com.piggybank.android.brokers.Brokers;
import com.piggybank.android.events.LaunchActivityAccountsEvent;
import com.piggybank.android.future.FutureOnUiThread;
import com.piggybank.android.models.service.UserModel;
import com.piggybank.android.util.SecureUtil;

import de.greenrobot.event.EventBus;

public class NoAccountActivity extends BaseActivity implements View.OnClickListener {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.layout_activity_no_account);

        Button signUpButton = (Button) findViewById(R.id.button_new_account);
        final Button signInButton = (Button) findViewById(R.id.button_existing_account);

        signUpButton.setOnClickListener(this);
        signInButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == findViewById(R.id.button_new_account)) {
            promptCreateUser();
        } else if (v == findViewById(R.id.button_existing_account)) {
            //TODO: implement this
        }
    }

    private void promptCreateUser() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        alert.setTitle("User name");
        alert.setMessage("Enter your name:");

        final EditText input = new EditText(this);
        alert.setView(input);

        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                createUser(input.getText().toString());
            }
        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
            }
        });

        alert.show();
    }

    private void createUser(String name) {
        UserModel userModel = new UserModel(name);

        Brokers.getInstance().getUserBroker().postUser(userModel, new FutureOnUiThread<UserModel>() {
            @Override
            public void onPromiseFulfilled(UserModel value) {
                SecureUtil.setUserKeySecret(value.getKey(), value.getSecret());
                EventBus.getDefault().post(new LaunchActivityAccountsEvent(NoAccountActivity.this));
            }
        });
    }

}
