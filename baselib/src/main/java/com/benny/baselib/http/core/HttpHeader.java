package com.benny.baselib.http.core;

import android.support.v4.util.ArrayMap;

/**
 * Created by yuanbb on 2017/9/12.
 */

public class HttpHeader {

    public static final String ACCEPT = "Accept";
    public static final String ACCEPT_CHARSET = "Accept-Charset";
    public static final String ACCEPT_ENCODING = "Accept-Encoding";
    public static final String ACCEPT_LANGUAGE = "Accept-Language";
    public static final String AUTHORIZATION = "Authorization";
    public static final String USER_AGENT = "User-Agent";
    public static final String CHARSET="Charset";
    public static final String CONNECTION = "Connection";
    public static final String CONTENT_TYPE="Content-Type";


    private static ArrayMap<String,String> mDefaultHeaders;

    static {
        mDefaultHeaders = new ArrayMap<>();
        mDefaultHeaders.put(CHARSET,"UTF-8");
        mDefaultHeaders.put(CONNECTION,"Keep-Alive");
    }


    public static ArrayMap<String, String> getDefaultHeaders() {
        return mDefaultHeaders;
    }
}
