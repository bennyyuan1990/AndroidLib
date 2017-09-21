//
// Created by yuanbb on 2017/9/21.
//

#include "com_benny_ffmpeg_FFmpegMediaPlayer.h"


#define MAX_AUDIO_FRME_SIZE 48000 * 4

#ifdef __cplusplus
extern "C" {
#endif

#include "libavcodec/avcodec.h"
#include "libavformat/avformat.h"
#include "libswscale/swscale.h"
#include "libavutil/imgutils.h"
#include "libswresample/swresample.h"

#include <android/native_window.h>
#include <android/native_window_jni.h>
#include <pthread.h>
#include <unistd.h>

//Log
#ifdef ANDROID
#include <android/log.h>
#define LOGE(format, ...)  __android_log_print(ANDROID_LOG_ERROR, "ffmpeg", format, ##__VA_ARGS__)
#define LOGD(format, ...)  __android_log_print(ANDROID_LOG_DEBUG, "ffmpeg", format, ##__VA_ARGS__)
#else
#define LOGE(format, ...)  printf("ffmpeg --ERROR: " format "\n", ##__VA_ARGS__)
#define LOGD(format, ...)  printf("ffmpeg --DEBUG: " format "\n", ##__VA_ARGS__)
#endif


struct MediaPlayer {
  JavaVM *javaVM;
  JNIEnv *jniEnv;

  AVFormatContext *in_format_context;
  int audio_stream_index = -1;
  int video_stream_index = -1;
  AVCodecContext *audio_codec_context;
  AVCodecContext *video_codec_context;
  AVCodec *audio_codec;
  AVCodec *video_codec;

  AVPacket *packet = NULL;
  AVFrame *frame = NULL;

  SwrContext *swr_context;

  AVSampleFormat in_sample_format;
  AVSampleFormat out_sample_format;

  int in_sample_rate;
  int out_sample_rate;

  uint64_t in_channel_layout;
  uint64_t out_channel_layout;
  int out_channel_layout_nb;

  uint8_t *out_buffer;


  jobject audio_track_object;
  jmethodID audio_track_write_method_id;
  jmethodID audio_track_stop_method_id;
};

/**
 * 初始化 文件格式 上下文
 * @param fileName 文件路径
 * @param mediaPlayer
 * @return
 */
int init_format_context(const char *fileName, MediaPlayer *mediaPlayer) {

  //注册所有编码解码组件
  av_register_all();

  AVFormatContext *format_ctx = avformat_alloc_context();

  //打开输入文件
  int result = avformat_open_input(&format_ctx, fileName, NULL, NULL);
  if (result < 0) {
    LOGE("无法打开文件:%s\n",fileName);
    return -1;
  }

  //查找获取视频、音频流信息
  result = avformat_find_stream_info(format_ctx, NULL);
  if (result < 0) {
    LOGE("无法打开多媒体文件流信息\n");
    return -1;
  }

  mediaPlayer->in_format_context = format_ctx;
}

/**
 * 根据 编解码上下文  打开解码器
 * @param codecContext
 * @return
 */
AVCodec *openCodec(AVCodecContext *codecContext) {
  //获取解码器
  AVCodec *codec = avcodec_find_decoder(codecContext->codec_id);
  if (codec == NULL) {
    LOGE("Couldn't find Codec.\n");
    return NULL;
  }
  //打开解码器
  if (avcodec_open2(codecContext, codec, NULL) < 0) {
    LOGE("Couldn't open codec.\n");
    return NULL;
  }
  return codec;
}

/**
 * 初始化 音视频 解码器
 * @param mediaPlayer
 * @return
 */
int init_codec(MediaPlayer *mediaPlayer) {

  //查找视频，音频流所在通道序列
  for (int i = 0; i < mediaPlayer->in_format_context->nb_streams; ++i) {
    if (mediaPlayer->in_format_context->streams[i]->codec->codec_type == AVMEDIA_TYPE_AUDIO) {
      mediaPlayer->audio_stream_index = i;
    } else if (mediaPlayer->in_format_context->streams[i]->codec->codec_type == AVMEDIA_TYPE_VIDEO) {
      mediaPlayer->video_stream_index = i;
    }
  }

  if (mediaPlayer->audio_stream_index < 0 && mediaPlayer->video_stream_index < 0)
    return -1;

  //申请编码数据内存空间
  mediaPlayer->packet = (AVPacket *) av_malloc(sizeof(AVPacket));

  //申请解码音频帧数据内存空间
  mediaPlayer->frame = av_frame_alloc();

  if (mediaPlayer->audio_stream_index >= 0) {
    mediaPlayer->audio_codec_context = mediaPlayer->in_format_context->streams[mediaPlayer->audio_stream_index]->codec;
    mediaPlayer->audio_codec = openCodec(mediaPlayer->audio_codec_context);
  }
  if (mediaPlayer->video_stream_index >= 0) {
    mediaPlayer->video_codec_context = mediaPlayer->in_format_context->streams[mediaPlayer->video_stream_index]->codec;
    mediaPlayer->video_codec = openCodec(mediaPlayer->video_codec_context);
  }
  return 0;
}

/**
 * 初始化 音频 重采样参数
 * @param mediaPlayer
 */
void init_audio_sample(MediaPlayer *mediaPlayer) {

  if (mediaPlayer->audio_codec_context == NULL)
    return;

//音频重采样转换上下文   ==>16bit 44100 PCM
  mediaPlayer->swr_context = swr_alloc();

  //重采样参数设置 (将音频数据统一转换为16bit 44100hz的PCM) --------begin

  //格式数据大小
  mediaPlayer->in_sample_format = mediaPlayer->audio_codec_context->sample_fmt;
  mediaPlayer->out_sample_format = AV_SAMPLE_FMT_S16;

  //采样率
  mediaPlayer->in_sample_rate = mediaPlayer->audio_codec_context->sample_rate;
  mediaPlayer->out_sample_rate = 44100;

  //声道
  mediaPlayer->in_channel_layout = mediaPlayer->audio_codec_context->channel_layout;
  //立体声
  mediaPlayer->out_channel_layout = AV_CH_LAYOUT_STEREO;

  swr_alloc_set_opts(mediaPlayer->swr_context, mediaPlayer->out_channel_layout, mediaPlayer->out_sample_format, mediaPlayer->out_sample_rate,
                     mediaPlayer->in_channel_layout, mediaPlayer->in_sample_format, mediaPlayer->in_sample_rate, 0, NULL);
  swr_init(mediaPlayer->swr_context);

  mediaPlayer->out_channel_layout_nb = av_get_channel_layout_nb_channels(mediaPlayer->out_channel_layout);
  //重采样参数设置 ------end


  mediaPlayer->out_buffer = (uint8_t *) av_malloc(MAX_AUDIO_FRME_SIZE);
}

/**
 * 反射调用JAVA代码，初始化AudioTrack实例
 * @param mediaPlayer
 * @param env
 * @param jobj
 */
void init_audio_track(MediaPlayer *mediaPlayer, JNIEnv *env, jobject jobj) {
  jclass jclazz = env->GetObjectClass(jobj);

  //获取AudioTrack对象
  jmethodID createAudioTrackMethodID = env->GetMethodID(jclazz, "createAudioTrack", "(II)Landroid/media/AudioTrack;");
  jobject audioTrackObj = env->CallObjectMethod(jclazz, createAudioTrackMethodID, mediaPlayer->out_sample_rate, mediaPlayer->out_channel_layout_nb);
  mediaPlayer->audio_track_object = env->NewGlobalRef(audioTrackObj);


  //调用AudioTrack.play方法
  jclass audioTrackCls = env->GetObjectClass(audioTrackObj);
  jmethodID audioTrackPlayMethodID = env->GetMethodID(audioTrackCls, "play", "()V");
  //执行播放
  env->CallVoidMethod(audioTrackObj, audioTrackPlayMethodID);

  //AudioTrack.write
  mediaPlayer->audio_track_write_method_id = env->GetMethodID(audioTrackCls, "write", "([BII)I");

  mediaPlayer->audio_track_stop_method_id = env->GetMethodID(audioTrackCls, "stop", "()V");
}


void convert_audio(MediaPlayer *mediaPlayer, JNIEnv *env) {
  if (mediaPlayer->packet->stream_index == mediaPlayer->audio_stream_index) {
    int got_frame = 0;
    //解码音频数据
    avcodec_decode_audio4(mediaPlayer->audio_codec_context,mediaPlayer->frame, &got_frame, mediaPlayer->packet);

    //非0，表示正确解码
    if (got_frame > 0) {
      //音频转码
      swr_convert(mediaPlayer->swr_context, &(mediaPlayer->out_buffer), MAX_AUDIO_FRME_SIZE, (const uint8_t **) mediaPlayer->frame->data, mediaPlayer->frame->nb_samples);
      int outBufferSize = av_samples_get_buffer_size(NULL, mediaPlayer->out_channel_layout_nb, mediaPlayer->frame->nb_samples, mediaPlayer->out_sample_format, 1);

      mediaPlayer->javaVM->AttachCurrentThread(&env,NULL);

      //将outBuffer缓冲区数据转换成byte数组
      jbyteArray audioSampleArray = env->NewByteArray(outBufferSize);
      jbyte *audioSampleArrayElement = env->GetByteArrayElements(audioSampleArray, NULL);
      memcpy(audioSampleArrayElement, mediaPlayer->out_buffer, outBufferSize);
      env->ReleaseByteArrayElements(audioSampleArray, audioSampleArrayElement, 0);

      env->CallIntMethod(mediaPlayer->audio_track_object, mediaPlayer->audio_track_write_method_id, audioSampleArray, 0, outBufferSize);

      env->DeleteLocalRef(audioSampleArray);

      mediaPlayer->javaVM->DetachCurrentThread();
    }
  }
}


void* decode_frame_data(void *player) {
  MediaPlayer *mediaPlayer = (MediaPlayer *) player;
  //读取每帧数据
  while (av_read_frame(mediaPlayer->in_format_context, mediaPlayer->packet) >= 0) {

    convert_audio(mediaPlayer, mediaPlayer->jniEnv);

    av_free_packet(mediaPlayer->packet);
  }

  mediaPlayer->jniEnv->CallVoidMethod(mediaPlayer->audio_track_object, mediaPlayer->audio_track_stop_method_id);

  av_frame_free(&(mediaPlayer->frame));
  av_free(mediaPlayer->out_buffer);

  swr_free(&(mediaPlayer->swr_context));
  if (mediaPlayer->audio_codec_context != NULL)avcodec_close(mediaPlayer->audio_codec_context);
  if (mediaPlayer->video_codec_context != NULL) avcodec_close(mediaPlayer->video_codec_context);
  avformat_close_input(&(mediaPlayer->in_format_context));

  free(mediaPlayer);
}


JNIEXPORT jint JNICALL Java_com_benny_ffmpeg_FFmpegMediaPlayer_player
    (JNIEnv *env, jobject jobj, jstring _fileName, jobject _surface) {

  const char *infileName = env->GetStringUTFChars(_fileName, NULL);

  MediaPlayer *mediaPlayer = (MediaPlayer *) malloc(sizeof(MediaPlayer));

  mediaPlayer->jniEnv = env;
  env->GetJavaVM(&(mediaPlayer->javaVM));


  //初始化 格式 上下文
  int result = init_format_context(infileName, mediaPlayer);
  if (result != 0) return result;

  //初始化 编码
  result = init_codec(mediaPlayer);
  if (result != 0) return result;

  //初始化 编码 重采样
  init_audio_sample(mediaPlayer);

  //初始化 音频 AudioTrack
  init_audio_track(mediaPlayer, env, jobj);


  pthread_t thread;
  //
  pthread_create(&thread,NULL,decode_frame_data,(void *)mediaPlayer);


  (env)->ReleaseStringUTFChars(_fileName, infileName);

  return 0;

}


#ifdef __cplusplus
}
#endif