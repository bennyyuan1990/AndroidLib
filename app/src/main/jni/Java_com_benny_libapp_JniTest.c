//
// Created by yuanbb on 2017/8/29.
//
#include <jni.h>

JNIEXPORT jstring JNICALL Java_com_benny_libapp_JniTest_getText(JNIEnv * env, jobject jobj){
   jstring new_jstr = (*env)->NewStringUTF(env, "Hello World11111");
   return new_jstr;
}



