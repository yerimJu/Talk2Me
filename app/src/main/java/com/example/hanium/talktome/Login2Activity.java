package com.example.hanium.talktome;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by SAMSUNG on 2017-07-30.
 */

public class Login2Activity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
    }

    public void mOnClick(View v){
        Intent intent;
        switch(v.getId()){
            case R.id.btn_login:
                //임시 로그인 버튼으로 추후에 로그인 확인 절차 필요함
                intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();
                break;
            //회원가입
            case R.id.btn_login_join:
                intent = new Intent(this, JoinActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_login_id_find:
                Intent intent2 = new Intent(this, IdFindRequestActivity.class);
                startActivity(intent2);
                break;
            case R.id.btn_login_pw_find:
                Intent intent3 = new Intent(this, PwFindRequestActivity.class);
                startActivity(intent3);
                break;
            default:
                break;
        }
    }
}
