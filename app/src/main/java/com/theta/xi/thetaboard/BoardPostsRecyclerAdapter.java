package com.theta.xi.thetaboard;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.theta.xi.thetaboard.datacontainers.BoardPostInformation;

import java.util.ArrayList;

public class BoardPostsRecyclerAdapter extends RecyclerView.Adapter<BoardPostsRecyclerAdapter.PostHolder> {
    private ArrayList<BoardPostInformation> localDataSet;

    public static class PostHolder extends RecyclerView.ViewHolder {

        BoardPostInformation post;
        final TextView titleText;
        final TextView bodyText;
        final TextView authorText;
        final TextView dateText;

        public PostHolder(View view) {
            super(view);
            titleText = view.findViewById(R.id.board_post_title);
            bodyText = view.findViewById(R.id.board_post_body);
            authorText = view.findViewById(R.id.board_post_author);
            dateText = view.findViewById(R.id.board_post_date);
        }

        public void setPost(BoardPostInformation newPost){
            post = newPost;
            titleText.setText(post.title);
            bodyText.setText(post.body);
            authorText.setText(post.author);
            dateText.setText(post.date);
        }
    }

    public BoardPostsRecyclerAdapter(ArrayList<BoardPostInformation> dataSet) {
        localDataSet = dataSet;
    }

    @NonNull
    @Override
    public PostHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.bullatin_boards_post_item, viewGroup, false);

        return new PostHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostHolder holder, int position) {
        holder.setPost(localDataSet.get(position));
    }

    @Override
    public int getItemCount() {
        return localDataSet.size();
    }
}
