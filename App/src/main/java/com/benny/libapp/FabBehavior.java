package com.benny.libapp;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Benny on 2017/8/26.
 */

public class FabBehavior extends FloatingActionButton.Behavior {

    private static final String TAG = "FabBehavior";

    private boolean isVisible = true;

    public FabBehavior() {
        super();
    }

    public FabBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, FloatingActionButton child, View directTargetChild, View target, int nestedScrollAxes) {
        return nestedScrollAxes == ViewCompat.SCROLL_AXIS_VERTICAL || super.onStartNestedScroll(coordinatorLayout, child, directTargetChild, target, nestedScrollAxes);
    }

    @Override
    public void onNestedScroll(CoordinatorLayout coordinatorLayout, FloatingActionButton child, View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed);


        if (isVisible && dyConsumed > 0) {
            isVisible = false;
            child.animate().scaleX(0).scaleY(0).start();
        } else if (!isVisible && dyConsumed < 0) {
            isVisible = true;
            child.animate().scaleX(1).scaleY(1).start();
        }

    }
}
