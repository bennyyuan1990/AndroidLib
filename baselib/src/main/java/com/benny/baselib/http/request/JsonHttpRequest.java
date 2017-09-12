package com.benny.baselib.http.request;

/**
 * Created by yuanbb on 2017/9/12.
 */

public class JsonHttpRequest extends TextHttpRequest {


    @Override
    public String getContentType() {
        return "text/json";
    }

    public void setBody(Object obj){
        //TODO:JSON序列化

    }
}
