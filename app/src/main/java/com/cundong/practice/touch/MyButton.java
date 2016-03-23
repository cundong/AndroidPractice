package com.cundong.practice.touch;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Button;

/**
 * Created by cundong on 2016/3/21.
 */
public class MyButton extends Button {

    private static final String TAG = "Touch";

    public MyButton(Context context) {
        super(context);
    }

    public MyButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {

        Log.i(TAG, "MyButton--dispatchTouchEvent" + Utils.getActionName(event));

        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        Log.i(TAG, "MyButton--onTouchEvent" + Utils.getActionName(event));

        return super.onTouchEvent(event);
    }
}