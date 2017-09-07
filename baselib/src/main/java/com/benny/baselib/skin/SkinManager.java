package com.benny.baselib.skin;

import android.content.Context;

/**
 * Created by yuanbb on 2017/9/7.
 */

public class SkinManager {

    private static SkinManager mInstance;
    private SkinResource mSkinResource;
    private Context mContext;

    static {
        mInstance = new SkinManager();
    }


    public static SkinManager getInstance() {
        return mInstance;
    }


    public void init(Context context) {
        mContext = context.getApplicationContext();
    }


    /**
     * 加载皮肤资源
     */
    public void loadSkin(String skinPath) {
        mSkinResource = new SkinResource(skinPath,mContext);
    }

    /**
     * 重置默认皮肤
     */
    public void resetDefault() {

    }

    public SkinResource getSkinResource() {
        return mSkinResource;
    }
}
