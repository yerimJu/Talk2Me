package com.example.hanium.talktome.models;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by yerim on 2017-09-27.
 */

@IgnoreExtraProperties
public class User {

    public String username;
    public String email;

    private String facebookAccesstoken = "NONE";
    private String twitterAccesstoken = "NONE";
    private String gmailAccesstoken = "NONE";


    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String username, String email) {
        this.username = username;
        this.email = email;
    }

    public void setFacebookAccesstoken(String acstoken) {
        this.facebookAccesstoken = acstoken;
    }
    public void setTwitterAccesstoken(String acstoken) {
        this.twitterAccesstoken = acstoken;
    }
    public void setGmailAccesstoken(String acstoken) {
        this.gmailAccesstoken = acstoken;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", facebookAccesstoken='" + facebookAccesstoken + '\'' +
                ", twitterAccesstoken='" + twitterAccesstoken + '\'' +
                ", gmailAccesstoken='" + gmailAccesstoken + '\'' +
                '}';
    }
}
