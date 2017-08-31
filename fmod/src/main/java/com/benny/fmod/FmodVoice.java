package com.benny.fmod;

import android.content.Context;

/**
 * Created by yuanbb on 2017/8/31.
 */

public class FmodVoice {

    static {
        System.loadLibrary("fmodL");
        System.loadLibrary("fmod");
        System.loadLibrary("fmodeVoice");
    }


    public static int FMOD_VOICE_TYPE_NORMAL =0;
    public static int FMOD_VOICE_TYPE_LUOLI =1;
    public static int FMOD_VOICE_TYPE_JINGSONG =2;
    public static int FMOD_VOICE_TYPE_DASHU =3;
    public static int FMOD_VOICE_TYPE_GAOGUAI =4;
    public static int FMOD_VOICE_TYPE_KONGLING =5;


    public static native void PlayVoice(String fileName,int type);

    public static native void Stop();



    public static void init(Context context){
        org.fmod.FMOD.init(context);
    }


    public static void close(){
        org.fmod.FMOD.close();
    }


}
