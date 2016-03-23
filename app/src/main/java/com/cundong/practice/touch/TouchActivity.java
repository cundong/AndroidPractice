package com.cundong.practice.touch;

import android.os.Bundle;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.cundong.practice.R;

public class TouchActivity extends AppCompatActivity {

    private static final String TAG = "Touch";

    private ViewGroup mRootView;
    private Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_touch);

        mRootView = (ViewGroup) findViewById(R.id.root);
        mButton = (Button) findViewById(R.id.button);
        mButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                final int action = MotionEventCompat.getActionMasked(event);
                Log.i(TAG, "Activity--View.OnTouchListener()--onTouch()" + Utils.getActionName(action));

                return false;
            }
        });

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "Activity--View.OnClick()");
            }
        });

        mButton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Log.i(TAG, "Activity--View.onLongClick()");
                return false;
            }
        });
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        final int action = MotionEventCompat.getActionMasked(ev);
        Log.i(TAG, "Activity--dispatchTouchEvent" + Utils.getActionName(action));

        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        final int action = MotionEventCompat.getActionMasked(event);
        Log.i(TAG, "Activity--onTouchEvent" + Utils.getActionName(action));

        return super.onTouchEvent(event);
    }
}
