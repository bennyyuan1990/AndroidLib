package com.benny.libapp;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.benny.baselib.view.progress.ArcProgress;

/**
 * Created by Benny on 2017/9/9.
 */

public class TestActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        ArcProgress arcProgress = (ArcProgress) findViewById(R.id.activity_test_arc_progress);

        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(arcProgress,"CurrentStep",0,100);
        objectAnimator.setStartDelay(1000);
        objectAnimator.setDuration(10000);
        objectAnimator.start();
    }
}
