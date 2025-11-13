package com.theta.xi.thetaboard.datacontainers;

public class BoardInformation {
    public final int boardID;
    public final String name;
    public final Boolean userIsAdmin;

    public BoardInformation(String name, Boolean userIsAdmin, int boardID){
        this.name = name;
        this.userIsAdmin = userIsAdmin;
        this.boardID = boardID;
    }

    public BoardInformation(){
        name = "Placeholder Title";
        userIsAdmin = false;
        this.boardID = 0;
    }
}
