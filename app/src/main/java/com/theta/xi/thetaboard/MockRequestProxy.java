package com.theta.xi.thetaboard;

import com.theta.xi.thetaboard.datacontainers.BoardInformation;
import com.theta.xi.thetaboard.datacontainers.BoardPostInformation;
import com.theta.xi.thetaboard.datacontainers.InviteResponse;
import com.theta.xi.thetaboard.datacontainers.MemberInformation;

import java.util.ArrayList;
import java.util.Arrays;

public class MockRequestProxy implements IRequestProxy {

    @Override
    public Boolean register(String email, String password) {
        return true;
    }

    @Override
    public Boolean login(String email, String password) {
        return true;
    }

    @Override
    public Boolean logout() {
        return true;
    }

    private boolean isSessionValid = false;
    private ArrayList<BoardInformation> boards = new ArrayList<>();

    private java.util.Map<Integer, ArrayList<BoardPostInformation>> postsMap = new java.util.HashMap<>();

    public MockRequestProxy() {
        boards.add(new BoardInformation("General Announcements", true, true, 1));
        boards.add(new BoardInformation("Project Updates", false, true, 2));
        boards.add(new BoardInformation("Random", false, false, 3));

        // Initialize posts for board 1
        ArrayList<BoardPostInformation> board1Posts = new ArrayList<>();
        board1Posts.add(new BoardPostInformation("Welcome!", "Welcome to the board.", "admin", "2025-01-01"));
        board1Posts.add(new BoardPostInformation("Update", "We are making progress.", "user1", "2025-01-02"));
        postsMap.put(1, board1Posts);
    }

    // ...

    @Override
    public ArrayList<BoardPostInformation> getAllPostsForBoard(int boardID) {
        if (postsMap.containsKey(boardID)) {
            return postsMap.get(boardID);
        }
        return new ArrayList<>();
    }

    // ...

    @Override
    public Boolean postOnBoard(int boardID, String title, String body) {
        if (!postsMap.containsKey(boardID)) {
            postsMap.put(boardID, new ArrayList<>());
        }
        postsMap.get(boardID).add(new BoardPostInformation(title, body, "currentUser", "2025-01-01"));
        return true;
    }

    public void setSessionValid(boolean sessionValid) {
        isSessionValid = sessionValid;
    }

    @Override
    public Boolean sessionValid() {
        return isSessionValid;
    }

    @Override
    public ArrayList<BoardInformation> getAllBoardsForUser() {
        return boards;
    }

    // ... (other methods)

    @Override
    public Boolean joinBoard(String joinCode) {
        boards.add(new BoardInformation("Joined Board " + joinCode, false, false, boards.size() + 1));
        return true;
    }

    // ...

    @Override
    public Boolean createBoard(String name, String description) {
        boards.add(new BoardInformation(name, true, true, boards.size() + 1));
        return true;
    }

    @Override
    public ArrayList<MemberInformation> getAllMembersForBoard(int boardID) {
        ArrayList<MemberInformation> members = new ArrayList<>();
        members.add(new MemberInformation("admin@example.com", "AdminUser"));
        members.add(new MemberInformation("user1@example.com", "UserOne"));
        return members;
    }

    @Override
    public Boolean kickUser(int boardID, String email) {
        return true;
    }

    @Override
    public InviteResponse inviteUser(int boardID) {
        return new InviteResponse(true, "MOCK-CODE-123", "Invite sent successfully");
    }

    @Override
    public Boolean leaveBoard(int boardID) {
        return true;
    }

    @Override
    public Boolean setDisplayName(String displayName) {
        return true;
    }
}
