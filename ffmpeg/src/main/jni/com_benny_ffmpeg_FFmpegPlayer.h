//
// Created by yuanbb on 2017/9/15.
//
#include <jni.h>
#ifndef _ANDROIDLIB_COM_BENNY_FFMPEG_FFMPEGPLAYER_H
#define _ANDROIDLIB_COM_BENNY_FFMPEG_FFMPEGPLAYER_H


#ifdef __cplusplus
extern "C" {
#endif
JNIEXPORT jint JNICALL Java_com_benny_ffmpeg_FFmpegPlayer_play
    (JNIEnv *, jclass, jstring, jobject );

#ifdef __cplusplus
}
#endif
#endif //ANDROIDLIB_COM_BENNY_FFMPEG_FFMPEGPLAYER_H


