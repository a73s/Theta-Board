package com.theta.xi.thetaboard.datacontainers;

import com.google.gson.annotations.SerializedName;

public class BoardPostInformation {
    //unused fields are kept for future dev
    @SerializedName("postId")
    public int postId;
    @SerializedName("boardId")
    public int boardId;
    @SerializedName("posterId")
    public int posterId;
    @SerializedName("posterUsername")
    public String author;
    @SerializedName("title")
    public String title;
    @SerializedName("body")
    public String body;
    @SerializedName("datePosted")
    public String date;

    public BoardPostInformation(String title, String body, String author, String date){
        this.title = title;
        this.body = body;
        this.author = author;
        this.date = date;
    }

    public BoardPostInformation(){
        title = "Placeholder Title";
        body = "Placeholder Body";
        author = "@author";
        date = "Date";
    }
}