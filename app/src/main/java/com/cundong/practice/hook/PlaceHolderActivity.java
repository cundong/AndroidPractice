package com.cundong.practice.hook;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.cundong.practice.R;

/**
 * Created by liucundong on 2016/7/21.
 * <p/>
 * 占位Activity，用于在AndroidMainfest中声明
 */
public class PlaceHolderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_place_holder);
    }
}