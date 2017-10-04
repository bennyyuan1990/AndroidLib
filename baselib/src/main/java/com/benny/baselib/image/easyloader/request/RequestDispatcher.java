package com.benny.baselib.image.easyloader.request;

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
         while (!isInterrupted()){
             try {
                 BitmapRequest bitmapRequest = mRequestQueue.take();
             }catch (Exception e){
                 e.printStackTrace();
             }
         }
    }
}
