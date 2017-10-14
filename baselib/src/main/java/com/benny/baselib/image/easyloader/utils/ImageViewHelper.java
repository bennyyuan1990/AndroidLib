package com.benny.baselib.image.easyloader.utils;

import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import java.lang.reflect.Field;

/**
 * Created by yuanbb on 2017/10/10.
 */

public class ImageViewHelper {

    private static int DEFAULT_WIDTH = 200;
    private static int DEFAULT_HEIGHT = 200;

    public static int getImageViewWidth(ImageView imageView) {
        if (imageView != null) {
            LayoutParams params = imageView.getLayoutParams();

            int width = 0;
            if (params != null && params.width != LayoutParams.WRAP_CONTENT) {
                width = imageView.getWidth();
            }

            if (width <= 0 && params != null) {
                width = params.width;
            }
            if (width <= 0) {
                width = getImageViewFieldValue(imageView, "mMaxWidth");
            }
            return width;

        }
        return DEFAULT_WIDTH;
    }


    public static int getImageViewHeight(ImageView imageView) {
        if (imageView != null) {
            LayoutParams params = imageView.getLayoutParams();

            int height = 0;
            if (params != null && params.height != LayoutParams.WRAP_CONTENT) {
                height = imageView.getWidth();
            }

            if (height <= 0 && params != null) {
                height = params.height;
            }
            if (height <= 0) {
                height = getImageViewFieldValue(imageView, "mMaxHeight");
            }
            return height;

        }
        return DEFAULT_HEIGHT;
    }

    private static int getImageViewFieldValue(ImageView imageView, String fieldName) {

        Class<? extends ImageView> imageCls = imageView.getClass();
        try {
            Field field = imageCls.getDeclaredField(fieldName);
            field.setAccessible(true);
            return field.getInt(imageView);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }


}
