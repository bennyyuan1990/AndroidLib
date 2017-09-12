package com.benny.baselib.http;

import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by yuanbb on 2017/9/12.
 */
class HttpEngine {

    private static HttpEngine mInstance;

    static {
        mInstance = new HttpEngine();
    }

    public static HttpEngine getInstance() {
        return mInstance;
    }

    private PriorityBlockingQueue<Runnable> mTaskQueue = new PriorityBlockingQueue<>();
    private ThreadPoolExecutor mThreadPool;

    private RejectedExecutionHandler mRejectedExecutionHandler = new RejectedExecutionHandler() {
        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            mTaskQueue.add(r);
        }
    };


    private Runnable mExecuteHandler = new Runnable() {
        @Override
        public void run() {

            while (true) {
                try {
                    Runnable take = mTaskQueue.take();
                    take.run();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }


        }
    };


    private HttpEngine() {
        mThreadPool = new ThreadPoolExecutor(5, 10, 1, TimeUnit.SECONDS, mTaskQueue, mRejectedExecutionHandler);
        mThreadPool.execute(mExecuteHandler);
    }


    public void addHttpTask(HttpTask task) {

    }


}
