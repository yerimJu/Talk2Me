package com.example.hanium.talktome;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.hanium.talktome.models.Notification;
import com.example.hanium.talktome.models.Setting;
import com.example.hanium.talktome.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static com.example.hanium.talktome.R.id.edit_keyword;

/**
 * Created by SAMSUNG on 2017-06-07.
 */

public class SettingActivity extends AppCompatActivity {

    private static final String TAG = "SettingActivity";

    // for firebase database
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private DatabaseReference mSettingReference;
    private DatabaseReference mNotificationReference;

    private PopupWindow pwindo;
    private EditText edit_keyword;
    private DatePicker datepick, datepick2;
    private TimePicker timepick;
    private Button btn_time_yes;

    private String Str_keyword; //edit text에서 키워드를 받아옴
    private String Str_year1, Str_month1, Str_day1, Str_year2, Str_month2, Str_day2; //시간여행
    private String Str_year3, Str_month3, Str_day3, Str_hour, Str_minute; //방해금지모드 picker에서 날짜, 시간을 받아옴

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mUser = mAuth.getCurrentUser();
        mSettingReference = mDatabase.child("settings");
        mNotificationReference = mDatabase.child("notifications");
    }

    public void mOnClick(View v) {
        LayoutInflater inflater = (LayoutInflater) SettingActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.keyword_popup, (ViewGroup) findViewById(R.id.popup_keyword));

        switch (v.getId()) {
            case R.id.postManage:
                Toast.makeText(getApplicationContext(), "관리할 게시물이 없습니다.", Toast.LENGTH_SHORT).show();
                break;
            case R.id.modifyProfile:
                Toast.makeText(getApplicationContext(), "수정할 프로필정보가 없습니다.", Toast.LENGTH_SHORT).show();
                break;
            case R.id.outMember:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);     // 여기서 this는 Activity의 this

                builder.setTitle("회원탈퇴")        // 제목 설정
                        .setMessage("정말로 회원탈퇴 하시겠습니까?")        // 메세지 설정
                        .setCancelable(false)        // 뒤로 버튼 클릭시 취소 가능 설정
                        .setPositiveButton("네", new DialogInterface.OnClickListener() {
                            // 확인 버튼 클릭시 설정
                            public void onClick(DialogInterface dialog, int whichButton) {
                                Toast.makeText(getApplicationContext(), "회원 탈퇴 되었습니다.", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(SettingActivity.this, SignInActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        })
                        .setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                            // 취소 버튼 클릭시 설정
                            public void onClick(DialogInterface dialog, int whichButton) {
                                dialog.cancel();
                            }
                        });

                AlertDialog dialog = builder.create();    // 알림창 객체 생성
                dialog.show();    // 알림창 띄우기

                break;
            case R.id.event:
                Toast.makeText(getApplicationContext(), "현재 진행중인 이벤트가 없습니다.", Toast.LENGTH_SHORT).show();
                break;
            case R.id.notice:
//                Intent noticeIntent = new Intent(SettingActivity.this,  Notice.class);
//                startActivity(noticeIntent);
                break;
            case R.id.faq:
//                Intent faqIntent = new Intent(SettingActivity.this, Faq.class);
//                startActivity(faqIntent);
                break;
            case R.id.callCenter:
                String officeNum = "01077209831";
                String telOffice = "tel:" + officeNum;
                startActivity(new Intent("android.intent.action.DIAL", Uri.parse(telOffice)));
                break;

            case R.id.rule:
//                Intent useRuleIntent = new Intent(SettingActivity.this, UseRule.class);
//                startActivity(useRuleIntent);
                break;
            case R.id.dealInfo:
//                Intent personRuleIntent = new Intent(SettingActivity.this, PersonRule.class);
//                startActivity(personRuleIntent);
                break;
            case R.id.btn_snsconect:
                Intent intent = new Intent(SettingActivity.this, SNSLoginActivity.class);
                startActivity(intent);
                break;

            case R.id.btn_keyword:
                //키워드 설정
                MakePopup(layout, 1);
                break;

            case R.id.btn_group:
                //우선순위 그룹 설정
                //친구 목록 어떻게 할지 정해야함
                layout = inflater.inflate(R.layout.group_popup, (ViewGroup) findViewById(R.id.popup_group));
                MakePopup(layout, 2);
                break;

            case R.id.btn_time:
                //시간여행 설정
                layout = inflater.inflate(R.layout.time_popup, (ViewGroup) findViewById(R.id.popup_time));
                btn_time_yes = (Button) layout.findViewById(R.id.btn_time_yes);
                btn_time_yes.setText("다음");
                MakePopup(layout, 3);
                break;

            case R.id.btn_noring:
                //방해금지 모드 설정
                layout = inflater.inflate(R.layout.noring_popup1, (ViewGroup) findViewById(R.id.popup_noring1));
                MakePopup(layout, 41);
                break;
            default:
                break;
        }
    }

    public void MakePopup(View layout, int id) {
        if (id == 3 || id == 41) pwindo = new PopupWindow(layout, 1000, 1600, true);
        else if (id == 42) pwindo = new PopupWindow(layout, 1000, 1400, true);
        else pwindo = new PopupWindow(layout, 1000, 800, true);
        pwindo.showAtLocation(layout, Gravity.CENTER, 0, 0);

        if (id == 1) edit_keyword = (EditText) layout.findViewById(R.id.edit_keyword);
        else if (id == 3)
            datepick = (DatePicker) layout.findViewById(R.id.datepick);
        else if(id == 41)
            datepick2 = (DatePicker) layout.findViewById(R.id.datepick2);
        else if (id == 42)
            timepick = (TimePicker) layout.findViewById(R.id.timepick);

    }

    public void btn_no(View v) {
        pwindo.dismiss();
    }

    //1. 키워드 설정
    public void btn_keyword_yes(View v) {
        Str_keyword = edit_keyword.getText().toString();
        if (Str_keyword.equals(""))
            Toast.makeText(getApplicationContext(), "키워드가 설정되지 않았습니다. 다시 입력해주세요.", Toast.LENGTH_SHORT).show();

        else {

            // 키워드 디비에 저장
            DatabaseReference mKeywordReference = mDatabase.child("settings").child(mUser.getUid()).child("keywords");
            mKeywordReference.push().setValue(Str_keyword);
            Toast.makeText(getApplicationContext(), "키워드 " + Str_keyword + "가 성공적으로 저장되었습니다.", Toast.LENGTH_SHORT).show();

            // 설정된 키워드 관련 알림 가져오기
            Query readKeywordQuery = mSettingReference.child(mUser.getUid()).child("keywords");
            readKeywordQuery.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    List<String> keywordList = new ArrayList<>();

                    for (DataSnapshot keywordSnapshot : dataSnapshot.getChildren()) {
                        // TODO: handle the post
                        String keyword = keywordSnapshot.getValue(String.class);
                        keywordList.add(keyword);
                    }

                    if (keywordList.isEmpty()) {
                        Toast.makeText(getApplicationContext(), "저장된 키워드가 없습니다.", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "저장된 키워드 : " + keywordList.toString(), Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    // Getting Post failed, log a message
                    Log.w(TAG, "loadUser:onCancelled", databaseError.toException());
                    // ...
                    Toast.makeText(getApplicationContext(), "현재 키워드 불러오기 기능이 작동되지 않습니다.", Toast.LENGTH_SHORT).show();
                }
            });

            pwindo.dismiss();
        }
    }

    //2. 우선순위 그룹 설정
    // 친구를 뭐로할지

    //3. 시간여행 설정
    public void btn_time_yes(View v) {
        if(btn_time_yes.getText().equals("다음")){
            Str_year1 = String.valueOf(datepick.getYear());
            Str_month1 = String.valueOf(datepick.getMonth() + 1);
            Str_day1 = String.valueOf(datepick.getDayOfMonth());

            btn_time_yes.setText("확인");
            pwindo.dismiss();
            LayoutInflater inflater = (LayoutInflater) SettingActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View layout = inflater.inflate(R.layout.time_popup, (ViewGroup) findViewById(R.id.popup_time));
            MakePopup(layout, 3);
        }
        else {
            Str_year2 = String.valueOf(datepick.getYear());
            Str_month2 = String.valueOf(datepick.getMonth() + 1);
            Str_day2 = String.valueOf(datepick.getDayOfMonth());

            Toast.makeText(getApplicationContext(), "시간 여행 날짜가 " + Str_year1 + "년 " + Str_month1 + "월 " + Str_day1 + "일 부터\n"+ Str_year2 + "년 " + Str_month2 + "월 " + Str_day2 + "일" + "까지로 설정되었습니다.", Toast.LENGTH_SHORT).show();
            // 이전 알림 조회 시간 디비에 저장
            // -> 저장 할 필요 없을 것 같음. 불러오기 기능이므로
            //    날짜 설정은 당일이 아닌 n일 이었던 것 같은데 이에 대한 입력 form 수정 필요

            // 설정된 시간의 알림 가져오기
            // -> 일단 해당 날짜로 조회하는 기능으로 구현하였음
            Query loadNotificationByTimeQuery = mNotificationReference.child(mUser.getUid());
            loadNotificationByTimeQuery.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    long now = System.currentTimeMillis();
                    Date date = new Date(now);
                    List<String> notificationList = new ArrayList<>();
                    Log.d(TAG, "입력 format : " + Str_year1 + "년 " + Str_month1 + "월 " + Str_day1 + "일");

                    for (DataSnapshot notificationSnapshot : dataSnapshot.getChildren()) {
                        // TODO: handle the post
                        Notification notification = notificationSnapshot.getValue(Notification.class);

                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                        try {
                            date = simpleDateFormat.parse(notification.getDate());
                            SimpleDateFormat dateFormat = new  SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault());
                            String strDate = dateFormat.format(date);

                            String temp1 = Str_year1+"-"+Str_month1+"-"+Str_day1+" 00:00";
                            Date day1 = dateFormat.parse(temp1);

                            String temp2 = Str_year2+"-"+Str_month2+"-"+Str_day2+" 23:59";
                            Date day2 = dateFormat.parse(temp2);

                            if(date.after(day1) && day2.after(date)){
                                notificationList.add(notification.getNid());

                                Toast.makeText(getApplicationContext(), strDate + "일의 알림 id : " + notificationList.toString(), Toast.LENGTH_SHORT).show();
                            }

                        } catch (ParseException e) {
                            e.printStackTrace();
                            Log.d(TAG, "date format으로 변환할 수 없습니다.");
                        }
                    }

                    if (notificationList.isEmpty()) {
                        Toast.makeText(getApplicationContext(), Str_year1 + "년 " + Str_month1 + "월 " + Str_day1 +"일에서\n" + Str_year2 + "년 " + Str_month2 + "월 " + Str_day2 + "일 사이에 알림이 없습니다.", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    // Getting Post failed, log a message
                    Log.w(TAG, "loadUser:onCancelled", databaseError.toException());
                    // ...
                    Toast.makeText(getApplicationContext(), "현재 키워드 불러오기 기능이 작동되지 않습니다.", Toast.LENGTH_SHORT).show();
                }
            });

            pwindo.dismiss();
        }

    }

    //4. 방해금지 모드 설정
    public void btn_noring_next(View v) {
        Str_year3 = String.valueOf(datepick2.getYear());
        Str_month3 = String.valueOf(datepick2.getMonth() + 1);
        Str_day3 = String.valueOf(datepick2.getDayOfMonth());

        pwindo.dismiss();
        LayoutInflater inflater = (LayoutInflater) SettingActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.noring_popup2, (ViewGroup) findViewById(R.id.popup_noring2));
        MakePopup(layout, 42);
    }
    public void btn_noring_yes(View v) {
        Str_hour = String.valueOf(timepick.getCurrentHour());
        Str_minute = String.valueOf(timepick.getCurrentMinute());

        Toast.makeText(getApplicationContext(), "방해금지 시간이 현재부터 "  + Str_year3 + "년 " + Str_month3 + "월 " + Str_day3 + "일\n"+ Str_hour + "시 " + Str_minute + "분 까지로 설정되었습니다.", Toast.LENGTH_SHORT).show();
        // 방해 금지 시간 디비에 저장

        // 설정된 시간의 알림 막기
        pwindo.dismiss();
    }
}
