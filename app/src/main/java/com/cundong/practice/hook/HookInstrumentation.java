package com.cundong.practice.hook;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by liucundong on 2016/7/20.
 * <p/>
 * hook Instrumentation
 */
public class HookInstrumentation extends Instrumentation {

    private static final String TAG = "HookInstrumentation";

    private Instrumentation mBase;

    public HookInstrumentation(Instrumentation base) {
        mBase = base;
    }

    /**
     * 重写父类execStartActivity()方法
     *
     * @param who
     * @param contextThread
     * @param token
     * @param target
     * @param intent
     * @param requestCode
     * @param options
     */
    public ActivityResult execStartActivity(
            Context who, IBinder contextThread, IBinder token, Activity target,
            Intent intent, int requestCode, Bundle options) {

        StringBuilder sb = new StringBuilder();
        sb.append("who:").append(who)
                .append(",contextThread:").append(contextThread)
                .append(",token:").append(token)
                .append(",target:").append(target)
                .append(",intent:").append(intent)
                .append(",requestCode:").append(requestCode)
                .append(",options:").append(options).toString();

        Log.d(TAG, "execStartActivity:" + sb.toString());

        Toast.makeText(who, "execStartActivity:" + sb.toString(), Toast.LENGTH_SHORT).show();

        ActivityResult result = null;

        try {
            Method execStartActivityMethod = Instrumentation.class.getDeclaredMethod("execStartActivity", Context.class, IBinder.class, IBinder.class,
                    Activity.class, Intent.class, int.class, Bundle.class);
            execStartActivityMethod.setAccessible(true);
            result = (ActivityResult) execStartActivityMethod.invoke(mBase, who, contextThread, token, target, intent, requestCode, options);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        return result;
    }
}