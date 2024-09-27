#include <string.h>
#include <jni.h>
#include <stdio.h>
#include <stdlib.h>

JNIEXPORT jstring JNICALL
Java_id_ac_ui_cs_mobileprogramming_muhammadferilbagusperkasa_cuacajabodetabek_fragment_AboutFragment_helloName(JNIEnv *env,
                                 jobject thiz,
                                 jstring string) {
    const char *name = (*env)->GetStringUTFChars(env,string, NULL);
    char message[74] = "Hello World!, ";
    jstring result;

    strcat(message, name);
    (*env)->ReleaseStringUTFChars(env,string, name);
    puts(message);
    result = (*env)->NewStringUTF(env,message);
    return result;
}


JNIEXPORT jint JNICALL Java_id_ac_ui_cs_mobileprogramming_muhammadferilbagusperkasa_cuacajabodetabek_fragment_AboutFragment_luckyNumber
        (JNIEnv *env, jclass clazz){
    int n = (rand()%100)+1;
    return n;
}