package com.benny.baselib.view.progress;

import android.content.Context;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by yuanbb on 2017/9/26.
 */

public class BounceProgress extends SurfaceView  implements SurfaceHolder.Callback{

    public BounceProgress(Context context) {
        super(context);
    }

    public BounceProgress(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BounceProgress(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }
}
