package com.yuanbb.common.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.yuanbb.common.R;

/**
 * 进度条按钮
 * Created by yuanbb on 2016/2/24.
 */
public class ProgressButton extends View {
    private Paint.FontMetrics _fontMetrics;
    private int _foreground; //前景色
    private int _background; //背景色
    private int _textColor; //文本颜色
    private float _textSize; //文本尺寸
    private String _text; //文本内容
    private int _max; //进度最大值
    private int _progress; //进度值

    private Paint _paint;
    private int _corner = 5;//圆角大小


    private OnProgressButtonClickListener _buttonListener;

    public ProgressButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public ProgressButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }


    private void init(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ProgressButton);

        _background = typedArray.getInteger(R.styleable.ProgressButton_backgroundColor, Color.parseColor("#C6C6C6"));
        _foreground = typedArray.getInteger(R.styleable.ProgressButton_foreground, Color.rgb(20, 131, 214));
        _textColor = typedArray.getInteger(R.styleable.ProgressButton_textColor, Color.WHITE);
        _max = typedArray.getInteger(R.styleable.ProgressButton_max, 100);
        _progress = typedArray.getInteger(R.styleable.ProgressButton_progress, 0);
        _text = typedArray.getString(R.styleable.ProgressButton_text);
        _textSize = typedArray.getDimension(R.styleable.ProgressButton_textSize, 12);
        typedArray.recycle();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        _paint = new Paint();
        _paint.setAntiAlias(true);
        _paint.setStrokeWidth(5);

        //绘制背景
        RectF oval = new RectF(0, 0, getMeasuredWidth(), getMeasuredHeight());
        _paint.setColor(this._background);
        canvas.drawRoundRect(oval, _corner, _corner, _paint);

        //绘制进度条
        _paint.setColor(_foreground);
        if (_progress <= _corner) {
            oval = new RectF(0, _corner - _progress, getMeasuredWidth() * _progress / _max, getMeasuredHeight());
            canvas.drawRoundRect(oval, _progress, _progress, _paint);
        } else {
            oval = new RectF(0, 0, getMeasuredWidth() * _progress / _max, getMeasuredHeight());
            canvas.drawRoundRect(oval, _corner, _corner, _paint);
        }

        //绘制文本
        if (TextUtils.isEmpty(_text)) return;

        _paint.setTextSize(_textSize);
        _paint.setColor(_textColor);
        _fontMetrics = _paint.getFontMetrics();
        float textVerticalBaselineY = getMeasuredHeight() / 2 - _fontMetrics.descent + (_fontMetrics.descent - _fontMetrics.ascent) / 2;
        canvas.drawText(_text, (getMeasuredWidth() - _paint.measureText(_text)) / 2, textVerticalBaselineY, _paint);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        _paint = new Paint();
        _paint.setTextSize(_textSize);

        Rect rect = new Rect();
        _paint.getTextBounds(_text, 0, _text.length(), rect);

        int width = rect.width();
        int heigth = rect.height();
        float textWidth = _paint.measureText(_text, 0, _text.length());


        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int hegithSize = MeasureSpec.getSize(heightMeasureSpec);

        switch (widthMode) {
            case MeasureSpec.AT_MOST:
                width = Math.min(widthSize,width + _corner * 2) ;
                break;
            case MeasureSpec.EXACTLY:
                width =widthSize;
                break;
        }

        switch (heightMode) {
            case MeasureSpec.AT_MOST:
                heigth =Math.min(hegithSize,heigth+ _corner * 2) ;
                break;
            case MeasureSpec.EXACTLY:
                heigth = hegithSize;
                break;
        }

        setMeasuredDimension(width, heigth);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (_buttonListener != null) _buttonListener.onClickListener();
                break;
        }
        return super.onTouchEvent(event);
    }

    /**
     * 设置进度最大值
     *
     * @param max
     */
    public void setMax(int max) {
        _max = max;
    }

    /**
     * 设置按钮文本
     *
     * @param text
     */
    public void setText(String text) {
        _text = text;
    }

    /**
     * 设置按钮前景色
     *
     * @param color
     */
    public void setForeground(int color) {
        _foreground = color;
    }

    /**
     * 设置按钮背景色
     *
     * @param color
     */
    public void setBackground(int color) {
        _background = color;
    }

    /**
     * 设置文本颜色
     *
     * @param color
     */
    public void setTextColor(int color) {
        _textColor = color;
    }

    /**
     * 设置文本字体大小
     *
     * @param size
     */
    public void setTextSize(float size) {
        _textSize = size;
    }

    /**
     * 设置进度值
     *
     * @param progress
     */
    public void setProgress(int progress) {
        if (progress > _max) return;
        _progress = progress;
        postInvalidate();
    }

    /**
     * 设置进度值
     *
     * @return
     */
    public int getProgress() {
        return _progress;
    }

    /**
     * 获取进度最大值
     *
     * @return
     */
    public int getMax() {
        return _max;
    }

    /**
     * 设置按钮监听
     *
     * @param listener
     */
    public void setOnProgressButtonClickListener(OnProgressButtonClickListener listener) {
        _buttonListener = listener;
    }


    public interface OnProgressButtonClickListener {
        public void onClickListener();
    }


}
