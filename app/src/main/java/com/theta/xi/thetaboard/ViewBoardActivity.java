package com.theta.xi.thetaboard;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.theta.xi.thetaboard.datacontainers.BoardInformation;
import com.theta.xi.thetaboard.datacontainers.BoardPostInformation;

import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ViewBoardActivity extends AppCompatActivity implements View.OnClickListener {

    public ViewBoardActivity() {
    }

    FloatingActionButton create_post_button;
    BoardInformation current_board;
    BoardPostsRecyclerAdapter adapter;

    private final Executor thread = Executors.newSingleThreadExecutor();
    private final Handler callback = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_board_activity);

        current_board = (BoardInformation) getIntent().getSerializableExtra("boardData");
        assert current_board != null;

        RecyclerView recyclerView = findViewById(R.id.board_post_list_container);
        create_post_button = findViewById(R.id.create_post_button);
        create_post_button.setOnClickListener(this);
        if (!current_board.userIsPoster){
            create_post_button.setVisibility(View.GONE);
        }

        adapter = new BoardPostsRecyclerAdapter(new ArrayList<>());
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(
                recyclerView.getContext(),
                layoutManager.getOrientation()
        );
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setAdapter(adapter);
        loadPosts();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadPosts();
    }

    private void loadPosts() {
        thread.execute(() -> {
            ArrayList<BoardPostInformation> posts = HttpRequestProxy.getProxy().getAllPostsForBoard(current_board.boardID);
            callback.post(() -> {
                adapter.updatePosts(posts);
            });
        });
    }

    @Override
    public void onClick(View v) {
        if(v == create_post_button){
            Intent intent = new Intent(this, CreatePostActivity.class);
            intent.putExtra("boardData", current_board);
            startActivity(intent);
        }
    }
}