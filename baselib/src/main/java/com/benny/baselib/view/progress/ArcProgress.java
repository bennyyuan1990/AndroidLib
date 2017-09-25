package com.benny.baselib.view.progress;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.benny.baselib.R;

/**
 * Created by Benny on 2017/9/10.
 */

public class ArcProgress extends View {
    private int mOutColor = Color.RED;
    private int mInColor = Color.BLUE;
    private float mBorderWidth = 10;
    private int mTextColor = Color.BLACK;
    private float mTextSize = 30;
    private Paint mPaint;
    private float mMaxStep = 100;
    private float mCurrentStep = 50;


    public ArcProgress(Context context) {
        this(context, null);
    }

    public ArcProgress(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ArcProgress(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, @Nullable AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs,R.styleable.ArcProgress);
        mOutColor = typedArray.getColor(R.styleable.ArcProgress_outColor, mOutColor);
        mInColor = typedArray.getColor(R.styleable.ArcProgress_inColor, mInColor);
        mBorderWidth = typedArray.getDimension(R.styleable.ArcProgress_arcBorderWidth, mBorderWidth);
        mTextColor = typedArray.getColor(R.styleable.ArcProgress_textColor, mTextColor);
        mTextSize = typedArray.getDimension(R.styleable.ArcProgress_textSize, mTextSize);
        typedArray.recycle();
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setDither(true);

        mPaint.setStrokeWidth(mBorderWidth);
        mPaint.setTextSize(mTextSize);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
    }

    public void setMaxStep(float maxStep) {
        this.mMaxStep = maxStep;
        postInvalidate();
    }

    public void setCurrentStep(float currentStep) {
        this.mCurrentStep = currentStep;
        postInvalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        if (widthMode == MeasureSpec.AT_MOST)
            width = (int) (getContext().getResources().getDisplayMetrics().density * 100);

        if (heightMode == MeasureSpec.AT_MOST)
            height = (int) (getContext().getResources().getDisplayMetrics().density * 100);

        setMeasuredDimension(width, height);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //外圆弧
        RectF outRect = new RectF(mBorderWidth, mBorderWidth, getWidth() - mBorderWidth, getHeight() - mBorderWidth);
        mPaint.setColor(mOutColor);
        mPaint.setStrokeWidth(mBorderWidth);
        mPaint.setStyle(Paint.Style.STROKE);
        canvas.drawArc(outRect, 135, 270, false, mPaint);


        //内圆弧
        if (mMaxStep > 0) {
            if (mCurrentStep > mMaxStep) mCurrentStep = mMaxStep;
            float percent = mCurrentStep / mMaxStep;

            mPaint.setColor(mInColor);
            canvas.drawArc(outRect, 135, percent * 270, false, mPaint);
        }

        String text = (int)mCurrentStep + "/" + (int)mMaxStep;
        float textWidth = mPaint.measureText(text);
        float startX = (getWidth() - textWidth) / 2;
        Paint.FontMetrics fontMetrics = mPaint.getFontMetrics();
        float startY = (getHeight() / 2) + (fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.bottom;
        mPaint.setColor(mTextColor);
        mPaint.setStrokeWidth(0);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        canvas.drawText(text, startX, startY, mPaint);


    }
}
