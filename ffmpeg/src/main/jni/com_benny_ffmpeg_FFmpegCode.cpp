//
// Created by yuanbb on 2017/9/15.
//


#include "com_benny_ffmpeg_FFmpegCode.h"

#ifdef __cplusplus
extern "C" {
#endif
#include <stdio.h>

#include "libavcodec/avcodec.h"
#include "libavformat/avformat.h"
#include "libswscale/swscale.h"

//Log
#ifdef ANDROID
#include <android/log.h>
#define LOGE(format, ...)  __android_log_print(ANDROID_LOG_ERROR, "ffmpeg", format, ##__VA_ARGS__)
#define LOGD(format, ...)  __android_log_print(ANDROID_LOG_DEBUG, "ffmpeg", format, ##__VA_ARGS__)
#else
#define LOGE(format, ...)  printf("ffmpeg --ERROR: " format "\n", ##__VA_ARGS__)
#define LOGD(format, ...)  printf("ffmpeg --DEBUG: " format "\n", ##__VA_ARGS__)
#endif


JNIEXPORT jint JNICALL Java_com_benny_ffmpeg_FFmpegCode_Decode2YUV
    (JNIEnv *env, jclass jclazz, jstring inFileName, jstring outFileName) {

  const char *infileName = env->GetStringUTFChars(inFileName, NULL);
  const char *outfileName = env->GetStringUTFChars(outFileName, NULL);

  AVFormatContext *inFormatContext = NULL;

  //注册所有编码解码组件
  av_register_all();

  //av_init_packet(&avpkt);

  //打开输入文件
  int result = avformat_open_input(&inFormatContext, infileName, NULL, NULL);
  if (result < 0) {
    LOGE("Cannot open input file\n");
    return env->ThrowNew(jclazz, "无法打开输入文件");
  }

  //查找获取视频、音频流信息
  result = avformat_find_stream_info(inFormatContext, NULL);
  if (result < 0) {
    av_log(NULL, AV_LOG_ERROR, "Cannot find stream information\n");
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

  //申请编码数据内存空间
  AVPacket *packet = (AVPacket *) av_malloc(sizeof(AVPacket));

  //像素数据（解码数据）
  AVFrame *inFrame = av_frame_alloc();
  AVFrame *yuvFrame = av_frame_alloc();

  //申请输出YUV每帧数据缓存内存
  uint8_t *out_buffer = (uint8_t *) av_malloc(avpicture_get_size(AV_PIX_FMT_YUV420P, codecContext->width, codecContext->height));
 //初始化缓冲区
  avpicture_fill((AVPicture *) yuvFrame, out_buffer, AV_PIX_FMT_YUV420P, codecContext->width, codecContext->height);


  //YUV输出文件
  FILE* fp_yuv = fopen(outfileName,"wb");


  //视频格式转换上下文
  struct SwsContext *sws_ctx = sws_getContext(
      codecContext->width, codecContext->height, codecContext->pix_fmt,
      codecContext->width, codecContext->height, AV_PIX_FMT_YUV420P,
      SWS_BILINEAR, NULL, NULL, NULL);


  int len,got_frame;
  //逐帧读取视频数据
  while(av_read_frame(inFormatContext,packet) >= 0){

    //解码视频数据
    len = avcodec_decode_video2(codecContext, inFrame, &got_frame, packet);

    //非0，表示正确解码
    if(got_frame){

      //执行转码 --->YUV
      sws_scale(sws_ctx, (const uint8_t *const *) inFrame->data, inFrame->linesize, 0, inFrame->height,
                yuvFrame->data, yuvFrame->linesize);
      int y_size = codecContext->width * codecContext->height;
      fwrite(yuvFrame->data[0], 1, y_size, fp_yuv);
      fwrite(yuvFrame->data[1], 1, y_size/4, fp_yuv);
      fwrite(yuvFrame->data[2], 1, y_size/4, fp_yuv);
    }

    av_free_packet(packet);

  }


  fclose(fp_yuv);

  av_frame_free(&inFrame);
  av_frame_free(&yuvFrame);
  avcodec_close(codecContext);
  avformat_free_context(inFormatContext);

  (env)->ReleaseStringUTFChars(inFileName, infileName);
  (env)->ReleaseStringUTFChars(outFileName, outfileName);

  return 0;
}


#ifdef __cplusplus
}
#endif
