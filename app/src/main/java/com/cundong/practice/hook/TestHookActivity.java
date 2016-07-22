package com.cundong.practice.hook;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.cundong.practice.R;
import com.cundong.practice.touch.TouchActivity;

/**
 * Created by liucundong on 2016/7/20.
 *
 */
public class TestHookActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_hook);

        findViewById(R.id.start).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Class clazz = TouchActivity.class;

                Intent intent = new Intent();
                intent.putExtra("reallyTarget", clazz);
                intent.setClass(TestHookActivity.this, PlaceHolderActivity.class);
//                getApplicationContext().startActivity(intent);
                startActivity(intent);

            }
        });
    }
}