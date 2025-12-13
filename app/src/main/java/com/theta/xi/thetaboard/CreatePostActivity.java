package com.theta.xi.thetaboard;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.theta.xi.thetaboard.datacontainers.BoardInformation;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class CreatePostActivity extends AppCompatActivity implements View.OnClickListener {

    public CreatePostActivity() {
    }

    BoardInformation current_board;
    private EditText title;
    private EditText body;
    private Button post_button;

    private final Executor thread = Executors.newSingleThreadExecutor();
    private final Handler callback = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_post_activity);
        title = findViewById(R.id.editTextPostTitle);
        body = findViewById(R.id.editTextPostBody);
        post_button = findViewById(R.id.buttonSubmitPost);
        post_button.setOnClickListener(this);
        current_board = (BoardInformation) getIntent().getSerializableExtra("boardData");
        assert current_board != null;
    }

    @Override
    public void onClick(View v) {
        if(v == post_button){
            String title = this.title.getText().toString().trim();
            String body = this.body.getText().toString().trim();
            thread.execute(() -> {
                Boolean success = HttpRequestProxy.getProxy().postOnBoard(current_board.boardID, title, body);
                callback.post(() -> {
                    post_button.setEnabled(true);
                    if(success) {
                        Toast.makeText(this, "Successfully created post.", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(this, "Failed to create post.", Toast.LENGTH_SHORT).show();
                    }
                });
            });
        }
    }
}