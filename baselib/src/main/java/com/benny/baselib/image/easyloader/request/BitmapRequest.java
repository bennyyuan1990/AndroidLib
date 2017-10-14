package com.benny.baselib.image.easyloader.request;

import android.support.annotation.NonNull;
import android.widget.ImageView;
import com.benny.baselib.image.easyloader.config.DisplayConfig;
import com.benny.baselib.image.easyloader.loader.EasyImageLoader;
import com.benny.baselib.image.easyloader.loader.EasyImageLoader.ImageLoadListener;
import com.benny.baselib.image.easyloader.policy.LoadPolicy;
import com.benny.baselib.utils.EncryptHelper;
import java.lang.ref.WeakReference;

/**
 * 图片加载请求参数
 * Created by Benny on 2017/10/4.
 */

public class BitmapRequest implements Comparable<BitmapRequest> {

    /**
     * 加载策略
     */
    private LoadPolicy mLoadPolicy = EasyImageLoader.getInstance().getImageLoaderConfig().getLoadPolicy();

    /**
     * ImageView引用
     */
    private WeakReference<ImageView> mImageView;
    /**
     * 图片加载路径地址
     */
    private String mUrl;
    private String mUrlMD5;


    private DisplayConfig mDisplayConfig;
    /**
     * 图片加载监听
     */
    private EasyImageLoader.ImageLoadListener mImageLoadListener;

    private int mSerialNo;

    /**
     * 请求序列号
     */
    public int getSerialNo() {
        return mSerialNo;
    }

    public void setSerialNo(int serialNo) {
        this.mSerialNo = serialNo;
    }

    public ImageView getImageView() {
        if (mImageView != null && mImageView.get() != null) {
            return mImageView.get();
        }
        return null;
    }

    public String getUrl() {
        return mUrl;
    }

    public String getUrlMD5() {
        return mUrlMD5;
    }

    public ImageLoadListener getImageLoadListener() {
        return mImageLoadListener;
    }

    public BitmapRequest(ImageView imageView, String url, DisplayConfig displayConfig, EasyImageLoader.ImageLoadListener imageLoadListener) {
        this.mImageView = new WeakReference<>(imageView);
        imageView.setTag(url);
        this.mUrl = url;
        this.mUrlMD5 = EncryptHelper.MD5(url);
        this.mImageLoadListener = imageLoadListener;
        this.mDisplayConfig = displayConfig;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        BitmapRequest that = (BitmapRequest) o;

        if (mSerialNo != that.mSerialNo) {
            return false;
        }
        return mLoadPolicy != null ? mLoadPolicy.equals(that.mLoadPolicy) : that.mLoadPolicy == null;

    }

    @Override
    public int hashCode() {
        int result = mLoadPolicy != null ? mLoadPolicy.hashCode() : 0;
        result = 31 * result + mSerialNo;
        return result;
    }

    /**
     * 处理请求加载优先级
     */
    @Override
    public int compareTo(@NonNull BitmapRequest o) {
        return  mLoadPolicy.compareTo(this, o);
    }
}
