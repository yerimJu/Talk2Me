package com.example.hanium.talktome.models;

import com.google.firebase.database.Exclude;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yerim on 2017-10-07.
 */

public class Setting {

    public String sid;
    public List<String> keyword = new ArrayList<String>();
    public List<String> priorityGroup = new ArrayList<String>();
    public String startNotiTime = "NULL";
    public String endNotiTime = "NULL";

    public Setting(String sid, List<String> keyword, List<String> priorityGroup, String startNotiTime, String endNotiTime) {
        this.sid = sid;
        this.keyword = keyword;
        this.priorityGroup = priorityGroup;
        this.startNotiTime = startNotiTime;
        this.endNotiTime = endNotiTime;
    }

    public void addKeyword(String kw) {
        this.keyword.add(kw);
    }

    public void addPriorityGroup(String person) {
        this.priorityGroup.add(person);
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("sid", this.sid);

        if (!this.keyword.isEmpty())
            result.put("keyword", this.keyword);
        if (!this.priorityGroup.isEmpty())
            result.put("priorityGroup", this.priorityGroup);
        if (!this.startNotiTime.equals("NULL"))
            result.put("startNotiTime", this.startNotiTime);
        if (!this.endNotiTime.equals("NULL"))
            result.put("endNotiTime", this.endNotiTime);

        return result;
    }
}
