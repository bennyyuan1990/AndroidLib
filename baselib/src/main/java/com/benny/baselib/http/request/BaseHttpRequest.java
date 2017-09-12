package com.benny.baselib.http.request;

import android.support.annotation.NonNull;
import com.benny.baselib.http.core.HttpMethod;

/**
 * Created by yuanbb on 2017/9/12.
 */

public abstract class BaseHttpRequest implements IHttpRequest {


    @Override
    public int getHttpMethod() {
        return HttpMethod.GET;
    }
}
