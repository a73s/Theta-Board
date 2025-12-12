package com.theta.xi.thetaboard.datacontainers;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class BoardInformation implements Serializable {
    //adjust serialized name based off api responses
    @SerializedName("boardId")
    public int boardID;
    @SerializedName("boardName")
    public String name;
    @SerializedName("boardDescription")
    public String description;
    @SerializedName("creatorId")
    public int creatorId;

    public Boolean userIsAdmin;
    public Boolean userIsPoster;

    @SerializedName("accessLevel")
    private String accessLevel;

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
    //this will be useful for ui logic
    public void setPermissions() {
        if (accessLevel == null) {
            userIsAdmin = false;
            userIsPoster = false;
            return;
        }
        if(accessLevel.equalsIgnoreCase("moderate")) {
            userIsAdmin = true;
            userIsPoster = true;
        }
        if(accessLevel.equalsIgnoreCase("post")) {
            userIsAdmin = false;
            userIsPoster = true;
        }
        if(accessLevel.equalsIgnoreCase("read")) {
            userIsAdmin = false;
            userIsPoster = false;
        }
    }
}