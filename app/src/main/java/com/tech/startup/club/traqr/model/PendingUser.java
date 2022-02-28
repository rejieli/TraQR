package com.tech.startup.club.traqr.model;

import com.google.firebase.Timestamp;

public class PendingUser {

    //CLASS USED WHEN USER IS CREATED AND WAITING FOR EMAIL CONFORMATION
    private User user;
    private String code;
    private final Timestamp timeStamp;
    private String tempPassword;

    public PendingUser(User user, String code, String tempPassword) {
        this.user = user;
        this.code = code;
        this.timeStamp = Timestamp.now();
        this.tempPassword = tempPassword;
    }

    public User getUser() {
        return user;
    }

    public String getCode() {
        return code;
    }

    public String getTempPassword() {
        return tempPassword;
    }
}
