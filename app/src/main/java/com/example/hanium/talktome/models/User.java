package com.example.hanium.talktome.models;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by yerim on 2017-09-27.
 */

@IgnoreExtraProperties
public class User {

    private String uid;
    private String username;
    private String email;

    private String facebookAccesstoken;
    private String twitterAccesstoken;
    private String gmailAccesstoken;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String uid, String username, String email) {
        this.uid = uid;
        this.username = username;
        this.email = email;
        this.facebookAccesstoken = "null";
        this.twitterAccesstoken = "null";
        this.gmailAccesstoken = "null";
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFacebookAccesstoken() {
        return facebookAccesstoken;
    }

    public void setFacebookAccesstoken(String facebookAccesstoken) {
        this.facebookAccesstoken = facebookAccesstoken;
    }

    public String getTwitterAccesstoken() {
        return twitterAccesstoken;
    }

    public void setTwitterAccesstoken(String twitterAccesstoken) {
        this.twitterAccesstoken = twitterAccesstoken;
    }

    public String getGmailAccesstoken() {
        return gmailAccesstoken;
    }

    public void setGmailAccesstoken(String gmailAccesstoken) {
        this.gmailAccesstoken = gmailAccesstoken;
    }

    @Override
    public String toString() {
        return "User{" +
                "uid='" + uid + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", facebookAccesstoken='" + facebookAccesstoken + '\'' +
                ", twitterAccesstoken='" + twitterAccesstoken + '\'' +
                ", gmailAccesstoken='" + gmailAccesstoken + '\'' +
                '}';
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("uid", this.uid);
        result.put("username", this.username);
        result.put("email", this.email);
        result.put("facebookAccesstoken", this.facebookAccesstoken);
        result.put("twitterAccesstoken", this.twitterAccesstoken);
        result.put("gmailAccesstoken", this.gmailAccesstoken);

        return result;
    }
}
