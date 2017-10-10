package com.benny.baselib.image.easyloader.cache;

import android.graphics.Bitmap;
import com.benny.baselib.image.easyloader.request.BitmapRequest;

/**
 * Created by Benny on 2017/10/4.
 */

public interface BitmapCache {

    /**
     * 缓存bitmap
     */
    void put(BitmapRequest request, Bitmap bitmap);

    /**
     * 获取BitmapRequest缓存Bitmap
     */
    Bitmap get(BitmapRequest request);

    /**
     * 移除缓存
     */
    void remove(BitmapRequest request);

}
