package com.benny.baselib.http;

import android.support.annotation.NonNull;
import com.benny.baselib.http.core.HttpMethod;
import com.benny.baselib.http.request.IHttpRequest;
import com.benny.baselib.http.response.IHttpResponse;
import java.io.BufferedInputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by yuanbb on 2017/9/12.
 */

public class HttpTask implements Runnable {

    private IHttpRequest mRequest;
    private IHttpResponse mResponse;

    public HttpTask(@NonNull IHttpRequest request, @NonNull IHttpResponse response) {
        mRequest = request;
        mResponse = response;
    }

    @Override
    public void run() {

        try {
            URL url = new URL(mRequest.getUrl());
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            try {
                urlConnection.setRequestMethod(HttpMethod.toString(mRequest.getHttpMethod()));
                urlConnection.setDoOutput(true);
                urlConnection.setConnectTimeout(HttpConfig.ConnectTimeout);
                urlConnection.setReadTimeout(HttpConfig.ReadTimeout);
              //  urlConnection.setDefaultUseCaches();

                BufferedInputStream in = new BufferedInputStream(urlConnection.getInputStream());

            } finally {
                urlConnection.disconnect();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
