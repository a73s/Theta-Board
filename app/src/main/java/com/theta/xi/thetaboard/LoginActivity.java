package com.theta.xi.thetaboard;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.button.MaterialButton;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private MaterialButton submit_email_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        submit_email_button = findViewById(R.id.submit_email_button);
        submit_email_button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v == submit_email_button ){
            finish();
        }
    }
}
