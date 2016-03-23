package com.cundong.practice;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.cundong.practice.R;
import com.cundong.practice.touch.TouchActivity;

public class MainActivity extends AppCompatActivity {

    private Button mTouchBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTouchBtn = (Button) findViewById(R.id.touch_btn);
        mTouchBtn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity( new Intent(MainActivity.this, TouchActivity.class));
                finish();
            }
        });
    }
}
