package com.benny.baselib.skin.attr;

import android.view.View;

/**
 * Created by yuanbb on 2017/9/7.
 */

public class SkinAttr {

    private String mResName;
    private SkinType mSkinType;

    public SkinAttr(String resName, SkinType skinType) {
        this.mResName = mResName;
        this.mSkinType = skinType;
    }


    public void skin(View view){
        mSkinType.skin(view,mResName);
    }

}
