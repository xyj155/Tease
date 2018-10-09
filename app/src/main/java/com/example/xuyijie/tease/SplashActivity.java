package com.example.xuyijie.tease;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;


import com.alibaba.android.arouter.launcher.ARouter;
import com.example.library.arouter.ArouterUrl;


public class SplashActivity extends AppCompatActivity {
    private static final String TAG = "SplashActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ARouter.getInstance().build(ArouterUrl.HOMEACTIVITY).navigation();
                finish();
            }
        }, 2000);

    }
}
