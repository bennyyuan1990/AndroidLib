package com.benny.baselib.image.easyloader.cache;

import android.content.Context;
import android.graphics.Bitmap;
import com.benny.baselib.image.easyloader.request.BitmapRequest;

/**
 * Created by yuanbb on 2017/10/10.
 */

public class DoubleCache implements BitmapCache {

    private MemoryCache mMemoryCache;
    private DiskCache mDiskCache;

    private static DoubleCache mInstance;

    public static DoubleCache getInstance(Context context) {
        if (mInstance == null) {
            synchronized (DiskCache.class) {
                mInstance = new DoubleCache(context);
            }
        }
        return mInstance;
    }

    private DoubleCache(Context context) {
        mMemoryCache = MemoryCache.getInstance();
        mDiskCache = DiskCache.getInstance(context);
    }

    @Override
    public void put(BitmapRequest request, Bitmap bitmap) {
        mMemoryCache.put(request, bitmap);
        mDiskCache.put(request, bitmap);
    }

    @Override
    public Bitmap get(BitmapRequest request) {
        Bitmap bitmap = mMemoryCache.get(request);
        if (bitmap == null) {
            bitmap = mDiskCache.get(request);
            if (bitmap != null) {
                mMemoryCache.put(request, bitmap);
            }
        }
        return bitmap;
    }

    @Override
    public void remove(BitmapRequest request) {
        mMemoryCache.remove(request);
        mDiskCache.remove(request);
    }
}
