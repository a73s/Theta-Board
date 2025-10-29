package com.theta.xi.thetaboard;

public class BoardPostInformation {

    public final String title;
    public final String body;
    public final String author;
    public final String date;

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
