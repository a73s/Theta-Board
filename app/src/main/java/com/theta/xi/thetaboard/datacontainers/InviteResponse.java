package com.theta.xi.thetaboard.datacontainers;

import com.google.gson.annotations.SerializedName;

public class InviteResponse {
    @SerializedName("success")
    public boolean success;
    @SerializedName("joinCode")
    public String joinCode;
    @SerializedName("message")
    public String message;

    public InviteResponse(boolean success, String joinCode, String message) {
        this.success = success;
        this.joinCode = joinCode;
        this.message = message;
    }

    public InviteResponse() {
        this.success = false;
        this.joinCode = null;
        this.message = "Invite failed";
    }
}