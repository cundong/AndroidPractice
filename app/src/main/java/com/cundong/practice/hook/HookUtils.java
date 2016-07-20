package com.cundong.practice.hook;

import android.app.Instrumentation;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by liucundong on 2016/5/26.
 */
public class HookUtils {

    //http://www.cnblogs.com/ZhangXiangQian/p/5386029.html

    /**
     * hook掉ActivityThread的mInstrumentation属性
     * ContextImpl中startActivity()是直接调用mMainThread.getInstrumentation().execStartActivity，所以Hook它即可
     *
     */
    public static void hookContextImpl() {

        try {
            //1.获得当前activityThread对象
            Class activityThreadClazz = Class.forName("android.app.ActivityThread");

            Method currentActivityThreadMethod = activityThreadClazz.getDeclaredMethod("currentActivityThread");
            currentActivityThreadMethod.setAccessible(true);
            Object activityThread = null;
            try {
                activityThread = currentActivityThreadMethod.invoke(null);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }

            //2.获得当前activityThread的mInstrumentation属性
            //ContextImpl中startActivity()是直接调用mMainThread.getInstrumentation().execStartActivity，所以Hook它即可
            Field mInstrumentationField = activityThreadClazz.getDeclaredField("mInstrumentation");
            mInstrumentationField.setAccessible(true);

            Instrumentation mInstrumentation = (Instrumentation)mInstrumentationField.get(activityThread);

            Instrumentation myInstrumentation = new HookInstrumentation(mInstrumentation);
            mInstrumentationField.set(activityThread, myInstrumentation);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }
}