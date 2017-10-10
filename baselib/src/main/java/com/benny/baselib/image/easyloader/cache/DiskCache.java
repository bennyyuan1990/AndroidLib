package com.benny.baselib.image.easyloader.cache;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.os.Environment;
import com.benny.baselib.image.easyloader.disklrucache.DiskLruCache;
import com.benny.baselib.image.easyloader.disklrucache.DiskLruCache.Editor;
import com.benny.baselib.image.easyloader.disklrucache.DiskLruCache.Snapshot;
import com.benny.baselib.image.easyloader.disklrucache.Util;
import com.benny.baselib.image.easyloader.request.BitmapRequest;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by Benny on 2017/10/4.
 */

public class DiskCache implements BitmapCache {

    private DiskLruCache mDiskLruCache;

    //缓存路径
    private String mCacheDir = "Image";
    private static final int MB = 1024 * 1024;


    private static DiskCache mInstance;

    public static DiskCache getInstance(Context context) {
        if (mInstance == null) {
            synchronized (DiskCache.class) {
                mInstance = new DiskCache(context);
            }
        }
        return mInstance;
    }

    private DiskCache(Context context) {
        initCache(context);
    }


    private void initCache(Context context) {
        File directory = getDiskCache(mCacheDir, context);
        if (!directory.exists()) {
            directory.mkdir();
        }
        try {
            mDiskLruCache = DiskLruCache.open(directory, 1, 1, MB * 50);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private File getDiskCache(String mCacheDir, Context context) {

        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            return new File(Environment.getExternalStorageDirectory(), mCacheDir);
        } else {
            return new File(context.getCacheDir(), mCacheDir);
        }
    }


    @Override
    public void put(BitmapRequest request, Bitmap bitmap) {
        try {
            Editor editor = mDiskLruCache.edit(request.getUrlMD5());
            OutputStream os = editor.newOutputStream(0);
            if (persistBitmap2Disk(bitmap, os)) {
                editor.commit();
            } else {
                editor.abort();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean persistBitmap2Disk(Bitmap bitmap, OutputStream os) {
        BufferedOutputStream bos = new BufferedOutputStream(os);
        bitmap.compress(CompressFormat.JPEG, 100, bos);
        try {
            bos.flush();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            Util.closeQuietly(bos);
        }
        return true;
    }

    @Override
    public Bitmap get(BitmapRequest request) {

        try {
            Snapshot snapshot = mDiskLruCache.get(request.getUrlMD5());
            if (snapshot != null) {
                return BitmapFactory.decodeStream(snapshot.getInputStream(0));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }

    @Override
    public void remove(BitmapRequest request) {
        try {
            mDiskLruCache.remove(request.getUrlMD5());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
