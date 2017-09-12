package com.benny.baselib.http.interfaces;

/**
 * Created by yuanbb on 2017/9/11.
 */

public interface IHttpListener<T> {


    void onSucess();

    void onFail(int httpCode, String message);
}
