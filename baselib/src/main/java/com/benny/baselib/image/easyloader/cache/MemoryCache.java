package com.benny.baselib.image.easyloader.cache;

import android.graphics.Bitmap;
import android.util.LruCache;
import com.benny.baselib.image.easyloader.request.BitmapRequest;

/**
 * Created by Benny on 2017/10/4.
 */

public class MemoryCache implements BitmapCache {

    private LruCache<String, Bitmap> mLruCache;


    private static MemoryCache mInstance;

    public static MemoryCache getInstance() {
        if (mInstance == null) {
            synchronized (DiskCache.class) {
                mInstance = new MemoryCache();
            }
        }
        return mInstance;
    }

    private MemoryCache() {
        int maxSize = (int) (Runtime.getRuntime().freeMemory() / 1024 / 8);
        mLruCache = new LruCache<String, Bitmap>(maxSize) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getRowBytes() * value.getHeight();
            }
        };
    }


    @Override
    public void put(BitmapRequest request, Bitmap bitmap) {
        mLruCache.put(request.getUrlMD5(), bitmap);
    }

    @Override
    public Bitmap get(BitmapRequest request) {
        return mLruCache.get(request.getUrlMD5());
    }

    @Override
    public void remove(BitmapRequest request) {
        mLruCache.remove(request.getUrlMD5());
    }
}
