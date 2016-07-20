package com.cundong.practice.utils;

import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.util.Log;

import com.cundong.practice.app.AppEnv;

import java.io.File;

/**
 * Created by cundong on 2015/9/28.
 * <p/>
 * SD卡操作工具类
 */
public class SDCardUtils {

    private static final boolean DEBUG = AppEnv.DEBUG;

    private static final String TAG = DEBUG ? "SDCardUtils" : SDCardUtils.class.getSimpleName();

    /**
     * SD卡是否挂载
     *
     * @return
     */
    public static boolean isMounted() {
        String status = Environment.getExternalStorageState();
        return status.equals(Environment.MEDIA_MOUNTED);
    }

    /**
     * 获取拓展存储Cache的绝对路径
     *
     * @param context
     * @return
     */
    public static String getExternalCacheDir(Context context) {

        if (!isMounted())
            return null;

        StringBuilder sb = new StringBuilder();

        File file = context.getExternalCacheDir();

        // In some case, even the sd card is mounted,
        // getExternalCacheDir will return null
        // may be it is nearly full.

        if (file != null) {
            sb.append(file.getAbsolutePath()).append(File.separator);
        } else {
            sb.append(Environment.getExternalStorageDirectory().getPath()).append("/Android/data/").append(context.getPackageName())
                    .append("/cache/").append(File.separator).toString();
        }

        return sb.toString();
    }

    /**
     * 获取拓展存储Files的绝对路径
     *
     * @param context
     * @return
     */
    public static String getExternalFilesDir(Context context) {

        if (!isMounted())
            return null;

        StringBuilder sb = new StringBuilder();

        File file = context.getExternalFilesDir(null);

        // In some case, even the sd card is mounted,
        // getExternalCacheDir will return null
        // may be it is nearly full.

        if (file != null) {
            sb.append(file.getAbsolutePath()).append(File.separator);
        } else {
            sb.append(Environment.getExternalStorageDirectory().getPath()).append("/Android/data/").append(context.getPackageName())
                    .append("/cache/").append(File.separator).toString();
        }

        return sb.toString();
    }

    /**
     * 是否外部存储已被移除
     *
     * @return
     */
    public static boolean isExternalStorageRemovable() {
        if (hasGingerbread()) {
            return Environment.isExternalStorageRemovable();
        } else {
            return Environment.MEDIA_REMOVED.equals(Environment.getExternalStorageState());
        }
    }

    private static boolean hasGingerbread() {
        return Build.VERSION.SDK_INT >= 9;
    }

    /**
     * 获取Cache目录
     * <p/>
     * 如果SD卡已经挂载，且没有被移除，就返回一个SD卡中的缓存目录，如：:/sdcard/Android/data/xxxx/cache/Theme
     * 否则，返回内置存储目录，如：/data/data/xxxx/cacheName
     *
     * @param context
     * @param cacheName
     */
    public static File getCacheFile(Context context, String cacheName) {
        if (isMounted() && !isExternalStorageRemovable()) {

            if (DEBUG) {
                Log.d(TAG, "cacheFile:" + new File(getExternalCacheDir(context), cacheName).getAbsolutePath());
            }
            return new File(getExternalCacheDir(context), cacheName);
        } else {

            if (DEBUG) {
                Log.d(TAG, "cacheFile:" + new File(context.getCacheDir(), cacheName).getAbsolutePath());
            }
            return new File(context.getCacheDir(), cacheName);
        }
    }
}