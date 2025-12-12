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
    private ArrayList<BoardPostInformation> boardPosts;

    public static class PostHolder extends RecyclerView.ViewHolder {
        BoardPostInformation post;
        final TextView title;
        final TextView body;
        final TextView author;
        final TextView date;

        public PostHolder(View view) {
            super(view);
            title = view.findViewById(R.id.board_post_title);
            body = view.findViewById(R.id.board_post_body);
            author = view.findViewById(R.id.board_post_author);
            date = view.findViewById(R.id.board_post_date);
        }

        public void setPost(BoardPostInformation newPost){
            post = newPost;
            title.setText(post.title);
            body.setText(post.body);
            author.setText(post.author);
            date.setText(post.date);
        }
    }

    public BoardPostsRecyclerAdapter(ArrayList<BoardPostInformation> dataSet) {
        boardPosts = dataSet;
    }

    public void updatePosts(ArrayList<BoardPostInformation> newPosts) {
        boardPosts = newPosts;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PostHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.bullatin_boards_post_item, viewGroup, false);

        return new PostHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostHolder holder, int position) {
        holder.setPost(boardPosts.get(position));
    }

    @Override
    public int getItemCount() {
        return boardPosts.size();
    }
}