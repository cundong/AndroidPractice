package com.cundong.practice.binder.impl;

import android.os.RemoteException;
import android.util.Log;

import com.cundong.touch.IDownloadService;

import java.util.Random;

/**
 * Created by liucundong on 2016/3/29.
 *
 * IDownloadService.aidl的真正业务类
 */
public class IDowanloadServiceImpl extends IDownloadService.Stub {

    private static final String TAG = "IDowanloadServiceImpl";

    // Random number generator
    private final Random mGenerator = new Random();

    @Override
    public void download(String url) throws RemoteException {
        //TODO 真正的业务逻辑
        Log.d(TAG, "download " + url);
    }

    @Override
    public void delete(String url) throws RemoteException {
        //TODO 真正的业务逻辑
        Log.d(TAG, "delete" + url);
    }

    @Override
    public void stop(String url) throws RemoteException {
        //TODO 真正的业务逻辑
        Log.d(TAG, "stop" + url);
    }

    @Override
    public int getQueueSize() throws RemoteException {
        Log.d(TAG, "getQueueSize");
        return mGenerator.nextInt(100);
    }
}
