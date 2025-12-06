package com.theta.xi.thetaboard;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.theta.xi.thetaboard.datacontainers.BoardInformation;

public class CreatePostActivity extends AppCompatActivity implements View.OnClickListener {

    public CreatePostActivity() {
    }

    BoardInformation current_board;
    private EditText post_title;
    private EditText post_body;
    private Button post_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_post_activity);
        post_title = findViewById(R.id.editTextPostTitle);
        post_body = findViewById(R.id.editTextPostBody);
        post_button = findViewById(R.id.buttonSubmitPost);
        post_button.setOnClickListener(this);

        current_board = (BoardInformation) getIntent().getSerializableExtra("BOARD_INFO");
        assert current_board != null;
    }

    @Override
    public void onClick(View v) {
        if(v == post_button){
            Boolean success = HttpRequestProxy.getProxy().postOnBoard(current_board.boardID, post_title.getText().toString(), post_body.getText().toString());
            if(success) {
                Toast.makeText(this, "Successfully created post.", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(this, "Failed to create post.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
