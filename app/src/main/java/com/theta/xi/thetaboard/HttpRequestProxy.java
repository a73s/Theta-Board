package com.theta.xi.thetaboard;

import com.google.gson.annotations.SerializedName;
import com.theta.xi.thetaboard.datacontainers.BoardInformation;
import com.theta.xi.thetaboard.datacontainers.BoardPostInformation;
import com.theta.xi.thetaboard.datacontainers.InviteResponse;
import com.theta.xi.thetaboard.datacontainers.MemberInformation;
import com.theta.xi.thetaboard.datacontainers.LoginResponse;
import com.google.gson.Gson;
import okhttp3.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import android.util.Log;
public class HttpRequestProxy implements IRequestProxy {
    // Payload for login/register
    static class AuthPayload {
        @SerializedName("email")
        String email;
        @SerializedName("password")
        String password;
        @SerializedName("username")
        String username;

        AuthPayload(String email, String password) {
            this.email = email;
            this.password = password;
        }

        AuthPayload(String email, String username, String password) {
            this.email = email;
            this.username = username;
            this.password = password;
        }
    }

    // Payload for posting to board
    static class PostPayload {
        @SerializedName("title")
        String title;
        @SerializedName("body")
        String body;

        PostPayload(String title, String body) {
            this.title = title;
            this.body = body;
        }
    }

    // Payload for kicking/inviting to board
    static class KickPayload {
        @SerializedName("email")
        String email;

        KickPayload(String email) {
            this.email = email;
        }
    }

    // Payload for joining board
    static class JoinPayload {
        @SerializedName("joinCode")
        String code;

        JoinPayload(String code) {
            this.code = code;
        }
    }

    // Payload for updating display name
    static class DisplayNamePayload {

        @SerializedName("displayName")
        String displayName;

        DisplayNamePayload(String displayName) {
            this.displayName = displayName;
        }
    }

    static class createBoardPayload{
        @SerializedName("boardName")
        String boardName;
        @SerializedName("boardDescription")
        String boardDesc;
        createBoardPayload(String boardName, String boardDesc) {
            this.boardName=boardName;
            this.boardDesc=boardDesc;
        }
    }
    //Helpers
    private String getEmailPrefix(String email) {
        if (email == null) {
            return "";
            //server will automatically reject this
        }
        int subStr = email.indexOf('@');
        if (subStr == -1) {
            return "";
            //server will automatically reject this
        }
        return email.substring(0, subStr);
    }

    private static final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    private final String baseUrl;
    private final OkHttpClient client = new OkHttpClient();
    private final Gson gson = new Gson();
    private String authToken = "";

    private static IRequestProxy instance = null;

    public static IRequestProxy getProxy() {
        if (instance == null) {
            instance = new HttpRequestProxy("http://10.0.2.2:8080/api"); // Placeholder ip/domain
        }
        return instance;
    }

    public HttpRequestProxy(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    // Build request for authorization header
    //this is done
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
                Log.d("postJson", baseUrl + path);
                return response.body().string();
            } else {
                Log.d("failed post request", "onCreate method called.");
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
    //not needed
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
    //this is done
    @Override
    public Boolean register(String email, String password) {
        try {
            AuthPayload payload = new AuthPayload(email, getEmailPrefix(email), password);
            String resp = postJson("/auth/register", payload);
            Log.d("HttpRequestProxy", "Login response: " + resp);
            return resp != null && resp.contains("201");
        } catch (Exception e) {
            return false;
        }
    }

    // login to account
    // Return true/false for success/failure
    // should set the auth token and keep it in the proxy class
    //this is done
    @Override
    public Boolean login(String username, String password) {
        try {
            AuthPayload payload = new AuthPayload(username, password);
            String resp = postJson("/auth/login", payload);

            if (resp == null) {
                return false;
            }

            LoginResponse json = gson.fromJson(resp, LoginResponse.class);
            if (json == null || json.token == null) {
                return false;
            }

            this.authToken = json.token;
            Log.d("Login Cookie", this.authToken);
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
    //this should be done needs to be implemented case in which after theme is switched the user is "logged out"
    @Override
    public Boolean sessionValid() {
        try {
            String resp = get("/auth/validate");
            return resp != null && resp.contains("true");
        } catch (Exception e) {
            return false;
        }
    }

    // Used to retrieve all boards that the user is in.
    //this is done
    @Override
    public ArrayList<BoardInformation> getAllBoardsForUser() {
        try {
            String resp = get("/board/all");
            BoardInformation[] arr = gson.fromJson(resp, BoardInformation[].class);
            for (BoardInformation board : arr) {
                board.setPermissions();
            }
            Log.d("getAllBoardsForUser", "Got boards");
            return new ArrayList<>(Arrays.asList(arr));
        } catch (Exception e) {
            Log.d("getAllBoardsForUser", "Failed to get boards: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    // Used to retrieve all boards that the user is in. Should only return something if the user is authorized to view the board
    //this works
    @Override
    public ArrayList<BoardPostInformation> getAllPostsForBoard(int boardID) {
        try {
            String resp = get("/board/" + boardID + "/posts");
            BoardPostInformation[] arr = gson.fromJson(resp, BoardPostInformation[].class);
            Log.d("getAllPostsForBoard", "Got posts for board");
            return new ArrayList<>(Arrays.asList(arr));
        } catch (Exception e) {
            Log.d("getAllPostsForBoard", "Failed to get post for board");
            return new ArrayList<>();
        }
    }

    // Used to retrieve all members that are in a board. Should only work if they are a member of the board
    // this is done
    @Override
    public ArrayList<MemberInformation> getAllMembersForBoard(int boardID) {
        try {
            String resp = get("/board/" + boardID + "/members");
            MemberInformation[] arr = gson.fromJson(resp, MemberInformation[].class);
            Log.d("getAllMembersForBoard", "Got members of board");
            return new ArrayList<>(Arrays.asList(arr));
        } catch (Exception e) {
            Log.d("getAllMembersForBoard", "Failed to get members of board");
            return new ArrayList<>();
        }
    }

    // Used to post on a board.
    // Should only be allowed if the user has the right to do so.
    //this is done
    @Override
    public Boolean postOnBoard(int boardID, String title, String body) {
        try {
            PostPayload payload = new PostPayload(title, body);
            String resp = postJson("/board/" + boardID + "/post", payload);
            return resp != null && resp.contains("true");
        } catch (Exception e) {
            return false;
        }
    }

    // Used to kick a user, should only work if the user is an admin
    // Return true/false for success/failure
    @Override
    public Boolean kickUser(int boardID, String email) {
        try {
            KickPayload payload = new KickPayload(email);
            String resp = postJson("/board/" + boardID + "/kick", payload);
            return resp != null && resp.contains("true");
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
    public InviteResponse inviteUser(int boardID) {
        try {
            String resp = postJson("/board/" + boardID + "/invite", new Object());
            if (resp == null) {
                return new InviteResponse(false, null, "Network error");
            }
            InviteResponse response = gson.fromJson(resp, InviteResponse.class);
            return response != null ? response : new InviteResponse(false, null, "Invalid response");
        } catch (Exception e) {
            Log.e("HttpRequestProxy", "Failed to invite user", e);
            return new InviteResponse(false, null, "Error: " + e.getMessage());
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
            String resp = postJson("/board/join", payload);
            return resp != null && resp.contains("true");
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

    @Override
    public Boolean createBoard(String name, String description) {
        try {
            createBoardPayload payload = new createBoardPayload(name,description);
            String resp = postJson("/board/create",payload);
            return resp != null && resp.contains("boardId");
        }catch(Exception e) {
            return false;
        }
    }
    //Used to get username

}

