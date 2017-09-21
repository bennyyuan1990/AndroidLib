package com.benny.ffmpeg;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;

/**
 * Created by yuanbb on 2017/9/21.
 */

public class FFmpegMediaPlayer {

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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public native int player(String fileName,Object surface);


    private AudioTrack createAudioTrack(int sampleRateHz, int nbChannels) {
        int audioFormat = AudioFormat.ENCODING_PCM_16BIT;

        int channelConfig;
        if (nbChannels == 1) {
            channelConfig = AudioFormat.CHANNEL_OUT_MONO;
        } else if (nbChannels == 2) {
            channelConfig = AudioFormat.CHANNEL_OUT_STEREO;
        } else {
            channelConfig = AudioFormat.CHANNEL_OUT_STEREO;
        }

        int minBufferSize = AudioTrack.getMinBufferSize(sampleRateHz, channelConfig, audioFormat);

        AudioTrack audioTrack = new AudioTrack(AudioManager.STREAM_MUSIC, sampleRateHz, channelConfig, audioFormat, minBufferSize, AudioTrack.MODE_STREAM);

        return audioTrack;
    }
}
