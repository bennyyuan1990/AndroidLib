package com.benny.ffmpeg;

/**
 * Created by yuanbb on 2017/9/15.
 */

public class FFmpegPlayer {
    static {
        try {
            System.loadLibrary("avutil");
            System.loadLibrary("swresample");
            System.loadLibrary("avcodec");
            System.loadLibrary("avformat");
            System.loadLibrary("swscale");
            //System.loadLibrary("avresample");
            System.loadLibrary("avfilter");
            System.loadLibrary("ffmpeg");
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public static native int play(String fileName,Object surface);
}
