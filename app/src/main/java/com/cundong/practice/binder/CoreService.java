package com.cundong.practice.binder;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.cundong.practice.binder.impl.IDowanloadServiceImpl;

/**
 * Created by liucundong on 2016/3/29.
 *
 * 位于:core进程的Service，提供服务给其他进程
 */
public class CoreService extends Service {

    @Override
    public IBinder onBind(Intent intent) {
        return new IDowanloadServiceImpl();
    }
}