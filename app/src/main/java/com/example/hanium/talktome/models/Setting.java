package com.example.hanium.talktome.models;

import com.google.firebase.database.Exclude;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yerim on 2017-10-07.
 */

public class Setting {

    private String uid;
    private List<String> rssFieldList;
    private List<String> keywordList;
    private List<String> priorityGroupList;
    private String startNotiTime;
    private String endNotiTime;

    public Setting() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Setting(String uid) {
        this.uid = uid;
        this.rssFieldList = new ArrayList<>();
        this.keywordList = new ArrayList<>();
        this.priorityGroupList = new ArrayList<>();
    }

    public void addRssFieldList(String rss) {
        this.rssFieldList.add(rss);
    }

    public void addKeywordList(String kw) {
        this.keywordList.add(kw);
    }

    public void addPriorityGroupList(String person) {
        this.priorityGroupList.add(person);
    }

    public void setNotiTimes(String startNotiTime, String endNotiTime) {
        this.startNotiTime = startNotiTime;
        this.endNotiTime = endNotiTime;
    }

    public String getUid() {
        return uid;
    }

    public Collection getRssFieldList() {
        return rssFieldList;
    }

    public Collection getKeywordList() {
        return keywordList;
    }

    public Collection getPriorityGroupList() {
        return priorityGroupList;
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
                ", rssFieldList=" + rssFieldList +
                ", keywordList=" + keywordList +
                ", priorityGroupList=" + priorityGroupList +
                ", startNotiTime='" + startNotiTime + '\'' +
                ", endNotiTime='" + endNotiTime + '\'' +
                '}';
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("uid", uid);
        result.put("rssFieldList", rssFieldList);
        result.put("keywordList", keywordList);
        result.put("priorityGroupList", priorityGroupList);
        result.put("startNotiTime", startNotiTime);
        result.put("endNotiTime", endNotiTime);

        return result;
    }
}
