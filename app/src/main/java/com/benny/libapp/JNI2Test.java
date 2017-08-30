package com.benny.libapp;

/**
 * Created by yuanbb on 2017/8/30.
 */

public class JNI2Test {
    static {
        System.loadLibrary("ndkTest");
    }

    public native String getString(String content);

}
