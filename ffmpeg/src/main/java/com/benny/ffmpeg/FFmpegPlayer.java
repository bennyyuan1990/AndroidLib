package com.benny.ffmpeg;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;

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


    public static native int playAudio(String fileName);

    /**
     * 构建AudioTrack，支持数据流方式播放声音
     * @param sampleRateHz
     * @param nbChannels
     * @return
     */
    public static AudioTrack createAudioTrack(int sampleRateHz,int nbChannels){
        int audioFormat = AudioFormat.ENCODING_PCM_16BIT;

        int channelConfig ;
        if(nbChannels == 1){
            channelConfig = AudioFormat.CHANNEL_OUT_MONO;
        }else if(nbChannels ==2){
            channelConfig = AudioFormat.CHANNEL_OUT_STEREO;
        }else {
            channelConfig = AudioFormat.CHANNEL_OUT_STEREO;
        }

        int minBufferSize = AudioTrack.getMinBufferSize(sampleRateHz, channelConfig, audioFormat);

        AudioTrack audioTrack = new AudioTrack(AudioManager.STREAM_MUSIC,sampleRateHz,channelConfig,audioFormat,minBufferSize,AudioTrack.MODE_STREAM);

        return audioTrack;
    }
}
