#include <stdio.h>
#include <unistd.h>
#include "com_benny_ffmpeg_FFmpegPlayer.h"

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

//Log
#ifdef ANDROID
#include <android/log.h>
#define LOGE(format, ...)  __android_log_print(ANDROID_LOG_ERROR, "ffmpeg", format, ##__VA_ARGS__)
#define LOGD(format, ...)  __android_log_print(ANDROID_LOG_DEBUG, "ffmpeg", format, ##__VA_ARGS__)
#else
#define LOGE(format, ...)  printf("ffmpeg --ERROR: " format "\n", ##__VA_ARGS__)
#define LOGD(format, ...)  printf("ffmpeg --DEBUG: " format "\n", ##__VA_ARGS__)
#endif


JNIEXPORT jint JNICALL
Java_com_benny_ffmpeg_FFmpegPlayer_render(JNIEnv *env, jclass jclazz, jstring fileName_,
                                        jobject surface) {
  const char *fileName = env->GetStringUTFChars(fileName_, NULL);

  AVFormatContext *inFormatContext = NULL;

  //注册所有编码解码组件
  av_register_all();


  //打开输入文件
  int result = avformat_open_input(&inFormatContext, fileName, NULL, NULL);
  if (result < 0) {
    LOGE("Cannot open input file\n");
    return env->ThrowNew(jclazz, "无法打开输入文件");
  }

  //查找获取视频、音频流信息
  result = avformat_find_stream_info(inFormatContext, NULL);
  if (result < 0) {
    LOGE("Cannot find stream information\n");
    return env->ThrowNew(jclazz, "无法找到文件流信息");
  }

  //查找视频流所在通道序列
  int videoStreamIndex = -1;
  for (int i = 0; i < inFormatContext->nb_streams; ++i) {
    if (inFormatContext->streams[i]->codec->codec_type == AVMEDIA_TYPE_VIDEO) {
      videoStreamIndex = i;
    }
  }

  if (videoStreamIndex == -1) {
    LOGE("Couldn't find a video stream.\n");
    return -1;
  }

  //编码上下文
  AVCodecContext *codecContext = inFormatContext->streams[videoStreamIndex]->codec;
  //获取视频解码器
  AVCodec *codec = avcodec_find_decoder(codecContext->codec_id);
  if (codec == NULL) {
    LOGE("Couldn't find Codec.\n");
    return -1;
  }
  //打开解码器
  result = avcodec_open2(codecContext, codec, NULL);
  if (result < 0) {
    LOGE("Couldn't open codec.\n");
    return -1;
  }

  //获取界面传下来的surface
  ANativeWindow *nativeWindow = ANativeWindow_fromSurface(env, surface);
  if (0 == nativeWindow) {
    LOGD("Couldn't get native window from surface.\n");
    return -1;
  }

  ANativeWindow_Buffer windowBuffer;

  //申请编码数据内存空间
  AVPacket *packet = (AVPacket *) av_malloc(sizeof(AVPacket));

  //像素数据（解码数据）
  AVFrame *inFrame = av_frame_alloc();
  AVFrame *rgbaFrame = av_frame_alloc();

  //申请输出YUV每帧数据缓存内存
  uint8_t *out_buffer = (uint8_t *) av_malloc(
      avpicture_get_size(AV_PIX_FMT_RGBA, codecContext->width, codecContext->height));
  //初始化缓冲区
  avpicture_fill((AVPicture *) rgbaFrame, out_buffer, AV_PIX_FMT_RGBA, codecContext->width,
                 codecContext->height);

  //视频格式转换上下文
  struct SwsContext *sws_ctx = sws_getContext(
      codecContext->width, codecContext->height, codecContext->pix_fmt,
      codecContext->width, codecContext->height, AV_PIX_FMT_RGBA,
      SWS_BICUBIC, NULL, NULL, NULL);


  if (0 >
      ANativeWindow_setBuffersGeometry(nativeWindow, codecContext->width, codecContext->height,
                                       WINDOW_FORMAT_RGBA_8888)) {
    LOGD("Couldn't set buffers geometry.\n");
    ANativeWindow_release(nativeWindow);
    return -1;
  }


  int len, got_frame;
  //逐帧读取视频数据
  while (av_read_frame(inFormatContext, packet) >= 0) {

    //解码视频数据
    len = avcodec_decode_video2(codecContext, inFrame, &got_frame, packet);

    //非0，表示正确解码
    if (got_frame) {

      //执行转码 --->YUV
      sws_scale(sws_ctx, (const uint8_t *const *) inFrame->data, inFrame->linesize, 0,
                inFrame->height,
                rgbaFrame->data, rgbaFrame->linesize);

      if (ANativeWindow_lock(nativeWindow, &windowBuffer, NULL) < 0) {
        LOGD("cannot lock window");
      } else {
        //将图像绘制到界面上，注意这里pFrameRGBA一行的像素和windowBuffer一行的像素长度可能不一致
        //需要转换好，否则可能花屏
        uint8_t *dst = (uint8_t *) windowBuffer.bits;
        for (int h = 0; h < codecContext->height; h++) {
          memcpy(dst + h * windowBuffer.stride * 4,
                 out_buffer + h * rgbaFrame->linesize[0],
                 rgbaFrame->linesize[0]);
        }
        ANativeWindow_unlockAndPost(nativeWindow);
        usleep(1000 * 16);
      }

    }

    av_free_packet(packet);

  }


  av_frame_free(&inFrame);
  av_frame_free(&rgbaFrame);
  avcodec_close(codecContext);
  avformat_close_input(&inFormatContext);

/**/
  env->ReleaseStringUTFChars(fileName_, fileName);

  return 0;
}




//播放音频
JNIEXPORT jint JNICALL Java_com_benny_ffmpeg_FFmpegPlayer_audio
    (JNIEnv *env, jclass jclazz, jstring _fileName) {


  const char *infileName = env->GetStringUTFChars(_fileName, NULL);

  AVFormatContext *inFormatContext = NULL;

  //注册所有编码解码组件
  av_register_all();


  //打开输入文件
  int result = avformat_open_input(&inFormatContext, infileName, NULL, NULL);
  if (result < 0) {
    LOGE("Cannot open input file\n");
    return env->ThrowNew(jclazz, "无法打开输入文件");
  }

  //查找获取视频、音频流信息
  result = avformat_find_stream_info(inFormatContext, NULL);
  if (result < 0) {
    LOGE("Cannot find stream information\n");
    return env->ThrowNew(jclazz, "无法找到文件流信息");
  }


  //查找音频流所在通道序列
  int audioStreamIndex = -1;
  for (int i = 0; i < inFormatContext->nb_streams; ++i) {
    if (inFormatContext->streams[i]->codec->codec_type == AVMEDIA_TYPE_AUDIO) {
      audioStreamIndex = i;
      break;
    }
  }

  if (audioStreamIndex == -1) {
    LOGE("Couldn't find a audio stream.\n");
    return -1;
  }

  //编码上下文
  AVCodecContext *codecContext = inFormatContext->streams[audioStreamIndex]->codec;
  //获取音频解码器
  AVCodec *codec = avcodec_find_decoder(codecContext->codec_id);
  if (codec == NULL) {
    LOGE("Couldn't find Codec.\n");
    return -1;
  }
  //打开解码器
  result = avcodec_open2(codecContext, codec, NULL);
  if (result < 0) {
    LOGE("Couldn't open codec.\n");
    return -1;
  }


  //申请编码数据内存空间
  AVPacket *packet = (AVPacket *) av_malloc(sizeof(AVPacket));

  //申请解码音频帧数据内存空间
  AVFrame *frame = av_frame_alloc();

  //音频重采样转换上下文   ==>16bit 44100 PCM
  SwrContext *swrContext = swr_alloc();

  //重采样参数设置 (将音频数据统一转换为16bit 44100hz的PCM) --------begin

  //格式数据大小
  AVSampleFormat inSampleFmt = codecContext->sample_fmt;
  AVSampleFormat outSampleFmt = AV_SAMPLE_FMT_S16;

  //采样率
  int inSampleRate = codecContext->sample_rate;
  int outSampleRate = 44100;

  //声道
  uint64_t inChannelLayout = codecContext->channel_layout;
  //立体声
  uint64_t outChannelLayout = AV_CH_LAYOUT_STEREO;

  swr_alloc_set_opts(swrContext, outChannelLayout, outSampleFmt, outSampleRate, inChannelLayout, inSampleFmt, inSampleRate, 0, NULL);
  swr_init(swrContext);

  int outChannelNb = av_get_channel_layout_nb_channels(outChannelLayout);
  //重采样参数设置 ------end

  //获取AudioTrack对象
  jmethodID createAudioTrackMethodID = env->GetStaticMethodID(jclazz, "createAudioTrack", "(II)Landroid/media/AudioTrack;");
  jobject audioTrackObj = env->CallStaticObjectMethod(jclazz, createAudioTrackMethodID, outSampleRate, outChannelNb);


  //调用AudioTrack.play方法
  jclass audioTrackCls = env->GetObjectClass(audioTrackObj);
  jmethodID audioTrackPlayMethodID = env->GetMethodID(audioTrackCls, "play", "()V");
  jmethodID audioTrackStopMethodID = env->GetMethodID(audioTrackCls, "stop", "()V");
  //执行播放
  env->CallVoidMethod(audioTrackObj, audioTrackPlayMethodID);

  //AudioTrack.write
  jmethodID audioTrackWriteMethodID = env->GetMethodID(audioTrackCls, "write", "([BII)I");


  uint8_t *outBuffer = (uint8_t *) av_malloc(MAX_AUDIO_FRME_SIZE);

  int got_frame = 0, ret;

  //读取每帧数据
  while (av_read_frame(inFormatContext, packet) >= 0) {
    if (packet->stream_index == audioStreamIndex) {

      //解码音频数据
      ret = avcodec_decode_audio4(codecContext, frame, &got_frame, packet);

      //非0，表示正确解码
      if (got_frame > 0) {
        //音频转码
        swr_convert(swrContext, &outBuffer, MAX_AUDIO_FRME_SIZE, (const uint8_t **) frame->data, frame->nb_samples);
        int outBufferSize = av_samples_get_buffer_size(NULL, outChannelNb, frame->nb_samples, outSampleFmt, 1);

        //将outBuffer缓冲区数据转换成byte数组
        jbyteArray  audioSampleArray = env->NewByteArray(outBufferSize);
        jbyte *audioSampleArrayElement = env->GetByteArrayElements(audioSampleArray,NULL);
        memcpy(audioSampleArrayElement,outBuffer,outBufferSize);
        env->ReleaseByteArrayElements(audioSampleArray,audioSampleArrayElement,0);

        env->CallIntMethod(audioTrackObj,audioTrackWriteMethodID,audioSampleArray,0,outBufferSize);

        env->DeleteLocalRef(audioSampleArray);
      }
    }

    av_free_packet(packet);

  }

  env->CallVoidMethod(audioTrackObj, audioTrackStopMethodID);

  av_frame_free(&frame);
  av_free(outBuffer);

  swr_free(&swrContext);
  avcodec_close(codecContext);
  avformat_close_input(&inFormatContext);

  (env)->ReleaseStringUTFChars(_fileName, infileName);

  return 0;
}


#ifdef __cplusplus
}
#endif