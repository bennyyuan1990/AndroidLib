package com.benny.baselib.view.navigationbar;

import android.content.Context;
import android.view.View;
import com.benny.baselib.R;
import com.benny.baselib.view.navigationbar.DefaultNavigationBar.Builder.DefaultNavigationBarParams;

/**
 * Created by yuanbb on 2017/8/24.
 */

public class DefaultNavigationBar extends AbsNavigationBar<DefaultNavigationBarParams> {

    protected DefaultNavigationBar(DefaultNavigationBarParams params) {

        super(params);

    }

    @Override
    public int getNavigationLayoutID() {
        return R.layout.navigation_default;
    }

    @Override
    public void applyView() {
        if (P.mCenterTitle != null) {
            setText(R.id.navigation_default_center_title_tv, P.mCenterTitle);
        }
        if (P.mRightTitle != null) {
            setText(R.id.navigation_default_right_title_tv, P.mRightTitle);
        }
        if (P.mLeftTitle != null) {
            setText(R.id.navigation_default_left_title_tv, P.mLeftTitle);
        }
        if (P.mRightOnClickListener != null) {
            setOnClickListener(R.id.navigation_default_right_title_tv, P.mRightOnClickListener);
        }
        if (P.mLeftOnClickListener != null) {
            setOnClickListener(R.id.navigation_default_left_title_tv, P.mLeftOnClickListener);
        }

    }

    public static class Builder extends AbsNavigationBar.Builder {

        private DefaultNavigationBarParams P;

        public Builder(Context context) {
            P = new DefaultNavigationBarParams();
            P.mContext = context;
        }

        public Builder setCenterTitle(CharSequence text) {
            P.mCenterTitle = text;
            return this;
        }


        public Builder setLeftTitle(CharSequence text) {
            P.mLeftTitle = text;
            return this;
        }

        public Builder setRightTitle(CharSequence text) {
            P.mRightTitle = text;
            return this;
        }


        public Builder setRightClickListener(View.OnClickListener listener) {
            P.mRightOnClickListener = listener;
            return this;
        }


        public Builder setLeftClickListener(View.OnClickListener listener) {
            P.mLeftOnClickListener = listener;
            return this;
        }


        public static class DefaultNavigationBarParams extends AbsNavigationBar.Builder.AbsNavigationBarParams {

            CharSequence mCenterTitle;
            CharSequence mLeftTitle;
            CharSequence mRightTitle;
            View.OnClickListener mLeftOnClickListener;
            View.OnClickListener mRightOnClickListener;

        }

        @Override
        public DefaultNavigationBar build() {
            return new DefaultNavigationBar(P);
        }


    }
}
