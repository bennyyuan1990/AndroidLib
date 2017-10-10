package com.benny.baselib.image.easyloader.loader;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by yuanbb on 2017/10/10.
 */

public class LoaderManager {

    private Map<String, Loader> mLoaderMap = new HashMap<>();
    private static LoaderManager mLoaderManager;

    private LoaderManager() {
        register("http", new NetLoader());
        register("https", new NetLoader());
        register("file", new LocalLoader());
    }

    public static LoaderManager getInstance() {
        if (mLoaderManager == null) {
            synchronized (LoaderManager.class) {
                if (mLoaderManager == null) {
                    mLoaderManager = new LoaderManager();
                }
            }
        }
        return mLoaderManager;
    }

    private void register(String schema, Loader loader) {
        mLoaderMap.put(schema, loader);
    }


    public Loader getLoader(String schema) {
        if (mLoaderMap.containsKey(schema)) {
            return mLoaderMap.get(schema);
        }
        return new NullLoader();
    }

}
