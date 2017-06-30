package com.crossit.collegeoffinearts.splash;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.crossit.collegeoffinearts.MainActivity;
import com.crossit.collegeoffinearts.R;
import com.crossit.collegeoffinearts.myAuth;


public class Splash extends AppCompatActivity {
    private final int SPLASH_DISPLAY_TIME = 3000;

    SharedPreferences sharedPref ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        sharedPref = getSharedPreferences("key",MODE_PRIVATE);



        final Handler handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                finish();
                startActivity(intent);

            }
        };

        handler.sendEmptyMessageDelayed(0,SPLASH_DISPLAY_TIME);
    }

    @Override
    protected void onStart() {
        super.onStart();

        myAuth.userId = sharedPref.getString("firebaseKey", "non");
        myAuth.userEmail = sharedPref.getString("firebaseEmail",null);
        myAuth.userName = sharedPref.getString("firebaseName",null);

    }

    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(0,0);
        Log.d("qwe",myAuth.userId);


    }
}
