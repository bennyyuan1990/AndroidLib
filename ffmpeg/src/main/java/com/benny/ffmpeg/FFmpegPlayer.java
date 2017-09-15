package com.benny.ffmpeg;

import android.view.Surface;

/**
 * Created by yuanbb on 2017/9/15.
 */

public class FFmpegPlayer {
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


    public static native int play(String fileName,Surface surface);
}
