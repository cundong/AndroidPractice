package com.cundong.practice.binder;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.cundong.practice.binder.impl.IDowanloadServiceImpl;

/**
 * Created by liucundong on 2016/3/29.
 */
public class CoreService extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new IDowanloadServiceImpl();
    }
}