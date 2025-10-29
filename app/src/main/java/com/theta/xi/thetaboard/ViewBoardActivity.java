package com.theta.xi.thetaboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ViewBoardActivity extends AppCompatActivity {

    public ViewBoardActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_board_activity);

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
