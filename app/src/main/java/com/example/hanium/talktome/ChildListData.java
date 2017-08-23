package com.example.hanium.talktome;

import android.graphics.drawable.Drawable;

/**
 * Created by yerimju on 2017-07-27.
 */
public class ChildListData {
    public String mChildText;
    public Drawable mChildItem;

    public ChildListData(Drawable d, String s) {
        this.mChildItem = d;
        this.mChildText = s;
    }
}
