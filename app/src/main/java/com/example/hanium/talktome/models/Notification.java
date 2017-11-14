package com.example.hanium.talktome.models;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by yerim on 2017-09-27.
 */

@IgnoreExtraProperties
public class Notification {

    public String uid;
    public String nid;
    public String title;
    public String content;
    public String url;
    public String date;
    public String isRead;

    public Notification() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Notification(String uid, String nid) {
        this.uid = uid;
        this.nid = nid;
    }

    public Notification(String uid, String nid, String title, String content, String url, SimpleDateFormat date, boolean isRead) {
        this.uid = uid;
        this.nid = nid;
        this.title = title;
        this.content = content;
        this.url = url;

        Date from = new Date();
        this.date = date.format(from);

        this.isRead = String.valueOf(isRead);
    }

    public boolean isEmpty() {
        boolean isempty = false;
        if (this.uid == null || this.nid == null || this.title == null || this.content == null || this.url == null || this.date == null || this.isRead == null)
            isempty = true;

        return isempty;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getNid() {
        return nid;
    }

    public void setNid(String nid) {
        this.nid = nid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getIsRead() {
        return isRead;
    }

    public void setIsRead(String isRead) {
        this.isRead = isRead;
    }

    // [START post_to_map]
    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("uid", this.uid);
        result.put("nid", this.nid);
        result.put("title", this.title);
        result.put("content", this.content);
        result.put("url", this.url);
        result.put("date", this.date);
        result.put("isRead", this.isRead);

        return result;
    }
    // [END post_to_map]


    @Override
    public String toString() {
        return "Notification{" +
                "uid='" + uid + '\'' +
                ", nid='" + nid + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", url='" + url + '\'' +
                ", date='" + date + '\'' +
                ", isRead='" + isRead + '\'' +
                '}';
    }

    public String getContents() {
        return date + "\n" + title + "\n" + content + "\n" + url;
    }
}
