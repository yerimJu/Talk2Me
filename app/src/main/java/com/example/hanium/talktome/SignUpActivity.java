package com.example.hanium.talktome;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by Jisu on 2017-11-19.
 */

public class SignUpActivity extends AppCompatActivity {
    String TAG = "JoinActivity";
    EditText et_join_id, et_join_pw, et_join_name, et_join_email;
    CheckBox cb_join_clause, cb_join_personal;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.join);

        et_join_id = (EditText) findViewById(R.id.et_join_id);
        et_join_pw= (EditText) findViewById(R.id.et_join_pw);
        et_join_name = (EditText) findViewById(R.id.et_join_name);
        et_join_email = (EditText) findViewById(R.id.et_join_email);

        cb_join_clause = (CheckBox) findViewById(R.id.cb_join_clause);
        cb_join_personal = (CheckBox) findViewById(R.id.cb_join_personal);

        mAuth = FirebaseAuth.getInstance();
    }

    public void mOnClick(View v) {
        switch (v.getId()) {
            case R.id.btn_join_id_check:
                //중복 확인

                break;
            case R.id.btn_join_clause:
                //이용약관 내용보기

                break;
            case R.id.btn_join_personal:
                //개인정보 내용보기

                break;
            case R.id.btn_join_ok:
                //회원가입
                String id = et_join_id.getText().toString();
                String pw = et_join_pw.getText().toString();
                String name = et_join_name.getText().toString();
                String email = et_join_email.getText().toString();

                createAccount(id, pw, name, email);
                break;
            default:
                break;
        }
    }

    private boolean validateForm(String id, String password, String name, String email) {
        boolean valid = true;

        if (TextUtils.isEmpty(id) || TextUtils.isEmpty(password) || TextUtils.isEmpty(name) || TextUtils.isEmpty(email)) {
            Toast.makeText(SignUpActivity.this, "양식을 다 입력해주세요.", Toast.LENGTH_SHORT).show();
            valid = false;
        } else {
            if(!TextUtils.isEmpty(id)){
                //이메일 형식
                final String regex = "\\p{Alnum}+@\\p{Alnum}+\\.\\p{Alnum}+";
                boolean isEmail = email.matches(regex);
                if(!isEmail){
                    Toast.makeText(SignUpActivity.this, "아이디를 이메일 형식으로 입력해주세요.", Toast.LENGTH_SHORT).show();
                    valid = false;
                }
            }
            else if(!TextUtils.isEmpty(password)){
                //비밀번호 형식
                final String regex2 = "\\p{Alnum}+";
                final String regex3 = "\\p{Digit}+";
                final String regex4 = "\\p{Alpha}+";
                boolean isAll = password.matches(regex2);
                boolean isNum = password.matches(regex3);
                boolean isAlpha = password.matches(regex4);

                boolean isPass = false;
                if(isAll && !isNum && !isAlpha) //영어 혹은 숫자로 이루어지고, 영어와 숫자만 포함되지 않을 때
                    isPass = true;

                if(password.length() < 6){
                    Toast.makeText(SignUpActivity.this, "비밀번호를 6자리 이상 입력해주세요.", Toast.LENGTH_SHORT).show();
                    valid = false;
                }
                else if(!isPass){
                    Toast.makeText(SignUpActivity.this, "비밀번호를 영어 + 숫자의 조합으로 입력해주세요.", Toast.LENGTH_SHORT).show();
                    valid = false;
                }
            }

            if(!cb_join_clause.isChecked() || !cb_join_personal.isChecked()){
                Toast.makeText(SignUpActivity.this, "이용약관과 개인정보 취급방침을 모두 동의해주세요.", Toast.LENGTH_SHORT).show();
                valid = false;
            }
        }
        return valid;
    }

    private void createAccount(String id, String password, String name, String email) {
        Log.d(TAG, "createAccount:" + id);
        if (!validateForm(id, password, name, email)) {
            return;
        }

        // [START create_user_with_email]
        mAuth.createUserWithEmailAndPassword(id, password)
                .addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            Toast.makeText(SignUpActivity.this, "회원가입 되었습니다.", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(SignUpActivity.this, SignInActivity.class));
                            finish();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(SignUpActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void onAuthSuccess(FirebaseUser user) {
        //String username = usernameFromEmail(user.getEmail());

        // Write new user
        //writeNewUser(user.getUid(), username, user.getEmail());

        // Go to MainActivity
        startActivity(new Intent(SignUpActivity.this, MainActivity.class));
        finish();
    }
}
