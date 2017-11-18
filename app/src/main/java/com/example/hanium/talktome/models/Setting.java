package com.example.hanium.talktome.models;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by yerim on 2017-10-07.
 */

public class Setting {

    private String uid;
    private int rssFields;
    private int keywords;
    private int priorityGroup;
    private String startNotiTime;
    private String endNotiTime;

    public Setting() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Setting(String uid) {
        this.uid = uid;
        this.rssFields = 0;
        this.keywords = 0;
        this.priorityGroup = 0;
    }

    public void addRssField(String rss) {
        this.rssFields++;
    }

    public void addKeyword(String kw) {
        this.keywords++;
    }

    public void addPriorityGroup(String person) {
        this.priorityGroup++;
    }

    public void setNotiTimes(String startNotiTime, String endNotiTime) {
        this.startNotiTime = startNotiTime;
        this.endNotiTime = endNotiTime;
    }

    public String getUid() {
        return uid;
    }

    public int getRssField() {
        return rssFields;
    }

    public int getKeyword() {
        return keywords;
    }

    public int getPriorityGroup() {
        return priorityGroup;
    }

    public String getStartNotiTime() {
        return startNotiTime;
    }

    public String getEndNotiTime() {
        return endNotiTime;
    }

    @Override
    public String toString() {
        return "Setting{" +
                "uid='" + uid + '\'' +
                ", rssField=" + rssFields +
                ", keyword=" + keywords +
                ", priorityGroup=" + priorityGroup +
                ", startNotiTime='" + startNotiTime + '\'' +
                ", endNotiTime='" + endNotiTime + '\'' +
                '}';
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("uid", uid);
        result.put("rssField", String.valueOf(rssFields));
        result.put("keyword", String.valueOf(keywords));
        result.put("priorityGroup", String.valueOf(priorityGroup));
        result.put("startNotiTime", startNotiTime);
        result.put("endNotiTime", endNotiTime);

        return result;
    }
}
