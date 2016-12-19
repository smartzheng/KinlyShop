package com.taolu.shop.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.taolu.shop.R;

public class StartActivity extends AppCompatActivity {

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case START_SPLASH:
                    Intent intent = new Intent(StartActivity.this,SplashActivity.class);
                    startActivity(intent);
                    finish();
                    //overridePendingTransition(-1,-1);
                    break;

            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        delay();


    }
    private final int START_SPLASH = 0;
    private void delay() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                SystemClock.sleep(1500);
                Message msg = new Message();
                msg.what = START_SPLASH;
                handler.sendMessage(msg);
            }
        }).start();
    }
}
