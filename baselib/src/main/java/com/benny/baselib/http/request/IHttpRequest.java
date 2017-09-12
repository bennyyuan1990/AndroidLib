package com.benny.baselib.http.request;

import android.support.annotation.NonNull;
import com.benny.baselib.http.core.HttpMethod.MethodType;

/**
 * Created by yuanbb on 2017/9/12.
 */

public interface IHttpRequest {

    @NonNull
    String getUrl();

    @MethodType
    int getHttpMethod();





}
