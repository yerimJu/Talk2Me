package com.example.hanium.talktome;

import android.app.TabActivity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.TabHost;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity{

    // extendableListView에 관한 변수들

    private ExpandableListView expandableListView;
    private CustomExpandableListViewAdapter mCustomListViewAdapter;
    private ArrayList<String> parentList;
    private ArrayList<ChildListData> alarmsToCheck;
    private ArrayList<ChildListData> recommandedNews;
    private ArrayList<ChildListData> alarmsNotCheck;
    private HashMap<String, ArrayList<ChildListData>> childList; // parent-child 연결할 hashmap 변수


    //액션버튼 메뉴 액션바에 집어 넣기
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);

    }

    //액션버튼을 클릭했을때의 동작
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        switch (id){
            case R.id.action_setting:
                Intent intent = new Intent(MainActivity.this, SettingActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_logout:
                FirebaseAuth.getInstance().signOut();

                Intent intent2 = new Intent(MainActivity.this, Login2Activity.class);
                startActivity(intent2);
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ChildListData user1 = new ChildListData(getResources().getDrawable(R.drawable.icon_facebook), "알림1이 도착했습니다.");
        ChildListData user2 = new ChildListData(getResources().getDrawable(R.drawable.icon_gmail), "알림2가 도착했습니다.");
        ChildListData user3 = new ChildListData(getResources().getDrawable(R.drawable.icon_rss), "알림3이 도착했습니다.");
        ChildListData user4 = new ChildListData(getResources().getDrawable(R.drawable.icon_twitter), "알림4이 도착했습니다.");

        alarmsToCheck = new ArrayList<ChildListData>();
        alarmsToCheck.add(user1);
        alarmsToCheck.add(user2);
        alarmsToCheck.add(user3);

        recommandedNews = new ArrayList<ChildListData>();
        recommandedNews.add(user2);
        recommandedNews.add(user3);

        alarmsNotCheck = new ArrayList<ChildListData>();
        alarmsNotCheck.add(user4);

        // list에 항목 넣기
        parentList = new ArrayList<String>();
        parentList.add("    확인 할 알림 ("+alarmsToCheck.size()+")");
        parentList.add("    추천 뉴스피드 ("+recommandedNews.size()+")");
        parentList.add("    선호하지 않는 알림함 ("+alarmsNotCheck.size()+")");

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
}
