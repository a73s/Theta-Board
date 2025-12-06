package com.theta.xi.thetaboard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.activity.OnBackPressedCallback;

import android.os.Bundle;
import android.view.View;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private MaterialButton register_account_button;
    private MaterialButton login_button;
    private TextInputEditText email_entry;
    private TextInputEditText password_entry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(HttpRequestProxy.getProxy().sessionValid()){
            finish();
        }
        setContentView(R.layout.login_activity);
        register_account_button = findViewById(R.id.register_account_button);
        register_account_button.setOnClickListener(this);
        login_button = findViewById(R.id.login_button);
        login_button.setOnClickListener(this);
        email_entry = findViewById(R.id.login_email_text);
        password_entry = findViewById(R.id.login_password_text);

        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
            }
        };
        // Add the callback to the dispatcher
        getOnBackPressedDispatcher().addCallback(this, callback);
    }

    @Override
    public void onClick(View v) {
        if(v == login_button) {
            Boolean success = HttpRequestProxy.getProxy().login(email_entry.getText().toString(), password_entry.getText().toString());
            if(success) {
                finish();
            }
        } else if (v == register_account_button) {
            Boolean success = HttpRequestProxy.getProxy().register(email_entry.getText().toString(), password_entry.getText().toString());
        }
    }
}
