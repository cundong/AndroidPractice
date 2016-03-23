package com.cundong.practice.touch;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.TextView;

/**
 * Created by cundong on 2016/3/23.
 */
public class MyTextView extends TextView {

    private static final String TAG = "Touch";

    public MyTextView(Context context) {
        super(context);
    }

    public MyTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {

        Log.i(TAG, "MyTextView--dispatchTouchEvent" + Utils.getActionName(event));

        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        Log.i(TAG, "MyTextView--onTouchEvent" + Utils.getActionName(event));

        return super.onTouchEvent(event);
    }
}