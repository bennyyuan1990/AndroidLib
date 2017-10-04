package com.benny.baselib.image.easyloader.config;

import com.benny.baselib.image.easyloader.cache.BitmapCache;
import com.benny.baselib.image.easyloader.policy.LoadPolicy;

/**
 * 图片加载策略配置
 * Created by Benny on 2017/10/4.
 */

public class ImageLoaderConfig {
    private ImageLoaderConfig() {
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

    private static class Builder {
        private ImageLoaderConfig mConfig;
        private DisplayConfig mDisplayConfig;

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
            mDisplayConfig.mLoadingImage = loadingImage;
            return this;
        }

        public Builder setFailedImage(int failedImage) {
            mDisplayConfig.mFailedImage = failedImage;
            return this;
        }
    }


}
