package com.benny.baselib.http.response;

import android.support.annotation.NonNull;

import com.benny.baselib.http.interfaces.IHttpListener;

/**
 * Created by yuanbb on 2017/9/12.
 */

public class BaseHttpResponse implements IHttpResponse {

    @NonNull
    @Override
    public IHttpListener getHttpListener() {
        return null;
    }
}
