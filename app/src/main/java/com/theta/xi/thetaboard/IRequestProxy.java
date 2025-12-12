package com.theta.xi.thetaboard;

import com.theta.xi.thetaboard.datacontainers.BoardInformation;
import com.theta.xi.thetaboard.datacontainers.BoardPostInformation;
import com.theta.xi.thetaboard.datacontainers.InviteResponse;
import com.theta.xi.thetaboard.datacontainers.MemberInformation;

import java.util.ArrayList;

public interface IRequestProxy {

    String authToken = "";

    // registers an account
    // Return true/false for success/failure
    public Boolean register(String email, String password);

    // login to account
    // Return true/false for success/failure
    // should set the auth token and keep it in the proxy class
    public Boolean login(String email, String password);

    // login out of account
    // Return true/false for success/failure
    // should set the auth token to ""
    public Boolean logout();

    // This is used to check if the current session should be logged out or not
    // Return true/false for valid/invalid
    public Boolean sessionValid();

    // Used to retrieve all boards that the user is in.
    public ArrayList<BoardInformation> getAllBoardsForUser();

    // Used to retrieve all boards that the user is in. Should only return something if the user is authorized to view the board
    public ArrayList<BoardPostInformation> getAllPostsForBoard(int boardID);

    // Used to retrieve all members that are in a board. Should only work if they are a member of the board
    public ArrayList<MemberInformation> getAllMembersForBoard(int boardID);

    // Used to post on a board.
    // Should only be allowed if the user has the right to do so.
    public Boolean postOnBoard(int boardID, String title, String body);

    // Used to kick a user, should only work if the user is an admin
    // Return true/false for success/failure
    public Boolean kickUser(int boardID, String email);

    // Used to send an invitation to a person over email, which contains a join code that they can enter, that will only work on their account.
    // The recipient would not necessarily need to have registered an account already.
    // Only an admin of the board should be able to send an invitation
    // We can change this if it that is not how we want to do that.
    // Return true/false for success/failure
    InviteResponse inviteUser(int boardID);

    // Used to leave a board
    // Return true/false for success/failure
    public Boolean leaveBoard(int boardID);

    // Used to join a board
    // The server should make sure that the invitation is associated with that account
    // Return true/false for success/failure
    public Boolean joinBoard(String joinCode);

    // Used to set display name
    // Return true/false for success/failure
    public Boolean setDisplayName(String displayName);

}