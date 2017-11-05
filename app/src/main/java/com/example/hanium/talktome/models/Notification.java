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
    //public String author;
    public String nid;
    public String title;
    public String content;
    public String url;
    public String date;

    public Notification() {

    }

    public Notification(String uid, String nid, String title, String content, String url, SimpleDateFormat date) {
        this.uid = uid;
        this.nid = nid;
        this.title = title;
        this.content = content;
        this.url = url;

        Date from = new Date();
        this.date = date.format(from);
    }

    public boolean isEmpty() {
        boolean isempty = false;
        if (this.uid == null || this.nid == null || this.title == null || this.content == null || this.url == null || this.date == null)
            isempty = true;

        return isempty;
    }

    // [START post_to_map]
    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("uid", this.uid);
        //result.put("author", author);
        result.put("nid", this.nid);
        result.put("title", this.title);
        result.put("content", this.content);
        result.put("url", this.url);
        result.put("date", this.date);

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
                ", date=" + date +
                '}';
    }

    public String getContents() {
        return date+"\n"+title+"\n"+content+"\n"+url;
    }
}
