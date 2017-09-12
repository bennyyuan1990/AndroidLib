package com.benny.baselib.http.request;

import android.support.annotation.NonNull;
import android.support.v4.util.ArrayMap;
import android.util.SparseArray;
import com.benny.baselib.http.core.HttpMethod.MethodType;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;

/**
 * Created by yuanbb on 2017/9/12.
 */

public interface IHttpRequest {

    @NonNull
    String getUrl();

    @MethodType
    int getHttpMethod();

    String getContentType();


    ArrayMap<String,String> getHttpHeader();

    void addHttpHeader(String key,String value);

    ArrayMap<String,Object> getQueryParams();

    void addQueryParam(String key,String value);
    void addQueryParam(String key,String[] value);


    void writeOutputStream(OutputStream outputStream) throws IOException;

    HttpURLConnection getHttpURLConnection();



}
