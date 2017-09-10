package com.benny.libapp;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.benny.baselib.view.list.LetterSideBar;
import com.benny.baselib.view.progress.ArcProgress;
import com.benny.baselib.view.text.ColorTrackTextView;

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


        ColorTrackTextView textView = (ColorTrackTextView) findViewById(R.id.activity_test_color_text_view);
        ObjectAnimator objectAnimator1 = ObjectAnimator.ofFloat(textView,"Step",0,1.0f);
        objectAnimator1.setStartDelay(1000);
        objectAnimator1.setDuration(1000);
        objectAnimator1.setRepeatCount(ValueAnimator.INFINITE);
        objectAnimator1.setRepeatMode(ValueAnimator.REVERSE);
        objectAnimator1.start();


        LetterSideBar letterSideBar = (LetterSideBar) findViewById(R.id.activity_test_letter_side_bar);
        letterSideBar.setOnSelectChangeListener(new LetterSideBar.OnSelectChangeListener() {
            @Override
            public void selectChange(String letter) {
                Log.d("LetterSideBar", "selectChange: " + letter);
            }
        });
    }
}
