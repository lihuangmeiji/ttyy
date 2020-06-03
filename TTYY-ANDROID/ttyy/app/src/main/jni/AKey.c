#include "jni.h"
#include "com_system_everydayvideo_AKey.h"

JNIEXPORT jstring JNICALL Java_com_system_everydayvideo_AKey_getString
  (JNIEnv *env, jclass jz){

  return (*env)->NewStringUTF(env,"(dQPKgUkbl_#EV-C");
}

JNIEXPORT jstring JNICALL Java_com_system_everydayvideo_AKey_getString1
  (JNIEnv *env, jclass jz){

  return (*env)->NewStringUTF(env,"AES");
}

JNIEXPORT jstring JNICALL Java_com_system_everydayvideo_AKey_getString2
  (JNIEnv *env, jclass jz){

  return (*env)->NewStringUTF(env,"AES/ECB/PKCS5Padding");
}

JNIEXPORT jstring JNICALL Java_com_system_everydayvideo_AKey_getString3
  (JNIEnv *env, jclass jz){

  return (*env)->NewStringUTF(env,"123qweASD!@#");
}

