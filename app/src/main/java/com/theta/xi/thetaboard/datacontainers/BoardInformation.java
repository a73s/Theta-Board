package com.theta.xi.thetaboard.datacontainers;

public class BoardInformation {
    public final int boardID;
    public final String name;
    public final Boolean userIsAdmin;
    public final Boolean userIsPoster;

    public BoardInformation(String name, Boolean userIsAdmin, Boolean userIsPoster, int boardID){
        this.name = name;
        this.userIsAdmin = userIsAdmin;
        this.userIsPoster = userIsPoster;
        this.boardID = boardID;
    }

    public BoardInformation(){
        name = "Placeholder Title";
        userIsAdmin = false;
        userIsPoster = false;
        this.boardID = 0;
    }
}
