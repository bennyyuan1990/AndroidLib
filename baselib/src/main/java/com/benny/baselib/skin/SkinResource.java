package com.benny.baselib.skin;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import java.lang.reflect.Method;

/**
 * Created by yuanbb on 2017/9/7.
 */

public class SkinResource {

    private Resources mSkinResources;
    private String mPackageName;

    @SuppressWarnings("deprecation")
    public SkinResource(String skinPath, Context context) {
        try {
            Resources resources = context.getResources();

            AssetManager assetManager = AssetManager.class.newInstance();
            Method addAssetPath = AssetManager.class.getMethod("addAssetPath", String.class);
            addAssetPath.invoke(assetManager, skinPath);

            mSkinResources = new Resources(assetManager, resources.getDisplayMetrics(), resources.getConfiguration());
            mPackageName = context.getPackageManager().getPackageArchiveInfo(skinPath, PackageManager.GET_ACTIVITIES).packageName;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @SuppressWarnings("deprecation")
    public Drawable getDrawableByName(String resName) {
        try {
            int resId = mSkinResources.getIdentifier(resName, "drawable", mPackageName);
            return mSkinResources.getDrawable(resId);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @SuppressWarnings("deprecation")
    public ColorStateList getColor(String resName) {
        try {
            int resId = mSkinResources.getIdentifier(resName, "color", mPackageName);
            return mSkinResources.getColorStateList(resId);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
