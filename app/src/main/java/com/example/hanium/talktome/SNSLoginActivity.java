package com.example.hanium.talktome;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterAuthToken;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

import org.json.JSONObject;

import io.fabric.sdk.android.Fabric;

/**
 * Created by Jisu on 2017-11-19.
 */

public class SNSLoginActivity extends Activity {
    // for firebase database
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    /*implements GoogleApiClient.OnConnectionFailedListener, View.OnClickListener {*/
    String TAG = "LoginActivity";
    // facebook Login 변수
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
        //Fabric.with(this, new Crashlytics());

        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);

        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        // facebook Login
        callbackManager = CallbackManager.Factory.create();
        FacebookLoginButton2 = (LoginButton) findViewById(R.id.fb_login_button);

        // graph api를 이용한 페이스북 로그인
        FacebookLoginButton2.setReadPermissions("public_profile", "user_friends","email");
        FacebookLoginButton2.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                String fbtoken = loginResult.getAccessToken().getToken();
                Toast.makeText(SNSLoginActivity.this,"로그인 성공 " + fbtoken, Toast.LENGTH_SHORT).show();

                Log.d(TAG, "토큰 : "+fbtoken);
                Log.d(TAG, "유저아이디"+loginResult.getAccessToken().getUserId());
                Log.d(TAG, "퍼미션 리스트"+loginResult.getAccessToken().getPermissions()+"");

                FirebaseUser user = mAuth.getCurrentUser();
                String userId = user.getUid();
                mDatabase.child("users").child(userId).child("facebookAccesstoken").setValue(fbtoken);
                Log.d(TAG, "Facebook Accesstoken saved successfully !");

                //loginResult.getAccessToken() 정보를 가지고 유저 정보를 가져올수 있습니다.
                GraphRequest request =GraphRequest.newMeRequest(loginResult.getAccessToken() ,
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {
                                try {
                                    Log.e("user profile",object.toString());
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
                Toast.makeText(SNSLoginActivity.this, "Authentication Success", Toast.LENGTH_SHORT).show();

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
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // 자동 로그인 계속 유지
        callbackManager.onActivityResult(requestCode, resultCode, data);
        loginButton.onActivityResult(requestCode, resultCode, data);

    }
}
