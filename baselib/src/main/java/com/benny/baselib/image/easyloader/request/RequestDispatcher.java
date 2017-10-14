package com.benny.baselib.image.easyloader.request;


import android.text.TextUtils;
import com.benny.baselib.image.easyloader.loader.Loader;
import com.benny.baselib.image.easyloader.loader.LoaderManager;
import java.util.concurrent.BlockingQueue;

/**
 * Created by Benny on 2017/10/4.
 *
 * 图片请求转发线程
 */

public class RequestDispatcher extends Thread {

    private BlockingQueue<BitmapRequest> mRequestQueue;

    public RequestDispatcher(BlockingQueue<BitmapRequest> requestQueue) {
        this.mRequestQueue = requestQueue;
    }

    @Override
    public void run() {
        while (!isInterrupted()) {
            try {
                BitmapRequest bitmapRequest = mRequestQueue.take();

                String schema = parseSchema(bitmapRequest.getUrl());

                LoaderManager.getInstance().getLoader(schema)

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private String parseSchema(String url) {
        if (TextUtils.isEmpty(url)) {
            return null;
        }

        if (url.contains("://")) {
            return url.split("://")[0];
        }
        return null;
    }
}
