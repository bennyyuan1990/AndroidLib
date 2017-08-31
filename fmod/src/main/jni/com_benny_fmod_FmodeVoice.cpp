//
// Created by yuanbb on 2017/8/31.
//

#include "inc/fmod.hpp"
#include "common.h"
#include "com_benny_fmod_FmodVoice.h"

#include <unistd.h>
#include <android/log.h>
#define LOGI(FORMAT, ...) __android_log_print(ANDROID_LOG_INFO,"fmode",FORMAT,##__VA_ARGS__);
#define LOGE(FORMAT, ...) __android_log_print(ANDROID_LOG_ERROR,"fmode",FORMAT,##__VA_ARGS__);


using namespace FMOD;

typedef enum {
  FMOD_VOICE_TYPE_NORMAL = 0,
  FMOD_VOICE_TYPE_LUOLI,
  FMOD_VOICE_TYPE_JINGSONG,
  FMOD_VOICE_TYPE_DASHU,
  FMOD_VOICE_TYPE_GAOGUAI,
  FMOD_VOICE_TYPE_KONGLING,
} VOICE_TYPE;


bool FMOD_CLOSE = false;



JNIEXPORT void JNICALL Java_com_benny_fmod_FmodVoice_PlayVoice
    (JNIEnv *env, jclass jclazz, jstring jfileName, jint jtype) {
  FMOD_CLOSE = false;
  const char *fileName = env->GetStringUTFChars(jfileName, NULL);

  System *system = NULL;
  Sound *sound = NULL;
  Channel *channel = NULL;
  DSP *dsp;
  float frequency = 0;

  try {
    FMOD_RESULT result;

    bool isPalying = true;

    //初始化fmode
    System_Create(&system);
    system->init(32, FMOD_INIT_NORMAL, 0);

    //创建声音
    system->createSound(fileName, FMOD_DEFAULT, NULL, &sound);

    switch (jtype) {
      case FMOD_VOICE_TYPE_NORMAL:
        system->playSound(sound, NULL, false, &channel);
        LOGI("palying:%s", fileName);
        break;

      case FMOD_VOICE_TYPE_LUOLI:
        //萝莉
        //DSP digital signal process
        //dsp -> 音效 创建fmod中预定义好的音效
        //FMOD_DSP_TYPE_PITCHSHIFT dsp，提升或者降低音调用的一种音效
        system->createDSPByType(FMOD_DSP_TYPE_PITCHSHIFT,&dsp);
        //设置音调的参数
        dsp->setParameterFloat(FMOD_DSP_PITCHSHIFT_PITCH,2.5);

        system->playSound(sound, 0, false, &channel);
        //添加到channel
        channel->addDSP(0,dsp);
        LOGI("%s","fix luoli");
        break;

      case FMOD_VOICE_TYPE_JINGSONG:
        //惊悚
        system->createDSPByType(FMOD_DSP_TYPE_TREMOLO,&dsp);
        dsp->setParameterFloat(FMOD_DSP_TREMOLO_SKEW, 0.5);
        system->playSound(sound, 0, false, &channel);
        channel->addDSP(0,dsp);

        break;
      case FMOD_VOICE_TYPE_DASHU:
        //大叔
        system->createDSPByType(FMOD_DSP_TYPE_PITCHSHIFT,&dsp);
        dsp->setParameterFloat(FMOD_DSP_PITCHSHIFT_PITCH,0.8);

        system->playSound(sound, 0, false, &channel);
        //添加到channel
        channel->addDSP(0,dsp);
        LOGI("%s","fix dashu");
        break;
      case FMOD_VOICE_TYPE_GAOGUAI:
        //搞怪
        //提高说话的速度
        system->playSound(sound, 0, false, &channel);
        channel->getFrequency(&frequency);
        frequency = frequency * 1.6;
        channel->setFrequency(frequency);
        LOGI("%s","fix gaoguai");
        break;
      case FMOD_VOICE_TYPE_KONGLING:
        //空灵
        system->createDSPByType(FMOD_DSP_TYPE_ECHO,&dsp);
        dsp->setParameterFloat(FMOD_DSP_ECHO_DELAY,300);
        dsp->setParameterFloat(FMOD_DSP_ECHO_FEEDBACK,20);
        system->playSound(sound, 0, false, &channel);
        channel->addDSP(0,dsp);
        LOGI("%s","fix kongling");
        break;


      default:
        break;
    }


    system->update();
    while (!FMOD_CLOSE && isPalying) {

      channel->isPlaying(&isPalying);
      usleep(1000 * 1000);
      LOGI("update:%c", isPalying);
    }
    goto end;
  } catch (...) {
    LOGE("%s", "发生异常");
    goto end;
  }


  end:
  env->ReleaseStringUTFChars(jfileName, fileName);
  if (channel != NULL) {
    channel->stop();

  }
  if (sound != NULL) {
    sound->release();
    sound = NULL;
  }
  if (system != NULL) {
    system->close();
    system->release();
    system = NULL;
  }

}


JNIEXPORT void JNICALL Java_com_benny_fmod_FmodVoice_Stop
    (JNIEnv *env, jclass jclazz) {
  FMOD_CLOSE = true;
  LOGI("Java_com_benny_fmod_FmodVoice_Stop");
}