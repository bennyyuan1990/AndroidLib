package com.benny.libapp;

import android.app.Application;

import com.benny.baselib.hook.ActivityHook;

/**
 * Created by Benny on 2017/9/9.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        new ActivityHook().register();
    }
}
