//
// Created by yuanbb on 2017/8/30.
//
#include "com_benny_libapp_JNI2Test.h"

JNIEXPORT jstring JNICALL Java_com_benny_libapp_JNI2Test_getString
    (JNIEnv *env, jobject jobj, jstring content) {
  return env->NewStringUTF("你好呀");
}