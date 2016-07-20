package com.cundong.practice.dynamicproxy;

import android.util.Log;

/**
 * Created by liucundong on 2016/7/20.
 * <p/>
 * 房屋中介实现类，内部使用，不公开
 */
public class HouseAgencyInterfaceImpl implements HouseAgencyInterface {

    private static final String TAG = "proxy";

    @Override
    public void buyHouse(String name, float price) {

        Log.i(TAG, "buyHouse, name:" + name + ", price:" + price);
    }
}