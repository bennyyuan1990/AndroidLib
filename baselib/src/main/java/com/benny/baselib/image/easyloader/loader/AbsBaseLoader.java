package com.benny.baselib.image.easyloader.loader;

import android.graphics.Bitmap;
import android.sax.RootElement;
import android.widget.ImageView;
import com.benny.baselib.image.easyloader.cache.BitmapCache;
import com.benny.baselib.image.easyloader.config.DisplayConfig;
import com.benny.baselib.image.easyloader.request.BitmapRequest;

/**
 * Created by Benny on 2017/10/4.
 */

public abstract class AbsBaseLoader implements Loader {

    private BitmapCache mBitmapCache = EasyImageLoader.getInstance().getImageLoaderConfig().getBitmapCache();

    private DisplayConfig mDisplayConfig = EasyImageLoader.getInstance().getImageLoaderConfig().getDisplayConfig();

    @Override
    public void loadImage(BitmapRequest request) {
        Bitmap bitmap = mBitmapCache.get(request);
        if (bitmap == null) {
            //显示  加载中图片
            showLoadingImage(request);
            //加载 图片
            bitmap = onLoadBitmap(request);
            //缓存图片
            cacheBitmap(request, bitmap);
        }

        delivery2UIThread(request, bitmap);

    }

    private void delivery2UIThread(final BitmapRequest request, final Bitmap bitmap) {
        ImageView imageView = request.getImageView();
        if (imageView == null) {
            return;
        }

        imageView.post(new Runnable() {
            @Override
            public void run() {
                updateImageView(request, bitmap);
            }
        });
    }

    private void updateImageView(BitmapRequest request, Bitmap bitmap) {
        ImageView imageView = request.getImageView();

        if (bitmap != null && imageView.getTag().equals(request.getUrl())) {
            imageView.setImageBitmap(bitmap);
        }

        if (bitmap == null && mDisplayConfig != null && mDisplayConfig.failedImage > 0) {
            imageView.setImageResource(mDisplayConfig.failedImage);
        }

        if (request.getImageLoadListener() != null) {
            request.getImageLoadListener().onCompleted(imageView, bitmap, request.getUrl());
        }
    }

    /**
     * 缓存图片
     */
    private void cacheBitmap(BitmapRequest request, Bitmap bitmap) {
        if (request != null && bitmap != null) {
            synchronized (AbsBaseLoader.class) {
                mBitmapCache.put(request, bitmap);
            }
        }
    }

    /**
     * 加载图片
     */
    protected abstract Bitmap onLoadBitmap(BitmapRequest request);

    /**
     * 显示 加载中 提示图片
     */
    private void showLoadingImage(BitmapRequest request) {
        if (hasLoadingPlaceHolder()) {
            final ImageView imageView = request.getImageView();
            if (imageView != null) {
                imageView.post(new Runnable() {
                    @Override
                    public void run() {
                        imageView.setImageResource(mDisplayConfig.loadingImage);
                    }
                });
            }
        }
    }


    private boolean hasLoadingPlaceHolder() {
        return mDisplayConfig != null && mDisplayConfig.loadingImage > 0;
    }
}
