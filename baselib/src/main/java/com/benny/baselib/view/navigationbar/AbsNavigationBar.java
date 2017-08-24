package com.benny.baselib.view.navigationbar;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.benny.baselib.view.navigationbar.AbsNavigationBar.Builder.AbsNavigationBarParams;

/**
 * Created by yuanbb on 2017/8/24.
 */

public abstract class AbsNavigationBar<T extends AbsNavigationBarParams> implements INavigationBar {

    private View mNavigationView;
    protected T P;

    protected AbsNavigationBar(T params) {

        P = params;
        initNavigationView(params);
    }

    private void initNavigationView(T params) {
        if (params.mParent == null) {
            params.mParent = (ViewGroup) ((ViewGroup) ((Activity) params.mContext).findViewById(android.R.id.content).getRootView()).getChildAt(0);
        }

        mNavigationView = LayoutInflater.from(params.mContext).inflate(getNavigationLayoutID(), params.mParent, false);

        params.mParent.addView(mNavigationView,0);
        applyView();
    }


    public <V extends View> V findViewById(int viewId) {
        return mNavigationView.findViewById(viewId);
    }

    public void setOnClickListener(int viewId, View.OnClickListener listener) {
        View view = findViewById(viewId);
        if (view != null) {
            view.setVisibility(View.VISIBLE);
            view.setOnClickListener(listener);
        }
    }

    public void setText(int viewId, CharSequence text) {
        TextView view = findViewById(viewId);
        if (view != null) {
            view.setVisibility(View.VISIBLE);
            view.setText(text);
        }
    }

    public void setBackgroudDrawable(int viewId, Drawable drawable) {
        View view = findViewById(viewId);
        if (view != null) {
            view.setVisibility(View.VISIBLE);
            view.setBackground(drawable);
        }
    }

    public void setImageIcon(int viewId, int iconId) {
        View view = findViewById(viewId);
        if (view != null) {
            view.setVisibility(View.VISIBLE);
            view.setBackgroundResource(iconId);
        }
    }


    public static abstract class Builder {

        abstract AbsNavigationBar build();


        static class AbsNavigationBarParams {

            public Context mContext;
            public ViewGroup mParent;
        }


    }


}
