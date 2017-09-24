package com.example.hanium.talktome;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterAuthToken;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import io.fabric.sdk.android.Fabric;
import okhttp3.internal.Util;

/**
 * Created by Jisu on 2017-07-29.
 */

public class LoginActivity extends Activity {
    // facebook Login 변수
    //private Button FacebookLoginButton;
    private LoginButton FacebookLoginButton2;
    private CallbackManager callbackManager;

    // 트위터
    private static final String SEARCH_QUERY = "Almounir";
    private static final String SEARCH_RESULT_TYPE = "recent";
    private static final int SEARCH_COUNT = 20;
    private long maxId;

    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    private static final String TWITTER_KEY = " 01wqQAQBbxpj0ALkh6mZjb48k";
    private static final String TWITTER_SECRET = " ZN9GDK5x5GJrlfkeNPdCPTrUAw2vSoKoxHgrEtu21NmvxKSmsq";

    private TwitterLoginButton loginButton;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);

        setContentView(R.layout.activity_login);

        // facebook Login
        callbackManager = CallbackManager.Factory.create();
        //FacebookLoginButton = (Button) findViewById(R.id.btnFacebookLogin);
        FacebookLoginButton2 = (LoginButton) findViewById(R.id.fb_login_button);

        // custom login button이므로 loginmanager를 통한 콜백함수 처리
        /*FacebookLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //LoginManager - 요청된 읽기 또는 게시 권한으로 로그인 절차를 시작합니다.
                LoginManager.getInstance().logInWithReadPermissions(LoginActivity.this,
                        Arrays.asList("public_profile", "user_friends"));
                LoginManager.getInstance().registerCallback(callbackManager,
                        new FacebookCallback<LoginResult>() {
                            @Override
                            public void onSuccess(LoginResult loginResult) {
                                Log.e("onSuccess", "onSuccess");
                                Toast.makeText(getApplicationContext(),"로그인 성공",Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onCancel() {
                                Log.e("onCancel", "onCancel");
                            }

                            @Override
                            public void onError(FacebookException exception) {
                                Log.e("onError", "onError " + exception.getLocalizedMessage());
                            }
                        });
            }
        });*/

        // graph api를 이용한 페이스북 로그인
        FacebookLoginButton2.setReadPermissions("public_profile", "user_friends","email");
        FacebookLoginButton2.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) { //로그인 성공시 호출되는 메소드
                Log.e("토큰",loginResult.getAccessToken().getToken());
                Log.e("유저아이디",loginResult.getAccessToken().getUserId());
                Log.e("퍼미션 리스트",loginResult.getAccessToken().getPermissions()+"");

                //loginResult.getAccessToken() 정보를 가지고 유저 정보를 가져올수 있습니다.
                GraphRequest request =GraphRequest.newMeRequest(loginResult.getAccessToken() ,
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {
                                try {
                                    Log.e("user profile",object.toString());
                                    Toast.makeText(getApplicationContext(),"user profile : "+object.toString()
                                            ,Toast.LENGTH_SHORT).show();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                request.executeAsync();
            }

            @Override
            public void onError(FacebookException error) { }

            @Override
            public void onCancel() { }
        });

        // 트위터
        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new Twitter(authConfig));

        loginButton = (TwitterLoginButton) findViewById(R.id.twitter_login_button);
        loginButton.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                // The TwitterSession is also available through:
                // Twitter.getInstance().core.getSessionManager().getActiveSession()
//                TwitterSession session = result.data;
//                // TODO: Remove toast and use the TwitterSession's userID
//                // with your app's user model
//                String msg = "@" + session.getUserName() + " logged in! (#" + session.getUserId() + ")";
//                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
//
                Toast.makeText(LoginActivity.this, "Authentication Success", Toast.LENGTH_SHORT).show();

                TwitterSession session = Twitter.getSessionManager().getActiveSession();
                TwitterAuthToken authToken = session.getAuthToken();
                String token = authToken.token;
                String secret = authToken.secret;
                String userName = session.getUserName();
            }

            @Override
            public void failure(TwitterException exception) {
                Log.d("TwitterKit", "Login with Twitter failure", exception);
            }

        });
        // 로그인 화면
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Pass the activity result to the login button.
        loginButton.onActivityResult(requestCode, resultCode, data);
    }


}
