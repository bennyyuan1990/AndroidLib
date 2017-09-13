package com.benny.ffmpeg;

/**
 * Created by yuanbb on 2017/9/13.
 */

public class FFmpegInfo {

    static {
        System.loadLibrary("avutil");
        System.loadLibrary("avcodec");
        System.loadLibrary("avfilter");
        System.loadLibrary("avformat");
        System.loadLibrary("swscale");
        System.loadLibrary("swresample");
        System.loadLibrary("avresample");
        System.loadLibrary("ffmpeg");
    }


    /**
     * FFmpeg类库支持的协议
     */
    public static native String getProtocol();

    /**
     * FFmpeg类库支持的封装格式
     */
    public static native String getAVFormat();

    /**
     * FFmpeg类库支持的编解码器
     */
    public static native String getAVCodec();

    /**
     * FFmpeg类库支持的滤镜
     */
    public static native String getAVFilter();

    /**
     * FFmpeg类库的配置信息
     */
    public static native String getConfigure();


}
