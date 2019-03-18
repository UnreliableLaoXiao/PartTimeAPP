package com.schoolpartime.schoolpartime.util;

import android.util.Log;

public class LogUtil {

    private static boolean mswitch = true;
    private static String TAG = "HeiBug";

    public static void i(String mes){
        if(mswitch)
            Log.i(TAG,mes);
    }

    public static void w(String mes){
        if(mswitch)
            Log.w(TAG,mes);
    }

    public static void d(String mes){
        if(mswitch)
            Log.d(TAG,mes);
    }

    public static void e(String mes){
        if(mswitch)
            Log.e(TAG,mes);
    }

    public static void e(String mes,Throwable e){
        if(mswitch)
            Log.e(TAG,mes,e);
    }

    public static void v(String mes){
        if(mswitch)
            Log.v(TAG,mes);
    }

}
