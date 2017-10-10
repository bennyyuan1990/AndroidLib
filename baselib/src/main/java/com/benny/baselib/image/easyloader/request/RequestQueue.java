package com.benny.baselib.image.easyloader.request;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 图片加载请求队列
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

    public RequestQueue(int threadCount) {
        this.mThreadCount = threadCount;
    }

    /**
     * 添加图片请求队列
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

        stop();
        startDispatchers();
    }

    /**
     * 开启转发器
     */
    private void startDispatchers() {
        mDispatchers = new RequestDispatcher[mThreadCount];
        for (int i = 0; i < mThreadCount; i++) {
            RequestDispatcher q = new RequestDispatcher(mQueue);
            mDispatchers[i] = q;
            mDispatchers[i].start();
        }
    }

    /**
     * 暂停请求
     */
    public void stop() {
        if (mDispatchers == null || mDispatchers.length == 0) {
            return;
        }

        for (int i = 0, count = mDispatchers.length; i < count; i++) {
            try {
                RequestDispatcher q = mDispatchers[i];
                if (q != null && q.isAlive()) {
                    q.stop();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            mDispatchers[i] = null;
        }
        mDispatchers = null;
    }

}
