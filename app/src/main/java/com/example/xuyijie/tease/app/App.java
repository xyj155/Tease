package com.example.xuyijie.tease.app;

import android.app.Application;


import com.alibaba.android.arouter.BuildConfig;
import com.alibaba.android.arouter.launcher.ARouter;

import cn.jpush.im.android.api.JMessageClient;

/**
 * Created by Xuyijie on 2018/10/5.
 */

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ARouter.openLog();
        ARouter.openDebug();
        ARouter.init(this);
        JMessageClient.setDebugMode(true);
        JMessageClient.init(this);
    }

}
