package com.example.hanium.talktome;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TabActivity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.TabHost;
import android.widget.Toast;

import com.example.hanium.talktome.models.Notification;
import com.example.hanium.talktome.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    // for firebase database
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private DatabaseReference mNotificationReference;
    private DatabaseReference mUserReference;

    // extendableListView에 관한 변수들
    private ExpandableListView expandableListView;
    private CustomExpandableListViewAdapter mCustomListViewAdapter;
    private ArrayList<String> parentList;
    private ArrayList<ChildListData> alarmsToCheck;
    private ArrayList<ChildListData> recommandedNews;
    private ArrayList<ChildListData> alarmsNotCheck;
    private ArrayList<ChildListData> testArray;
    private ArrayList<ChildListData> PreviousAlarms;
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
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_setting:
                Intent intent = new Intent(MainActivity.this, SettingActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_logout:
                FirebaseAuth.getInstance().signOut();

                Intent intent2 = new Intent(MainActivity.this, SignInActivity.class);
                startActivity(intent2);
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private boolean isPrevious(String noti_time, String prev_time) throws ParseException {
        // 이전알림 조회 날짜 인지 체크
        // string -> date -> string
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd",java.util.Locale.getDefault());
        Date temp = dateFormat.parse(noti_time);
        String strDate = dateFormat.format(temp);

        return strDate.equals(prev_time);
    }

    private boolean isProhibit(String time) throws ParseException {
        // 방해금지 시간인지 체크 - true면 방해금지 시간, false면 방해금지 시간 아님(알람 o)
        // 현재 시간 구하기
        long now = System.currentTimeMillis();
        Date now_date = new Date(now);

        // 방해금지 시간 string -> date
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm", java.util.Locale.getDefault());
        Date proh_date = dateFormat.parse(time);

        // date -> string
        /*SimpleDateFormat dateFormat = new  SimpleDateFormat("yyyy-MM-dd HH:mm", java.util.Locale.getDefault());
        Date date = new Date();
        String strDate = dateFormat.format(date);*/

        // 시간 비교, proh_date가 now_date보다 이후 일때 true, 아니면 false
        return proh_date.after(now_date);
    }

    private void set_notification(String title, String content) {
        NotificationManager notificationManager = (NotificationManager) MainActivity.this.getSystemService(MainActivity.this.NOTIFICATION_SERVICE);
        Intent intent1 = new Intent(MainActivity.this.getApplicationContext(), MainActivity.class); //인텐트 생성.

        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext());
        intent1.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);//현재 액티비티를 최상으로 올리고, 최상의 액티비티를 제외한 모든 액티비티를없앤다.

        PendingIntent pendingNotificationIntent = PendingIntent.getActivity(MainActivity.this, 0, intent1, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setSmallIcon(R.drawable.icon_facebook).setTicker("HETT").setWhen(System.currentTimeMillis())
                .setContentTitle(title)
                .setContentText(content)
                .setDefaults(NotificationCompat.DEFAULT_SOUND | NotificationCompat.DEFAULT_VIBRATE)
                .setContentIntent(pendingNotificationIntent)
                .setAutoCancel(true)
                .setOngoing(true);
        //해당 부분은 API 4.1버전부터 작동합니다.

        notificationManager.notify(1, builder.build()); // Notification send
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mUser = mAuth.getCurrentUser();
        Log.d(TAG, "Hi, " + mUser.getEmail());
        final String userId = mUser.getUid();
        mNotificationReference = mDatabase.child("notifications").child(userId);

        // DB 정보가 담긴 array
        testArray = new ArrayList<ChildListData>();
        PreviousAlarms = new ArrayList<ChildListData>();

        // 실시간 DB를 위한 child event listener
        ChildEventListener notificationListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Log.d(TAG, "onChildAdded:" + dataSnapshot.getKey());

                Notification noti = dataSnapshot.getValue(Notification.class);
                Log.d(TAG, "I got a new notification :)\n" + noti.toString());
                testArray.add(new ChildListData(null, noti.getContents()));

                // 푸시알람이 오게 해야함
                try {

                    // 알림 받은 항목에 대해서는 알림을 보내지 않아야 함 - 추가로 기술 필요
                    // 방해 금지 날짜 받아오는 부분 추가 기술 필요
                    if(!isProhibit("2017-12-18 13:30"))
                        set_notification(noti.title, noti.content);

                    System.out.println("하이2 들어옴");
                    // 이전 알림 조회 날짜와 현재 날짜가 같다면 이전알림조회 탭에 추가
                    // 이전 알림 조회 날짜 받아오는 부분 추가 기술 필요
                    if(isPrevious(noti.date, "2017-11-14"))
                        PreviousAlarms.add(new ChildListData(null, noti.getContents()));

                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Log.d(TAG, "onChildChanged:" + dataSnapshot.getKey());
                // 사용되지 않을 항목
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Log.d(TAG, "onChildRemoved:" + dataSnapshot.getKey());

                Notification noti = dataSnapshot.getValue(Notification.class);
                Log.d(TAG, "This notification was removed :(\n" + noti.toString());
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                Log.d(TAG, "onChildRemoved:" + dataSnapshot.getKey());
                // 사용되지 않을 항목
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "postComments:onCancelled", databaseError.toException());
                Toast.makeText(MainActivity.this, "Failed to load notifications.",
                        Toast.LENGTH_SHORT).show();
            }
        };
        mNotificationReference.addChildEventListener(notificationListener);

        /* end firebase */

        ChildListData user1 = new ChildListData(getResources().getDrawable(R.drawable.icon_facebook), "알림1이 도착했습니다.");
        ChildListData user2 = new ChildListData(getResources().getDrawable(R.drawable.icon_gmail), "알림2가 도착했습니다.");
        ChildListData user3 = new ChildListData(getResources().getDrawable(R.drawable.icon_rss), "알림3이 도착했습니다.");
        ChildListData user4 = new ChildListData(getResources().getDrawable(R.drawable.icon_twitter), "알림4이 도착했습니다.");

        ChildListData user11 = new ChildListData(getResources().getDrawable(R.drawable.icon_facebook), "DB에 임의의 notification 추가하기");
        ChildListData user12 = new ChildListData(getResources().getDrawable(R.drawable.icon_gmail), "현재 user의 facebook 정보 불러오기");

        alarmsToCheck = new ArrayList<ChildListData>();
        alarmsToCheck.add(user1);
        alarmsToCheck.add(user2);
        alarmsToCheck.add(user3);

        recommandedNews = new ArrayList<ChildListData>();
        recommandedNews.add(user2);
        recommandedNews.add(user3);

        alarmsNotCheck = new ArrayList<ChildListData>();
        alarmsNotCheck.add(user11);
        alarmsNotCheck.add(user12);

        // list에 항목 넣기
        parentList = new ArrayList<String>();
        parentList.add("    확인 할 알림 (" + alarmsToCheck.size() + ")");
        parentList.add("    추천 뉴스피드 (" + recommandedNews.size() + ")");
        parentList.add("    테스트 전용 탭1 (" + alarmsNotCheck.size() + ")");
        parentList.add("    테스트 전용 탭2 (" + testArray.size() + ")");
        parentList.add("    이전 알림 조회 ("+PreviousAlarms.size()+")");

        // parent와 child를 hashmap으로 연결
        childList = new HashMap<String, ArrayList<ChildListData>>();
        childList.put(parentList.get(0), alarmsToCheck);
        childList.put(parentList.get(1), recommandedNews);
        childList.put(parentList.get(2), alarmsNotCheck);
        childList.put(parentList.get(3), testArray);
        childList.put(parentList.get(4), PreviousAlarms);

        // expandablelistview, customadapter 연결 후 OnClickListener 선언
        expandableListView = (ExpandableListView) findViewById(R.id.expandableListView);
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
                if (groupPosition == 2 && childPosition == 0) {
                    // 전체 개체를 다시 쓰지 않고도 하위 항목을 업데이트 하는 방법
                    // data 저장할 때 주의 : string이 아닌 자료형은 json으로 저장할 때 오류가 나므로 string 형변환 필요

                    String key = mDatabase.child("notifications").push().getKey();
                    String userId = mUser.getUid();

                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.KOREA);
                    Notification noti = new Notification(userId, key, "title", "contentsssss", "https://github.com/yerimJu/Talk2Me", simpleDateFormat, false);
                    Map<String, Object> notiValues = noti.toMap();

                    Map<String, Object> childUpdates = new HashMap<>();
                    childUpdates.put("/notifications/" + userId + "/" + key, notiValues);

                    mDatabase.updateChildren(childUpdates);
                    Log.d(TAG, "noti was saved :)\n" + noti.toString());

                    Toast.makeText(MainActivity.this, "New notification saved in DB",
                            Toast.LENGTH_SHORT).show();
                } else if (groupPosition == 2 && childPosition == 1) {
                    // user facebookaccesstoken 불러오기
                    mUserReference = mDatabase.child("users").child(mUser.getUid());
                    Query userQuery = mUserReference;
                    userQuery.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            User user = dataSnapshot.getValue(User.class);
                            String facebookAccesstoken = dataSnapshot.child("facebookAccesstoken").getValue().toString();

                            Log.d(TAG, "Current user information : "+ user);
                            Toast.makeText(MainActivity.this, "Your facebook access token : \n, "+facebookAccesstoken,
                                    Toast.LENGTH_SHORT).show();

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            // Getting Post failed, log a message
                            Log.w(TAG, "loadUser:onCancelled", databaseError.toException());
                            // ...
                        }
                    });

                }

                return false;

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    public void onStop() {
        super.onStop();
    }
}
