package com.theta.xi.thetaboard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.activity.OnBackPressedCallback;

import android.os.Bundle;
import android.view.View;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private MaterialButton submit_email_button;
    private MaterialButton submit_otp_button;
    private TextInputEditText email_entry;
    private TextInputEditText otp_entry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        submit_email_button = findViewById(R.id.submit_email_button);
        submit_email_button.setOnClickListener(this);
        submit_otp_button = findViewById(R.id.submit_otp_button);
        submit_otp_button.setOnClickListener(this);
        email_entry = findViewById(R.id.login_email_text);
        otp_entry = findViewById(R.id.login_otp_text);

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
        finish();
    }
}
