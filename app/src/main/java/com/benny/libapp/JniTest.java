package com.benny.libapp;

/**
 * Created by yuanbb on 2017/8/29.
 */

public class JniTest {

    static {
        System.loadLibrary("ndkTest");
    }

    public native String getText();


}
