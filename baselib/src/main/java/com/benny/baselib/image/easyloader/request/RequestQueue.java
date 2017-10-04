package com.benny.baselib.image.easyloader.request;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Benny on 2017/10/4.
 */

public class RequestQueue {

    /**
     * 阻塞队列
     */
    private BlockingQueue<BitmapRequest> mQueue = new PriorityBlockingQueue<>();

    /**
     * 请求转发数量
     */
    private int mThreadCount;

    private RequestDispatcher[] mDispatchers;

    private AtomicInteger mSerialNo = new AtomicInteger(0);

    /**
     * 添加图片请求队列
     *
     * @param request
     */
    public void addRequest(BitmapRequest request) {
        if (!mQueue.contains(request)) {
            //设置请求序列号
            request.setSerialNo(mSerialNo.incrementAndGet());
            mQueue.add(request);
        }
    }


    /**
     * 开启图片请求
     */
    public void start() {

    }

    /**
     * 暂停请求
     */
    public void stop() {

    }

}
