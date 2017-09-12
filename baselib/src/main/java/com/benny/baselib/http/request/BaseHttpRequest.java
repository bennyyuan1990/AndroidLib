package com.benny.baselib.http.request;

import android.support.v4.util.ArrayMap;
import android.text.TextUtils;
import com.benny.baselib.http.HttpConfig;
import com.benny.baselib.http.core.HttpHeader;
import com.benny.baselib.http.core.HttpMethod;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;

/**
 * Created by yuanbb on 2017/9/12.
 */

public abstract class BaseHttpRequest implements IHttpRequest {

    private ArrayMap<String, Object> mQueryParam = new ArrayMap<>();
    private ArrayMap<String, String> mHttpHeader = new ArrayMap<>();

    @Override
    public int getHttpMethod() {
        return HttpMethod.GET;
    }

    @Override
    public ArrayMap<String, String> getHttpHeader() {
        return mHttpHeader;
    }


    @Override
    public ArrayMap<String, Object> getQueryParams() {
        return mQueryParam;
    }


    @Override
    public void addHttpHeader(String key, String value) {
        mHttpHeader.put(key, value);
    }

    @Override
    public void addQueryParam(String key, String value) {
        mQueryParam.put(key, value);
    }

    @Override
    public void addQueryParam(String key, String[] value) {
        mQueryParam.put(key, value);
    }

    @Override
    public HttpURLConnection getHttpURLConnection() {
        if (TextUtils.isEmpty(getUrl())) {
            throw new NullPointerException("URL 不能为空");
        }

        StringBuffer urlString = new StringBuffer(getUrl());
        if (getQueryParams() != null && getQueryParams().size() > 0) {
            if (urlString.indexOf("?") < 0) {
                urlString.append("?");
                urlString.append(queryParam2String(getQueryParams()));
            }
        }

        try {
            URL url = new URL(urlString.toString());
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            urlConnection.setRequestMethod(HttpMethod.toString(getHttpMethod()));
            urlConnection.setDoOutput(true);
            urlConnection.setDoInput(true);
            urlConnection.setConnectTimeout(HttpConfig.ConnectTimeout);
            urlConnection.setReadTimeout(HttpConfig.ReadTimeout);

            //设置请求头
            if (HttpMethod.GET != getHttpMethod()) {
                urlConnection.setRequestProperty(HttpHeader.CONTENT_TYPE, getContentType());
            }

            setRequestHeader(urlConnection, HttpHeader.getDefaultHeaders());
            setRequestHeader(urlConnection, getHttpHeader());

            //设置请求体
            writeOutputStream(urlConnection.getOutputStream());

            return urlConnection;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


    protected static String queryParam2String(ArrayMap<String, Object> params) {
        Iterator<String> iterator = params.keySet().iterator();
        StringBuffer result = new StringBuffer();
        String key;
        while (iterator.hasNext()) {
            key = iterator.next();
            Object value = params.get(key);
            if (value instanceof String[]) {
                String[] temp = (String[]) value;
                for (int i = 0, count = temp.length; i < count; i++) {
                    result.append("&" + key + "=" + temp[i]);
                }
            } else {
                result.append("&" + key + "=" + value);
            }
        }
        if (result.length() > 0) {
            return result.toString().substring(1);
        } else {
            return "";
        }
    }

    protected static void setRequestHeader(HttpURLConnection urlConnection, ArrayMap<String, String> headers) {
        if (headers == null || headers.size() == 0) {
            return;
        }
        Iterator<String> iterator = headers.keySet().iterator();
        String key;
        while (iterator.hasNext()) {
            key = iterator.next();
            urlConnection.setRequestProperty(key, headers.get(key));
        }
    }


}
