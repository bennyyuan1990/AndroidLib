package com.benny.baselib.skin.attr;

import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.TextView;
import com.benny.baselib.skin.SkinManager;
import com.benny.baselib.skin.SkinResource;

/**
 * Created by yuanbb on 2017/9/7.
 */

public enum SkinType {
    TEXTCOLOR("textColor") {
        @Override
        public void skin(View view, String resName) {
            SkinResource skinResource = SkinManager.getInstance().getSkinResource();
            if (skinResource != null) {
                ColorStateList color = skinResource.getColor(resName);
                if (color != null && view instanceof TextView) {
                    ((TextView) view).setTextColor(color);
                }

            }
        }
    }, BACKGROUND("background") {
        @Override
        public void skin(View view, String resName) {

            SkinResource skinResource = SkinManager.getInstance().getSkinResource();
            if (skinResource != null) {
                Drawable drawable = skinResource.getDrawableByName(resName);
                if (drawable != null) {
                    view.setBackground(drawable);
                    return;
                }

                ColorStateList color = skinResource.getColor(resName);
                if (color != null) {
                    view.setBackgroundColor(color.getDefaultColor());
                    return;
                }

            }

        }
    }, SRC("src") {
        @Override
        public void skin(View view, String resName) {
            SkinResource skinResource = SkinManager.getInstance().getSkinResource();
            if (skinResource != null) {
                Drawable drawable = skinResource.getDrawableByName(resName);
                if (drawable != null) {
                    view.setBackground(drawable);
                }
            }
        }
    };

    private String mResName;

    SkinType(String resName) {
        this.mResName = resName;
    }

    public abstract void skin(View view, String resName);


    public String getResName() {
        return mResName;
    }
}
