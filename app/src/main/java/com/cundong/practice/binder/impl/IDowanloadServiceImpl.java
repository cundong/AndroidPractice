package com.cundong.practice.binder.impl;

import android.os.RemoteException;

import com.cundong.touch.IDownloadService;

import java.util.Random;

/**
 * Created by liucundong on 2016/3/29.
 */
public class IDowanloadServiceImpl extends IDownloadService.Stub {

    // Random number generator
    private final Random mGenerator = new Random();

    @Override
    public void download(String url) throws RemoteException {
        //TODO 真正的业务逻辑
    }

    @Override
    public void delete(String url) throws RemoteException {
        //TODO 真正的业务逻辑
    }

    @Override
    public void stop(String url) throws RemoteException {
        //TODO 真正的业务逻辑
    }

    @Override
    public int getQueueSize() throws RemoteException {
        return mGenerator.nextInt(100);
    }
}
