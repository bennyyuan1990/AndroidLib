package com.yuanbb.common.widget;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Checkable;

import com.yuanbb.common.R;

/**
 * Created by yuanbb on 2016/2/26.
 */
public class SwitchButton extends View implements Checkable {
    private boolean mIsCheck = false;

    private Bitmap mSwitchBottom;
    private Bitmap mSwitchFrame;
    private Bitmap mSwitchMask;
    private Bitmap mSwitchPressed;

    private int mMoveLength = 0;
    private Rect mDest = null;//绘制的目标区域大小
    private Rect mSrc = null;//截取源图片的大小
    private int mDeltX = 0;//移动的偏移量
    private Paint mPaint = null;
    private int mWidth = 100;
    private int mHeigth = 100;

    private float mLastX;
    private float mCurrentX;

    private String TAG = "SwitchButton";

    public SwitchButton(Context context) {
        super(context);
        initView();
    }

    public SwitchButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public SwitchButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        Resources resources = getResources();
        mSwitchBottom = BitmapFactory.decodeResource(resources, R.drawable.switch_bottom);
        mSwitchFrame = BitmapFactory.decodeResource(resources, R.drawable.switch_frame);
        mSwitchMask = BitmapFactory.decodeResource(resources, R.drawable.switch_mask);
        mSwitchPressed = BitmapFactory.decodeResource(resources, R.drawable.switch_btn_pressed);

        //mMoveLength = mSwitchBottom.getWidth() - mSwitchFrame.getWidth();

        mSrc = new Rect();
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setAlpha(255);
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = getMeasuredWidth();
        mHeigth = getMeasuredHeight();
        mMoveLength = mWidth;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        if (mDest == null)
            mDest = new Rect(0, 0, mWidth, mHeigth);

        if (mDeltX > 0) {
            mSrc.set(mSwitchBottom.getWidth() * mDeltX / mMoveLength, 0, mSwitchBottom.getWidth() * mDeltX / mMoveLength + mSwitchBottom.getWidth(), mSwitchBottom.getHeight());
        } else if (mDeltX <= 0) {
            mSrc.set(-mSwitchBottom.getWidth() * mDeltX / (mMoveLength*2), 0, mSwitchBottom.getWidth() / 2 - mSwitchBottom.getWidth() * mDeltX / mMoveLength, mSwitchBottom.getHeight());
        }
        Log.d(TAG, "mDeltX: " + mDeltX);

        int count = canvas.saveLayer(new RectF(mDest), null, Canvas.MATRIX_SAVE_FLAG | Canvas.CLIP_SAVE_FLAG | Canvas.HAS_ALPHA_LAYER_SAVE_FLAG | Canvas.FULL_COLOR_LAYER_SAVE_FLAG | Canvas.CLIP_TO_LAYER_SAVE_FLAG);
        canvas.drawBitmap(mSwitchBottom, mSrc, mDest, null);
        canvas.drawBitmap(mSwitchPressed, mSrc, mDest, null);
        canvas.drawBitmap(mSwitchFrame, mSrc, mDest, null);
        canvas.drawBitmap(mSwitchMask, mSrc, mDest, mPaint);
        canvas.restoreToCount(count);


        //super.onDraw(canvas);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLastX = event.getX();
                return true;
            case MotionEvent.ACTION_UP:
                return true;
            case MotionEvent.ACTION_MOVE:
                mCurrentX = event.getX();
                mDeltX = (int) (mCurrentX - mLastX);
                if (Math.abs(mDeltX) > mWidth) {
                    mDeltX = mDeltX > 0 ? mWidth : -mWidth;
                }
                invalidate();
                return true;
        }


        return super.onTouchEvent(event);
    }

    @Override
    public void setChecked(boolean checked) {
        mIsCheck = checked;
    }

    @Override
    public boolean isChecked() {
        return mIsCheck;
    }

    @Override
    public void toggle() {
        mIsCheck = !mIsCheck;
    }
}
