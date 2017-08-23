package com.example.hanium.talktome;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.TabHost;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends TabActivity {

    // extendableListView에 관한 변수들

    private ExpandableListView expandableListView;
    private CustomExpandableListViewAdapter mCustomListViewAdapter;
    private ArrayList<String> parentList;
    private ArrayList<ChildListData> alarmsToCheck;
    private ArrayList<ChildListData> recommandedNews;
    private ArrayList<ChildListData> alarmsNotCheck;
    private HashMap<String, ArrayList<ChildListData>> childList; // parent-child 연결할 hashmap 변수


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Tab 활성화
        TabHost tabHost = getTabHost();

        TabHost.TabSpec spec1 = tabHost.newTabSpec("Tab1").setIndicator("Facebook");
        spec1.setContent(R.id.tab1);
        tabHost.addTab(spec1);

        TabHost.TabSpec spec2 = tabHost.newTabSpec("Tab2").setIndicator("Instagram");
        spec2.setContent(R.id.tab2);
        tabHost.addTab(spec2);

        TabHost.TabSpec spec3 = tabHost.newTabSpec("Tab3").setIndicator("Twitter");
        spec3.setContent(R.id.tab3);
        tabHost.addTab(spec3);

        // 0번째 tab 기본 활성화
        tabHost.setCurrentTab(0);

        // list에 항목 넣기
        parentList = new ArrayList<String>();
        parentList.add("확인 할 알림");
        parentList.add("추천 뉴스피드");
        parentList.add("선호하지 않는 알림함");

        ChildListData user1 = new ChildListData(getResources().getDrawable(R.drawable.pic_profile), "알림이 도착했습니다.");
        alarmsToCheck = new ArrayList<ChildListData>();
        alarmsToCheck.add(user1);

        recommandedNews = new ArrayList<ChildListData>();
        recommandedNews.add(user1);

        alarmsNotCheck = new ArrayList<ChildListData>();
        alarmsNotCheck.add(user1);

        // parent와 child를 hashmap으로 연결
        childList = new HashMap<String, ArrayList<ChildListData>>();
        childList.put(parentList.get(0), alarmsToCheck);
        childList.put(parentList.get(1), recommandedNews);
        childList.put(parentList.get(2), alarmsNotCheck);

        // expandablelistview, customadapter 연결 후 OnClickListener 선언
        expandableListView = (ExpandableListView)findViewById(R.id.expandableListView);
        mCustomListViewAdapter = new CustomExpandableListViewAdapter(this, parentList, childList);
        expandableListView.setAdapter(mCustomListViewAdapter);

        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                // 그룹이 열리면 실행
            }
        });
        expandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int groupPosition) {
                // 그룹이 닫히면 실행
            }
        });
        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                return false;
            }
        });


    }
    public void Btn_Setting(View view){
        Intent intent = new Intent(MainActivity.this, SettingActivity.class);
        startActivity(intent);
        finish();
    }
}
