package com.tsl.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.tsl.app.R;


public class Splash extends AppCompatActivity {

    private static int SPLASH_TIME = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        afterSplash();
    }


    private void afterSplash() {
        new Handler().postDelayed(startApp, SPLASH_TIME);
    }

    private Runnable startApp = new Runnable() {
        @Override
        public void run() {
            Intent intent = ViewManager.getLoginActivity(Splash.this);
            startActivity(intent);
            finish();
        }
    };


}
