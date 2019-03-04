package com.schoolpartime.schoolpartime.util.sp;

import android.app.Activity;

public class SpCommonUtils {

    private static String ONCE_START = "oncestart";
    private static String IS_LOGIN = "isLogin";

    public static boolean isOnceStart(Activity activity){
        return SpUtils.getSharedPreferencesForBoolean(activity, ONCE_START);
    }

    public static void setIsLogin(Activity activity){
        SpUtils.setSharedPreferences(activity,IS_LOGIN,false);
    }

}
