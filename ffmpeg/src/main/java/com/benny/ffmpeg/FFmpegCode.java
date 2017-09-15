package com.benny.ffmpeg;

/**
 * Created by yuanbb on 2017/9/15.
 */

public class FFmpegCode {

    static {
        System.loadLibrary("avutil");
        System.loadLibrary("swresample");
        System.loadLibrary("avcodec");
        System.loadLibrary("avformat");
        System.loadLibrary("swscale");
        //System.loadLibrary("avresample");
        System.loadLibrary("avfilter");
        System.loadLibrary("ffmpeg");
    }

    public static native int Decode2YUV(String inFileName,String outFileName);

}
