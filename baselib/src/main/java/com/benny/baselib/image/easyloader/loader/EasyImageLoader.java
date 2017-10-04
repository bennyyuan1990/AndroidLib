package com.benny.baselib.image.easyloader.loader;

import android.text.style.EasyEditSpan;
import android.widget.ImageView;

import com.benny.baselib.image.easyloader.config.DisplayConfig;
import com.benny.baselib.image.easyloader.config.ImageLoaderConfig;
import com.benny.baselib.image.easyloader.request.RequestQueue;

/**
 * Created by Benny on 2017/10/4.
 */

public class EasyImageLoader {

    private ImageLoaderConfig mImageLoaderConfig;

    private RequestQueue mRequestQueue;

    private static EasyImageLoader mInstance;

    public ImageLoaderConfig getImageLoaderConfig() {
        return mImageLoaderConfig;
    }

    private EasyImageLoader() {

    }

    private EasyImageLoader(ImageLoaderConfig imageLoaderConfig) {
        this.mImageLoaderConfig = imageLoaderConfig;
    }


    public static EasyImageLoader getInstance(ImageLoaderConfig imageLoaderConfig) {
        if (mInstance == null) {
            synchronized (EasyImageLoader.class) {
                if (mInstance == null) {
                    mInstance = new EasyImageLoader(imageLoaderConfig);
                }
            }
        }
        return mInstance;
    }


    public static EasyImageLoader getInstance() {
        if (mInstance == null) {
            throw new UnsupportedOperationException("UnInit");
        }
        return mInstance;
    }


    public void displayImage(ImageView imageView,String uri){

    }

    public void displayImage(ImageView imageView, String uri, DisplayConfig config,ImageLoadListener listener){

    }


    public static interface  ImageLoadListener{

    }

}
