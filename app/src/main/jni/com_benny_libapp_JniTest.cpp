//
// Created by yuanbb on 2017/8/30.
//
#include "com_benny_libapp_JniTest.h"

JNIEXPORT jstring JNICALL Java_com_benny_libapp_JniTest_getText
    (JNIEnv *env, jobject jobj) {
  return env->NewStringUTF("你好呀");

}