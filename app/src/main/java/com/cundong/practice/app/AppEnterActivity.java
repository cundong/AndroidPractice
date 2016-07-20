package com.cundong.practice.app;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.cundong.practice.R;
import com.cundong.practice.binder.BinderActivity;
import com.cundong.practice.hook.TestHookActivity;
import com.cundong.practice.touch.TouchActivity;
import com.cundong.practice.utils.FileUtil;
import com.cundong.practice.utils.SDCardUtils;

public class AppEnterActivity extends AppCompatActivity {

    private final static int REQUEST_CODE_REQUEST_STORAGE = 1;

    private Button mTouchBtn, mBinderBtn, mStorageBtn1, mStorageBtn2;

    private Button mHookBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTouchBtn = (Button) findViewById(R.id.touch_btn);
        mTouchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AppEnterActivity.this, TouchActivity.class));
            }
        });

        mBinderBtn = (Button) findViewById(R.id.binder_btn);
        mBinderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AppEnterActivity.this, BinderActivity.class));
            }
        });

        mStorageBtn1 = (Button) findViewById(R.id.storage_btn1);
        mStorageBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onWriteSdcardCacheFiles();
            }
        });

        mStorageBtn2 = (Button) findViewById(R.id.storage_btn2);
        mStorageBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Build.VERSION.SDK_INT >= 23) {
                    if (PackageManager.PERMISSION_GRANTED == ContextCompat.checkSelfPermission(AppEnterActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                        onWriteSdcardMediaFiles();
                    } else {
                        requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE_REQUEST_STORAGE);
                    }
                } else {
                    onWriteSdcardMediaFiles();
                }
            }
        });

        mHookBtn = (Button) findViewById(R.id.hook_btn);
        mHookBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(AppEnterActivity.this, TestHookActivity.class);
                Bundle bundle = new Bundle();
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case REQUEST_CODE_REQUEST_STORAGE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission Granted
                    onWriteSdcardMediaFiles();
                } else {
                    // Permission Denied
                    Toast.makeText(AppEnterActivity.this, "WRITE_EXTERNAL_STORAGE Denied.", Toast.LENGTH_SHORT)
                            .show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    /**
     * 写sd卡拓展缓存目录
     */
    private void onWriteSdcardCacheFiles() {

        if (SDCardUtils.isMounted()) {
            String path = SDCardUtils.getExternalFilesDir(this);
            boolean result = FileUtil.writeFile(path + "/" + "demo2.txt", "test." + System.currentTimeMillis(), true);

            if (result) {
                Toast.makeText(AppEnterActivity.this, "write success.", Toast.LENGTH_SHORT)
                        .show();
            } else {
                Toast.makeText(AppEnterActivity.this, "write error.", Toast.LENGTH_SHORT)
                        .show();
            }
        } else {
            Toast.makeText(AppEnterActivity.this, "no sdcard.", Toast.LENGTH_SHORT)
                    .show();
        }
    }

    /**
     * 写sd卡任意目录
     */
    private void onWriteSdcardMediaFiles() {
        if (SDCardUtils.isMounted()) {
            boolean result = FileUtil.writeFile("/sdcard/demo.txt", "test." + System.currentTimeMillis(), true);

            if (result) {
                Toast.makeText(AppEnterActivity.this, "write success.", Toast.LENGTH_SHORT)
                        .show();
            } else {
                Toast.makeText(AppEnterActivity.this, "write error.", Toast.LENGTH_SHORT)
                        .show();
            }
        } else {
            Toast.makeText(AppEnterActivity.this, "no sdcard.", Toast.LENGTH_SHORT)
                    .show();
        }
    }
}