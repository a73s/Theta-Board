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

    // Used to post on a board.
    // Should only be allowed if the user has the right to do so.
    public Boolean postOnBoard(String authToken, int boardID, String title, String body);

    // Used to kick a user, should only work if the user is an admin
    // Return true/false for success/failure
    public Boolean kickUser(String authToken, int boardID, String email);

    // Used to send an invitation to a person over email, which contains a join code that they can enter, that will only work on their account.
    // The recipient would not necessarily need to have registered an account already.
    // Only an admin of the board should be able to send an invitation
    // We can change this if it that is not how we want to do that.
    // Return true/false for success/failure
    public Boolean inviteUser(String authToken, int boardID, String recipientEmail);

    // Used to leave a board
    // Return true/false for success/failure
    public Boolean leaveBoard(String authToken, int boardID);

    // Used to join a board
    // The server should make sure that the invitation is associated with that account
    // Return true/false for success/failure
    public Boolean joinBoard(String authToken, String joinCode);

    // Used to set display name
    // Return true/false for success/failure
    public Boolean setDisplayName(String authToken, String displayName);
}
