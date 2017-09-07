package com.benny.baselib.skin.attr;

import android.view.View;
import java.util.List;

/**
 * Created by yuanbb on 2017/9/7.
 */

public class SkinView {

    private View mView;

    private List<SkinAttr> mAtts;


    public SkinView(View mView, List<SkinAttr> mAtts) {
        this.mView = mView;
        this.mAtts = mAtts;
    }


    public void skin() {
        if (mView == null || mAtts == null || mAtts.size() == 0) {
            return;
        }

        for (SkinAttr attr : mAtts) {
            attr.skin(mView);
        }
    }
}
