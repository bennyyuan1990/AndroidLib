package com.benny.baselib.image.easyloader.config;

import com.benny.baselib.image.easyloader.cache.BitmapCache;
import com.benny.baselib.image.easyloader.cache.DoubleCache;
import com.benny.baselib.image.easyloader.cache.MemoryCache;
import com.benny.baselib.image.easyloader.policy.LoadPolicy;
import com.benny.baselib.image.easyloader.policy.SerialPolicy;

/**
 * 图片加载策略配置
 * Created by Benny on 2017/10/4.
 */

public class ImageLoaderConfig {

    private ImageLoaderConfig() {
        mDisplayConfig = new DisplayConfig();
        mLoadPolicy = new SerialPolicy();
        mBitmapCache = MemoryCache.getInstance();
    }

    /**
     * 缓存池
     */
    private BitmapCache mBitmapCache;
    /**
     * 加载策略
     */
    private LoadPolicy mLoadPolicy;
    /**
     * 请求线程数
     */
    private int mThreadCount = Runtime.getRuntime().availableProcessors();


    private DisplayConfig mDisplayConfig;

    public BitmapCache getBitmapCache() {
        return mBitmapCache;
    }

    public LoadPolicy getLoadPolicy() {
        return mLoadPolicy;
    }

    public int getThreadCount() {
        return mThreadCount;
    }

    public DisplayConfig getDisplayConfig() {
        return mDisplayConfig;
    }

    private static class Builder {

        private ImageLoaderConfig mConfig;


        public Builder() {
            mConfig = new ImageLoaderConfig();
        }

        public Builder setCachePolicy(LoadPolicy loadPolicy) {
            mConfig.mLoadPolicy = loadPolicy;
            return this;
        }

        public Builder setBitmapCache(BitmapCache bitmapCache) {
            mConfig.mBitmapCache = bitmapCache;
            return this;
        }

        public Builder setThreadCount(int threadCount) {
            mConfig.mThreadCount = threadCount;
            return this;
        }


        public Builder setLoadingImage(int loadingImage) {
            mConfig.mDisplayConfig.loadingImage = loadingImage;
            return this;
        }

        public Builder setFailedImage(int failedImage) {
            mConfig.mDisplayConfig.failedImage = failedImage;
            return this;
        }
    }


}
