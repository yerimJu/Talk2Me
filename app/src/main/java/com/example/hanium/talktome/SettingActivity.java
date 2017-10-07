package com.example.hanium.talktome;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TimePicker;
import android.widget.Toast;

import static com.example.hanium.talktome.R.id.edit_keyword;

/**
 * Created by SAMSUNG on 2017-06-07.
 */

public class SettingActivity extends AppCompatActivity {
    private PopupWindow pwindo;
    private EditText edit_keyword;
    private DatePicker datepick;
    private TimePicker timepick;

    private String Str_keyword; //edit text에서 키워드를 받아옴
    private String Str_year, Str_month, Str_day, Str_hour, Str_minute; //방해금지모드, 시간여행 picker에서 날짜, 시간을 받아옴

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting);
    }

    public void mOnClick(View v) {
        LayoutInflater inflater = (LayoutInflater) SettingActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.keyword_popup, (ViewGroup) findViewById(R.id.popup_keyword));

        switch (v.getId()) {
            case R.id.postManage:
                Toast.makeText(getApplicationContext(),"관리할 게시물이 없습니다.",Toast.LENGTH_SHORT).show();
                break;
            case R.id.modifyProfile:
                Toast.makeText(getApplicationContext(),"수정할 프로필정보가 없습니다.",Toast.LENGTH_SHORT).show();
                break;
            case R.id.outMember:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);     // 여기서 this는 Activity의 this

                builder.setTitle("회원탈퇴")        // 제목 설정
                        .setMessage("정말로 회원탈퇴 하시겠습니까?")        // 메세지 설정
                        .setCancelable(false)        // 뒤로 버튼 클릭시 취소 가능 설정
                        .setPositiveButton("네", new DialogInterface.OnClickListener(){
                            // 확인 버튼 클릭시 설정
                            public void onClick(DialogInterface dialog, int whichButton){
                                Toast.makeText(getApplicationContext(),"회원 탈퇴 되었습니다.",Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(SettingActivity.this, Login2Activity.class);
                                startActivity(intent);
                                finish();
                            }
                        })
                        .setNegativeButton("아니오", new DialogInterface.OnClickListener(){
                            // 취소 버튼 클릭시 설정
                            public void onClick(DialogInterface dialog, int whichButton){
                                dialog.cancel();
                            }
                        });

                AlertDialog dialog = builder.create();    // 알림창 객체 생성
                dialog.show();    // 알림창 띄우기

                break;
            case R.id.event:
                Toast.makeText(getApplicationContext(),"현재 진행중인 이벤트가 없습니다.",Toast.LENGTH_SHORT).show();
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
                Intent intent = new Intent(SettingActivity.this, LoginActivity.class);
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
                MakePopup(layout, 3);
                break;

            case R.id.btn_noring:
                //방해금지 모드 설정
                layout = inflater.inflate(R.layout.noring_popup, (ViewGroup) findViewById(R.id.popup_noring));
                MakePopup(layout, 4);
                break;
            default:
                break;
        }
    }
    public void MakePopup(View layout, int id){
        if(id==3) pwindo = new PopupWindow(layout, 1000, 1600, true);
        else if(id==4) pwindo = new PopupWindow(layout, 1000, 1400, true);
        else pwindo = new PopupWindow(layout, 1000, 800, true);
        pwindo.showAtLocation(layout, Gravity.CENTER, 0, 0);

        if(id == 1) edit_keyword = (EditText) layout.findViewById(R.id.edit_keyword);
        else if(id==3)
            datepick = (DatePicker) layout.findViewById(R.id.datepick);
        else if(id==4)
            timepick = (TimePicker) layout.findViewById(R.id.timepick);

    }
    public void btn_no(View v){
        pwindo.dismiss();
    }

    //1. 키워드 설정
    public void btn_keyword_yes(View v){
        Str_keyword = edit_keyword.getText().toString();
        if(Str_keyword.equals(""))
            Toast.makeText(getApplicationContext(), "키워드가 설정되지 않았습니다. 다시 입력해주세요.", Toast.LENGTH_SHORT).show();

        else {
            Toast.makeText(getApplicationContext(), "키워드가 " + Str_keyword + "로 설정되었습니다.", Toast.LENGTH_SHORT).show();
            // 키워드 디비에 저장

            // 설정된 키워드 관련 알림 가져오기
            pwindo.dismiss();
        }
    }

    //2. 우선순위 그룹 설정
    // 친구를 뭐로할지

    //3. 시간여행 설정
    public void btn_time_yes(View v){
        Str_year = String.valueOf(datepick.getYear());
        Str_month = String.valueOf(datepick.getMonth() + 1);
        Str_day = String.valueOf(datepick.getDayOfMonth());

        Toast.makeText(getApplicationContext(), "시간 여행 할 날짜가 " + Str_year+"년 "+Str_month+"월 "+Str_day+"일" + "로 설정되었습니다.", Toast.LENGTH_SHORT).show();
        // 이전 알림 조회 시간 디비에 저장

        // 설정된 시간의 알림 가져오기
        pwindo.dismiss();

    }

    //4. 방해금지 모드 설정
    public void btn_noring_yes(View v){
        Str_hour = String.valueOf(timepick.getCurrentHour());
        Str_minute = String.valueOf(timepick.getCurrentMinute());

        Toast.makeText(getApplicationContext(), "방해금지 시간이 현재부터 " + Str_hour+":"+Str_minute+" 까지로 설정되었습니다.", Toast.LENGTH_SHORT).show();
        // 방해 금지 시간 디비에 저장

        // 설정된 시간의 알림 막기
        pwindo.dismiss();
    }
}
