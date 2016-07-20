package com.cundong.practice.app;

import android.app.Application;
import android.content.Context;

import com.cundong.practice.hook.HookUtils;

/**
 * Created by liucundong on 2016/7/20.
 */
public class PracticeApplication extends Application {

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);

        HookUtils.hookContextImpl();
    }
}