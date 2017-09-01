package com.benny.baselib.view.recyclerview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Benny on 2017/9/1.
 */

public class GridViewItemDecoration extends RecyclerView.ItemDecoration {
    private Paint mPaint;
    private Drawable mDrawable;

    public GridViewItemDecoration(Context context, int drawableID) {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mDrawable = ContextCompat.getDrawable(context, drawableID);
    }


    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        int position = parent.getChildAdapterPosition(view);
        if (position != 0) {
            outRect.top = mDrawable.getIntrinsicHeight();
            outRect.right = mDrawable.getIntrinsicWidth();
        }
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);

        horizontalDraw(c, parent);
        verticalDraw(c,parent);
    }

    private void horizontalDraw(Canvas canvas, RecyclerView parent) {
        int childCount = parent.getChildCount();
        Rect rect = new Rect();
        for (int i = 1; i < childCount; i++) {
            View child = parent.getChildAt(i);
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            rect.left = child.getLeft() - params.leftMargin;
            rect.right = child.getRight() - mDrawable.getIntrinsicWidth() - params.rightMargin;
            rect.bottom = child.getTop() - params.topMargin;
            rect.top = rect.bottom - mDrawable.getIntrinsicHeight();
            mDrawable.setBounds(rect);
            mDrawable.draw(canvas);
        }
    }


    private void verticalDraw(Canvas canvas, RecyclerView parent) {
        int childCount = parent.getChildCount();
        Rect rect = new Rect();
        for (int i = 1; i < childCount; i++) {
            View child = parent.getChildAt(i);
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            rect.left = child.getRight() + params.leftMargin;
            rect.right = rect.left + mDrawable.getIntrinsicWidth();
            rect.top = child.getTop() - params.topMargin;
            rect.bottom = child.getBottom() - params.bottomMargin;
            mDrawable.setBounds(rect);
            mDrawable.draw(canvas);
        }
    }
}
