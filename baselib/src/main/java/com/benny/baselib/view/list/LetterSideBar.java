package com.benny.baselib.view.list;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.benny.baselib.R;

/**
 * Created by Benny on 2017/9/10.
 */

public class LetterSideBar extends View {

    private Paint mPaint;
    private Paint mPressPaint;
    private int mTextColor = Color.BLACK;
    private int mPressColor = Color.RED;
    private float mTextSize = 20;
    private String[] mLetter = new String[]{"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "#"};
    private int mLetterWidth;
    private int mItemHeight;
    private String mPrePressLetter = null;
    private OnSelectChangeListener mOnSelectChangeListener;

    public void setOnSelectChangeListener(OnSelectChangeListener onSelectChangeListener) {
        this.mOnSelectChangeListener = onSelectChangeListener;
    }

    public LetterSideBar(Context context) {
        this(context, null);
    }

    public LetterSideBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LetterSideBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setDither(true);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.LetterSideBar);
        mTextColor = typedArray.getColor(R.styleable.LetterSideBar_barTextColor, mTextColor);
        mTextSize = typedArray.getDimension(R.styleable.LetterSideBar_barTextSize, mTextSize);
        mPressColor = typedArray.getColor(R.styleable.LetterSideBar_barPressColor, mPressColor);
        typedArray.recycle();
        mPaint.setTextSize(mTextSize);
        mPaint.setColor(mTextColor);
        mPressPaint = new Paint(mPaint);
        mPressPaint.setColor(mPressColor);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int height = MeasureSpec.getSize(heightMeasureSpec);
        mLetterWidth = (int) mPaint.measureText(mLetter[0]);
        mItemHeight = (height - getPaddingTop() - getPaddingBottom()) / mLetter.length;
        setMeasuredDimension(mLetterWidth + getPaddingLeft() + getPaddingRight(), height);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint.FontMetrics fontMetrics = mPaint.getFontMetrics();
        int baseline = (int) ((fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.bottom);
        int x = getPaddingLeft();
        int y = getPaddingTop() + mItemHeight / 2 + baseline;
        for (int i = 0, count = mLetter.length; i < count; i++) {
            canvas.drawText(mLetter[i], x, y + i * mItemHeight, mLetter[i] == mPrePressLetter ? mPressPaint : mPaint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                checkPress(event.getX(), event.getY());
                break;
            case MotionEvent.ACTION_MOVE:
                checkPress(event.getX(), event.getY());
                break;
            case MotionEvent.ACTION_UP:
                mPrePressLetter = null;
                if (mOnSelectChangeListener != null) {
                    mOnSelectChangeListener.selectChange(mPrePressLetter);
                }
                postInvalidate();
                break;
            default:
                break;
        }
        return true;
    }

    private void checkPress(float x, float y) {
        int index = (int) ((y - getPaddingTop()) / mItemHeight);
        if (index >= mLetter.length) {
            index = -1;
        }

        if (index < 0) {
            if (mOnSelectChangeListener != null && mPrePressLetter != null) {
                mOnSelectChangeListener.selectChange(null);
            }
            mPrePressLetter = null;
            postInvalidate();
            return;
        }

        if (mLetter[index] != mPrePressLetter) {
            mPrePressLetter = mLetter[index];
            if (mOnSelectChangeListener != null) {
                mOnSelectChangeListener.selectChange(mPrePressLetter);
            }
            postInvalidate();
        }
    }

    public interface OnSelectChangeListener {

        void selectChange(String letter);
    }

}
