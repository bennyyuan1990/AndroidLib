package com.benny.baselib.http;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import com.benny.baselib.http.request.IHttpRequest;
import com.benny.baselib.http.response.IHttpResponse;
import java.io.IOException;
import java.net.HttpURLConnection;

/**
 * Created by yuanbb on 2017/9/12.
 */

public class HttpTask implements Runnable {

    private IHttpRequest mRequest;
    private IHttpResponse mResponse;
    private static Handler mHandler;

    public HttpTask(@NonNull IHttpRequest request, @NonNull IHttpResponse response) {
        mRequest = request;
        mResponse = response;
        if (mHandler != null) {
            mHandler = new Handler(Looper.getMainLooper());
        }
    }

    @Override
    public void run() {

        try {
            final HttpURLConnection httpURLConnection = mRequest.getHttpURLConnection();
            if (httpURLConnection != null) {
                httpURLConnection.connect();
                if (httpURLConnection.getResponseCode() != HttpURLConnection.HTTP_OK) {

                } else {
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            if (mResponse.getHttpListener() != null) {
                                try {
                                    mResponse.getHttpListener().onFail(httpURLConnection.getResponseCode(), httpURLConnection.getResponseMessage());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    });
                }
            }

            httpURLConnection.disconnect();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
