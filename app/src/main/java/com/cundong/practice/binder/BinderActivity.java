package com.cundong.practice.binder;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.cundong.practice.R;
import com.cundong.touch.IDownloadService;

/**
 * 位于ui进程的Activity，用于发起对:core进程的远程调用
 * <p/>
 * 一个典型的异步Binder例子
 */
public class BinderActivity extends AppCompatActivity {

    private IDownloadService mIDownloadService;

    private boolean isBound = false;

    private Button mButton;

    private ServiceConnection mConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {

            mIDownloadService = IDownloadService.Stub.asInterface(service);
            isBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            isBound = false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_binder);

        mButton = (Button) findViewById(R.id.call_btn);
        mButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (isBound) {

                    int size = 0;

                    try {
                        size = mIDownloadService.getQueueSize();

                        mIDownloadService.download("http://www.baidu.com/xxx.apk");
                        mIDownloadService.stop("http://www.baidu.com/xxx.apk");
                        mIDownloadService.delete("http://www.baidu.com/xxx.apk");

                    } catch (RemoteException e) {
                        e.printStackTrace();

                        Toast.makeText(BinderActivity.this, "no bound, please try again", Toast.LENGTH_SHORT).show();

                        // bindService
                        Intent intent = new Intent(BinderActivity.this, CoreService.class);
                        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
                    }

                    Toast.makeText(BinderActivity.this, "size: " + size, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(BinderActivity.this, "no bound, please try again", Toast.LENGTH_SHORT).show();

                    // bindService
                    Intent intent = new Intent(BinderActivity.this, CoreService.class);
                    bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        Intent intent = new Intent(this, CoreService.class);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();

        if (isBound) {
            unbindService(mConnection);
            isBound = false;
        }
    }
}