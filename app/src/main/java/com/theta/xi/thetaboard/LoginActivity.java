package com.theta.xi.thetaboard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.activity.OnBackPressedCallback;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {


    private MaterialButton register_account_button;
    private MaterialButton login_button;
    private TextInputEditText email_entry;
    private TextInputEditText password_entry;

    // For background threading
    private final Executor thread = Executors.newSingleThreadExecutor();
    private final Handler callback = new Handler(Looper.getMainLooper());

    private Toast currentToast = null;

    private void showToast(String message) {
        //hacky workaround to toast looping if network is failing
        if (currentToast != null) {
            currentToast.cancel();
        }
        currentToast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
        currentToast.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        checkSessionValidity();
    }

    private void checkSessionValidity() {
        thread.execute(() -> {
            try {
                boolean sessionValid = HttpRequestProxy.getProxy().sessionValid();
                callback.post(() -> {
                    if (sessionValid) {
                        Log.d("checkSessionValidity", "Session is valid");
                        finish();
                    } else {
                        Log.d("checkSessionValidity", "Session is invalid");
                    }
                });
            } catch (Exception e) {
                Log.e("checkSessionValidity", "Session is invalid", e);
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v == login_button) {
            handleLogin();
        } else if (v == register_account_button) {
            handleRegister();
        }
    }

    private void handleLogin() {
        String email = email_entry.getText().toString().trim();
        String password = password_entry.getText().toString().trim();
        thread.execute(() -> {
            boolean success = false;
            String errorMessage = "Failed to log in.";
            try {
                success = HttpRequestProxy.getProxy().login(email, password);
                Log.d("handleLogin", "Login" + success);
            } catch (Exception e) {
                Log.d("handleLogin", "Login failed");
                errorMessage = "Exception";
            }

            final boolean finalSuccess = success;
            final String finalErrorMessage = errorMessage;
            callback.post(() -> {
                setButtonsEnabled(true);
                if (finalSuccess) {
                    showToast("Successfully logged in.");
                    finish();
                } else {
                    showToast(finalErrorMessage);
                }
            });
        });
    }

    private void handleRegister() {
        String email = email_entry.getText().toString().trim();
        String password = password_entry.getText().toString().trim();
        thread.execute(() -> {
            boolean success = false;
            String message;
            try {
                success = HttpRequestProxy.getProxy().register(email, password);
                Log.d("handleRegister", "Registration result: " + success);
                if (success) {
                    message = "Successfully registered.";
                } else {
                    message = "Failed to register.";
                }
            } catch (Exception e) {
                Log.d("handleRegister", "Registration failed");
                message = "Exception";
            }
            final String finalMessage = message;
            callback.post(() -> {
                setButtonsEnabled(true);
                showToast(finalMessage);
            });
        });
    }

    private void setButtonsEnabled(boolean enabled) {
        login_button.setEnabled(enabled);
        register_account_button.setEnabled(enabled);
        email_entry.setEnabled(enabled);
        password_entry.setEnabled(enabled);
    }

}