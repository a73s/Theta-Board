package com.theta.xi.thetaboard;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.theta.xi.thetaboard.datacontainers.BoardPostInformation;

import java.util.ArrayList;

public class ViewBoardActivity extends AppCompatActivity implements View.OnClickListener {

    public ViewBoardActivity() {
    }

    FloatingActionButton create_post_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_board_activity);

        // TODO: replace with a request (more likely a call to a requests class)
        String[] titles = {"Apple", "Banana", "Cherry", "Dates", "Elderberry", "Fig", "Grape", "Hackberry", "Indian Plum", "Juniper", "Kiwi", "Lemon", "Mango", "Nectarine"};
        ArrayList<BoardPostInformation> posts = new ArrayList<>();
        for(int i = 0; i < titles.length; i++) {
            posts.add(new BoardPostInformation(titles[i], "Content" + i, "author" +i , "date"+ i));
        }

        RecyclerView recyclerView = findViewById(R.id.board_post_list_container);
        create_post_button = findViewById(R.id.create_post_button);
        create_post_button.setOnClickListener(this);

        BoardPostsRecyclerAdapter adapter = new BoardPostsRecyclerAdapter(posts);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(
                recyclerView.getContext(),
                layoutManager.getOrientation()
        );
        recyclerView.addItemDecoration(dividerItemDecoration);

        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        if(v == create_post_button){
            try {
                Intent intent = new Intent(this, CreatePostActivity.class);
                startActivity(intent);
            } catch (Exception e){
                Log.d("TAG", "onClick: alksdfjalksd");
            }
        }
    }
}
