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

    @Override
    public Boolean sessionValid() {
        return true;
    }

    @Override
    public ArrayList<BoardInformation> getAllBoardsForUser() {
        ArrayList<BoardInformation> boards = new ArrayList<>();
        boards.add(new BoardInformation("General Announcements", true, true, 1));
        boards.add(new BoardInformation("Project Updates", false, true, 2));
        boards.add(new BoardInformation("Random", false, false, 3));
        return boards;
    }

    @Override
    public ArrayList<BoardPostInformation> getAllPostsForBoard(int boardID) {
        ArrayList<BoardPostInformation> posts = new ArrayList<>();
        posts.add(new BoardPostInformation("Welcome!", "Welcome to the board.", "admin", "2025-01-01"));
        posts.add(new BoardPostInformation("Update", "We are making progress.", "user1", "2025-01-02"));
        return posts;
    }

    @Override
    public ArrayList<MemberInformation> getAllMembersForBoard(int boardID) {
        ArrayList<MemberInformation> members = new ArrayList<>();
        members.add(new MemberInformation("admin@example.com", "AdminUser"));
        members.add(new MemberInformation("user1@example.com", "UserOne"));
        return members;
    }

    @Override
    public Boolean postOnBoard(int boardID, String title, String body) {
        return true;
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
    public Boolean joinBoard(String joinCode) {
        return true;
    }

    @Override
    public Boolean setDisplayName(String displayName) {
        return true;
    }

    @Override
    public Boolean createBoard(String name, String description) {
        return true;
    }
}
