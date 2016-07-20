package com.cundong.practice.dynamicproxy;

import android.util.Log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Created by liucundong on 2016/7/20.
 * <p/>
 * 动态代理类的InvocationHandler
 */
public class HomeLinkInvocationHandler implements InvocationHandler {

    private static final String TAG = "proxy";

    private Object mProxy = null;

    public HomeLinkInvocationHandler(Object proxy) {
        this.mProxy = proxy;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        Log.i(TAG, "proxy: " + proxy.getClass().toString());

        Log.i(TAG, "do some processing before the method invocation");

        Object result = method.invoke(mProxy, args);

        Log.i(TAG, "do some processing after the method invocation");
        return result;
    }
}