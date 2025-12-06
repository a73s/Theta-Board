package com.theta.xi.thetaboard;

import com.theta.xi.thetaboard.datacontainers.BoardInformation;
import com.theta.xi.thetaboard.datacontainers.BoardPostInformation;
import com.theta.xi.thetaboard.datacontainers.MemberInformation;
import com.theta.xi.thetaboard.datacontainers.LoginResponse;
import com.google.gson.Gson;
import okhttp3.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class HttpRequestProxy implements IRequestProxy {
    // Payload for login/register
    static class AuthPayload {
        String email;
        String password;

        AuthPayload(String email, String password) {
            this.email = email;
            this.password = password;
        }
    }

    // Payload for posting to board
    static class PostPayload {
        String title;
        String body;

        PostPayload(String title, String body) {
            this.title = title;
            this.body = body;
        }
    }

    // Payload for kicking/inviting to board
    static class EmailPayload {
        String email;

        EmailPayload(String email) {
            this.email = email;
        }
    }

    // Payload for joining board
    static class JoinPayload {
        String code;

        JoinPayload(String code) {
            this.code = code;
        }
    }

    // Payload for updating display name
    static class DisplayNamePayload {
        String displayName;

        DisplayNamePayload(String displayName) {
            this.displayName = displayName;
        }
    }

    private static final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    private final String baseUrl;
    private final OkHttpClient client = new OkHttpClient();
    private final Gson gson = new Gson();
    private String authToken = "";

    private static IRequestProxy instance = null;
    public static IRequestProxy getProxy(){
        if(instance == null){
            instance = new HttpRequestProxy("192.168.1.100"); // Placeholder ip/domain
        }
        return instance;
    }

    public HttpRequestProxy(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    // Build request for authorization header
    private Request.Builder authorizedRequest(String url) {
        Request.Builder builder = new Request.Builder().url(url);
        if (!authToken.isEmpty()) {
            builder.addHeader("Authorization", "Bearer " + authToken);
        }
        return builder;
    }

    // Send POST requests
    private String postJson(String path, Object bodyObj) throws IOException {
        String json = gson.toJson(bodyObj);
        RequestBody body = RequestBody.create(json, JSON);

        Request request = authorizedRequest(baseUrl + path)
                .post(body)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.body() != null) {
                return response.body().string();
            } else {
                return null;
            }
        }
    }

    // Send GET requests
    private String get(String path) throws IOException {
        Request request = authorizedRequest(baseUrl + path)
                .get()
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.body() != null) {
                return response.body().string();
            } else {
                return null;
            }
        }
    }

    // Send DELETE requests
    private String delete(String path) throws IOException {
        Request request = authorizedRequest(baseUrl + path)
                .delete()
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.body() != null) {
                return response.body().string();
            } else {
                return null;
            }
        }
    }

    // registers an account
    // Return true/false for success/failure
    @Override
    public Boolean register(String email, String password) {
        try {
            AuthPayload payload = new AuthPayload(email, password);
            String resp = postJson("/auth/register", payload);
            return resp != null && resp.contains("success");
        } catch (Exception e) {
            return false;
        }
    }

    // login to account
    // Return true/false for success/failure
    // should set the auth token and keep it in the proxy class
    @Override
    public Boolean login(String email, String password) {
        try {
            AuthPayload payload = new AuthPayload(email, password);
            String resp = postJson("/auth/login", payload);

            if (resp == null) {
                return false;
            }

            LoginResponse json = gson.fromJson(resp, LoginResponse.class);
            if (json == null || json.token == null) {
                return false;
            }

            this.authToken = json.token;
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // login out of account
    // Return true/false for success/failure
    // should set the auth token to ""
    @Override
    public Boolean logout() {
        try {
            get("/auth/logout"); // optional server endpoint
            this.authToken = "";
            return true;
        } catch (Exception e) {
            this.authToken = "";
            return false;
        }
    }

    // This is used to check if the current session should be logged out or not
    // Return true/false for success/failure
    @Override
    public Boolean sessionValid() {
        try {
            String resp = get("/auth/validate");
            return resp != null && resp.contains("valid");
        } catch (Exception e) {
            return false;
        }
    }

    // Used to retrieve all boards that the user is in.
    @Override
    public ArrayList<BoardInformation> getAllBoardsForUser() {
        try {
            String resp = get("/boards/mine");
            BoardInformation[] arr = gson.fromJson(resp, BoardInformation[].class);
            return new ArrayList<>(Arrays.asList(arr));
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    // Used to retrieve all boards that the user is in. Should only return something if the user is authorized to view the board
    @Override
    public ArrayList<BoardPostInformation> getAllPostsForBoard(int boardID) {
        try {
            String resp = get("/boards/" + boardID + "/posts");
            BoardPostInformation[] arr = gson.fromJson(resp, BoardPostInformation[].class);
            return new ArrayList<>(Arrays.asList(arr));
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    // Used to retrieve all members that are in a board. Should only work if they are a member of the board
    @Override
    public ArrayList<MemberInformation> getAllMembersForBoard(int boardID) {
        try {
            String resp = get("/boards/" + boardID + "/members");
            MemberInformation[] arr = gson.fromJson(resp, MemberInformation[].class);
            return new ArrayList<>(Arrays.asList(arr));
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    // Used to post on a board.
    // Should only be allowed if the user has the right to do so.
    @Override
    public Boolean postOnBoard(int boardID, String title, String body) {
        try {
            PostPayload payload = new PostPayload(title, body);
            String resp = postJson("/boards/" + boardID + "/post", payload);
            return resp != null && resp.contains("success");
        } catch (Exception e) {
            return false;
        }
    }

    // Used to kick a user, should only work if the user is an admin
    // Return true/false for success/failure
    @Override
    public Boolean kickUser(int boardID, String email) {
        try {
            EmailPayload payload = new EmailPayload(email);
            String resp = postJson("/boards/" + boardID + "/kick", payload);
            return resp != null && resp.contains("success");
        } catch (Exception e) {
            return false;
        }
    }

    // Used to send an invitation to a person over email, which contains a join code that they can enter, that will only work on their account.
    // The recipient would not necessarily need to have registered an account already.
    // Only an admin of the board should be able to send an invitation
    // We can change this if it that is not how we want to do that.
    // Return true/false for success/failure
    @Override
    public Boolean inviteUser(int boardID, String recipientEmail) {
        try {
            EmailPayload payload = new EmailPayload(recipientEmail);
            String resp = postJson("/boards/" + boardID + "/invite", payload);
            return resp != null && resp.contains("success");
        } catch (Exception e) {
            return false;
        }
    }

    // Used to leave a board
    // Return true/false for success/failure
    @Override
    public Boolean leaveBoard(int boardID) {
        try {
            String resp = delete("/boards/" + boardID + "/leave");
            return resp != null && resp.contains("success");
        } catch (Exception e) {
            return false;
        }
    }

    // Used to join a board
    // The server should make sure that the invitation is associated with that account
    // Return true/false for success/failure
    @Override
    public Boolean joinBoard(String joinCode) {
        try {
            JoinPayload payload = new JoinPayload(joinCode);
            String resp = postJson("/boards/join", payload);
            return resp != null && resp.contains("success");
        } catch (Exception e) {
            return false;
        }
    }

    // Used to set display name
    // Return true/false for success/failure
    @Override
    public Boolean setDisplayName(String displayName) {
        try {
            DisplayNamePayload payload = new DisplayNamePayload(displayName);
            String resp = postJson("/user/display-name", payload);
            return resp != null && resp.contains("success");
        } catch (Exception e) {
            return false;
        }
    }
}

