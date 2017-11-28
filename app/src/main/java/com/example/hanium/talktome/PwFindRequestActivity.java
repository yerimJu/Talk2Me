package com.example.hanium.talktome;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class PwFindRequestActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private EditText et_pw_find_request_id, et_pw_find_request_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pw_find_request);

        et_pw_find_request_id = (EditText) findViewById(R.id.et_pw_find_request_id);
        et_pw_find_request_name = (EditText) findViewById(R.id.et_pw_find_request_name);
        mAuth = FirebaseAuth.getInstance();
    }

    public void clickFindPassword(View v){
        //비밀번호 재설정 이메일 보내기
        if(TextUtils.isEmpty(et_pw_find_request_id.getText().toString()) || TextUtils.isEmpty(et_pw_find_request_name.getText().toString()))
            Toast.makeText(PwFindRequestActivity.this, "양식을 다 입력해주세요.", Toast.LENGTH_SHORT).show();
        else {
            String emailAddress = et_pw_find_request_id.getText().toString().trim();
            mAuth.sendPasswordResetEmail(emailAddress)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(PwFindRequestActivity.this, "비밀번호 재설정 이메일을 보냈습니다.", Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                Toast.makeText(PwFindRequestActivity.this, "아이디가 존재하지 않습니다.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }
}
