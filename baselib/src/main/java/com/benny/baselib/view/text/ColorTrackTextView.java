package com.benny.baselib.view.text;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import com.benny.baselib.R;

/**
 * Created by Benny on 2017/9/10.
 */

public class ColorTrackTextView extends android.support.v7.widget.AppCompatTextView {
    private Paint mPaint;
    private int mCoverColor = Color.GREEN;
    private float mStep = 0.5f;

    public void setStep(float step) {
        if (step < 0) step = 0;
        else if (step > 1) step = 1;
        this.mStep = step;
        postInvalidate();
    }

    public ColorTrackTextView(Context context) {
        this(context, null);
    }

    public ColorTrackTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ColorTrackTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        mPaint = new Paint(getPaint());

        TypedArray typedArray = context.obtainStyledAttributes(R.styleable.ColorTrackTextView);
        mCoverColor = typedArray.getColor(R.styleable.ColorTrackTextView_coverColor, mCoverColor);
        typedArray.recycle();

        mPaint.setColor(mCoverColor);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        float textWidth = getPaint().measureText(getText().toString());
        LinearGradient linearGradient = new LinearGradient(0, getHeight() / 2,
                textWidth, getHeight() / 2,
                new int[]{getTextColors().getDefaultColor(), mCoverColor, getTextColors().getDefaultColor()},
                new float[]{0, 0.5f, 1}, Shader.TileMode.CLAMP);
        Matrix matrix = new Matrix();
        matrix.setTranslate((mStep*2 - 1) * textWidth, 0);
        linearGradient.setLocalMatrix(matrix);
        getPaint().setShader(linearGradient);

        super.onDraw(canvas);
    }
}
