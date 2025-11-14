package com.theta.xi.thetaboard.datacontainers;

import android.provider.ContactsContract.CommonDataKinds.Email;

public class MemberInformation {
    final public String email;
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

