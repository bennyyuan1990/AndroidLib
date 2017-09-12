package com.benny.baselib.http.response;

import android.support.annotation.NonNull;
import com.benny.baselib.http.interfaces.IHttpListener;

/**
 * Created by yuanbb on 2017/9/12.
 */

public interface IHttpResponse {


    @NonNull
    IHttpListener getHttpListener();
}
