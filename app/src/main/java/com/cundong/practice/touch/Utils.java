package com.cundong.practice.touch;

import android.support.v4.view.MotionEventCompat;
import android.view.MotionEvent;

/**
 * Created by cundong on 2016/3/22.
 */
public class Utils {

    public static String getActionName(MotionEvent event) {
        int action = MotionEventCompat.getActionMasked(event);
        return getActionName(action);
    }

    public static String getActionName(int action) {

        String name = "(DEF_)";
        switch (action) {
            case MotionEvent.ACTION_DOWN: {
                name = "(MotionEvent.ACTION_DOWN)";
                break;
            }
            case MotionEvent.ACTION_MOVE: {
                name = "(MotionEvent.ACTION_MOVE)";
                break;
            }
            case MotionEvent.ACTION_UP: {
                name = "(MotionEvent.ACTION_UP)";
                break;
            }
            case MotionEvent.ACTION_CANCEL: {
                name = "(MotionEvent.ACTION_CANCEL)";
                break;
            }
        }
        return name;
    }
}