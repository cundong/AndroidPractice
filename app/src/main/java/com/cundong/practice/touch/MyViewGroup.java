package com.cundong.practice.touch;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.LinearLayout;

/**
 * Created by cundong on 2016/3/21.
 */
public class MyViewGroup extends LinearLayout {

    private static final String TAG = "Touch";

    public MyViewGroup(Context context) {
        super(context);
    }

    public MyViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.i(TAG, "ViewGroup--dispatchTouchEvent" + Utils.getActionName(ev));
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.i(TAG, "ViewGroup--onInterceptTouchEvent" + Utils.getActionName(ev));
        return super.onInterceptTouchEvent(ev);
    }

    @Override
     public boolean onTouchEvent(MotionEvent event) {
        Log.i(TAG, "！！！ViewGroup--onTouchEvent" + Utils.getActionName(event));
//        return super.onTouchEvent(event);
        return true;
    }

    /**
     * ViewGroup几种逻辑：
     * 1.拦截但是不消费事件
     * 2.拦截并且消费事件
     * 3.不拦截，但是消费了事件
     */

    /**
     * View几种逻辑：
     * 1.消费事件
     * 2.不消费事件
     */
}