package com.theta.xi.thetaboard;

import com.theta.xi.thetaboard.datacontainers.BoardInformation;
import com.theta.xi.thetaboard.datacontainers.BoardPostInformation;
import com.theta.xi.thetaboard.datacontainers.MemberInformation;

import java.util.ArrayList;

public interface IRequestProxy {

    // This is used to tell the server to send a password or code to the user's email.
    // Return true/false for success/failure
    public Boolean initiateLogin(String email);

    // This is used to submit the password or code for authentication,
    // returns an authToken that can be sent to identify the session in the future, empty if failed
    // May add an email parameter
    public String completeLogin(String password);

    // This is used to check if the current session should be logged out or not
    // Return true/false for success/failure
    public Boolean sessionValid(String authToken);

    // Used to retrieve all boards that the user is in.
    public ArrayList<BoardInformation> getAllBoardsForUser(String authToken);

    // Used to retrieve all boards that the user is in. Should only return something if the user is authorized to view the board
    public ArrayList<BoardPostInformation> getAllPostsForBoard(String authToken, int boardID);

    // Used to retrieve all members that are in a board. Should only work if they are a member of the board
    public ArrayList<MemberInformation> getAllMembersForBoard(String authToken, int boardID);

    // Used to kick a user, should only work if the user is an admin
    // Return true/false for success/failure
    public Boolean kickUser(String authToken, String email);

    // Used to leave a board
    // Return true/false for success/failure
    public Boolean leaveBoard(String authToken, int boardID);

    // Used to set display name
    // Return true/false for success/failure
    public Boolean setDisplayName(String authToken, String displayName);
}
