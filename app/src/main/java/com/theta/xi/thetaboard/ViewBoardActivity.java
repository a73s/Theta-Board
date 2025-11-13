package com.theta.xi.thetaboard;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.theta.xi.thetaboard.datacontainers.BoardPostInformation;

import java.util.ArrayList;

public class ViewBoardActivity extends AppCompatActivity {

    public ViewBoardActivity() {
    }

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
}
