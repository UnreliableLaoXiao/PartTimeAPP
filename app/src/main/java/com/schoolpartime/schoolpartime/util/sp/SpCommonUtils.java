package com.schoolpartime.schoolpartime.util.sp;

import android.app.Activity;

public class SpCommonUtils {

    private static String ONCE_START = "oncestart";
    private static String IS_LOGIN = "isLogin";
    private static String USERID = "userid";

    public static boolean getOnceStart(Activity activity){
        return SpUtils.getSharedPreferencesForBoolean(activity, ONCE_START);
    }

    public static void setOnceStart(Activity activity,boolean boo){
        SpUtils.setSharedPreferences(activity, ONCE_START,boo);
    }

    public static boolean getIsLogin(Activity activity){
        return SpUtils.getSharedPreferencesForBoolean(activity, IS_LOGIN);
    }

    public static void setIsLogin(Activity activity,boolean boo){
        SpUtils.setSharedPreferences(activity,IS_LOGIN,boo);
    }

    public static void setUserId(Activity activity,long id){
        SpUtils.setSharedPreferences(activity,USERID,id);
    }

    public static long getUserId(Activity activity){
        return SpUtils.getSharedPreferencesForLong(activity,USERID);
    }

}
