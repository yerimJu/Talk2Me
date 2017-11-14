package com.example.hanium.talktome;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.hanium.talktome.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseNetworkException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by SAMSUNG on 2017-07-30.
 */

public class Login2Activity extends AppCompatActivity {

    // for firebase database
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;

    private EditText et_id, et_pw;
    String TAG = "Login2Activity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        et_id = (EditText) findViewById(R.id.et_login_id);
        et_pw = (EditText) findViewById(R.id.et_login_pw);
    }

    public void mOnClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                String email = et_id.getText().toString();
                String password = et_pw.getText().toString();

                if (validateForm(email, password))
                    signIn(email, password);
                break;
            //회원가입
            case R.id.btn_login_join:
                Intent intent = new Intent(this, JoinActivity.class);
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

    private boolean validateForm(String email, String password) {
        boolean valid = true;

        if (TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {
            Toast.makeText(Login2Activity.this, "아이디를 입력해주세요.", Toast.LENGTH_SHORT).show();
            valid = false;
        } else if (TextUtils.isEmpty(password) && !TextUtils.isEmpty(email)) {
            Toast.makeText(Login2Activity.this, "비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show();
            valid = false;
        } else if (TextUtils.isEmpty(email) && TextUtils.isEmpty(password)) {
            Toast.makeText(Login2Activity.this, "아이디와 비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show();
            valid = false;
        }
        return valid;
    }

    private void signIn(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(Login2Activity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful())
                            Toast.makeText(Login2Activity.this, "아이디 또는 비밀번호가 틀렸습니다.", Toast.LENGTH_SHORT).show();
                        else {
                            Toast.makeText(Login2Activity.this, "로그인 되었습니다.", Toast.LENGTH_SHORT).show();
                            onAuthSuccess(mAuth.getCurrentUser());
                            Intent intent = new Intent(Login2Activity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                });
    }

    private void onAuthSuccess(FirebaseUser user) {
        String username = usernameFromEmail(user.getEmail());

        // Write new user
        writeNewUser(user.getUid(), username, user.getEmail());

        // Go to MainActivity
        startActivity(new Intent(Login2Activity.this, MainActivity.class));
        finish();
    }

    private String usernameFromEmail(String email) {
        if (email.contains("@")) {
            return email.split("@")[0];
        } else {
            return email;
        }
    }

    // [START basic_write]
    private void writeNewUser(String userId, String name, String email) {
        User user = new User(userId, name, email);

        mDatabase.child("users").child(userId).setValue(user);
        Log.d(TAG, user.toString());
    }
    // [END basic_write]
}
