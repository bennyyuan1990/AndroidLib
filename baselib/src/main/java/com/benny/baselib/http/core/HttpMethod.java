package com.benny.baselib.http.core;

import android.support.annotation.IntDef;

/**
 * Created by yuanbb on 2017/9/12.
 */

public class HttpMethod {

    public static final int GET = 0;
    public static final int POST = 1;
    public static final int PUT = 2;
    public static final int DELETE = 3;
    public static final int PATCH = 4;
    public static final int HEAD = 5;
    public static final int OPTIONS = 6;
    public static final int TRACE = 7;


    @IntDef({GET, POST, PUT, DELETE, PATCH, HEAD, OPTIONS, TRACE})
    public @interface MethodType {

    }


    public static String toString(@MethodType int method) {
        switch (method) {
            case GET:
                return "GET";
            case POST:
                return "POST";
            case PUT:
                return "PUT";
            case DELETE:
                return "DELETE";
            case PATCH:
                return "PATCH";
            case HEAD:
                return "HEAD";
            case OPTIONS:
                return "OPTIONS";
            case TRACE:
                return "TRACE";
            default:
                return "GET";

        }

    }
}
