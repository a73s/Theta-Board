package com.theta.xi.thetaboard.datacontainers;

import android.provider.ContactsContract.CommonDataKinds.Email;

import com.google.gson.annotations.SerializedName;

public class MemberInformation {
    @SerializedName("email")
    final public String email;
    @SerializedName("username")
    final public String nickname;

    public MemberInformation() {
        this.email = "johndoe@example.com";
        this.nickname = "John Doe";
    }
    public MemberInformation(String email, String nickname) {
        this.email = email;
        this.nickname = nickname;
    }

}

