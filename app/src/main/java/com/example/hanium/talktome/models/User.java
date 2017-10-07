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

    public String username;
    public String email;

    private String facebookAccesstoken = "NULL";
    private String twitterAccesstoken = "NULL";
    private String gmailAccesstoken = "NULL";

    public Setting setting;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String username, String email) {
        this.username = username;
        this.email = email;
    }

    public void setFacebookAccesstoken(String acstoken) { this.facebookAccesstoken = acstoken; }
    public void setTwitterAccesstoken(String acstoken) {
        this.twitterAccesstoken = acstoken;
    }
    public void setGmailAccesstoken(String acstoken) {
        this.gmailAccesstoken = acstoken;
    }
    public String getFacebookAccesstoken() {
        return this.facebookAccesstoken;
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

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("username", this.username);
        result.put("email", this.email);
        if (this.facebookAccesstoken.equals("NULL"))
            result.put("facebookAccesstoken",this.facebookAccesstoken);
        if (this.twitterAccesstoken.equals("NULL"))
            result.put("twitterAccesstoken",this.twitterAccesstoken);
        if (this.gmailAccesstoken.equals("NULL"))
            result.put("gmailAccesstoken",this.gmailAccesstoken);

        return result;
    }
}
