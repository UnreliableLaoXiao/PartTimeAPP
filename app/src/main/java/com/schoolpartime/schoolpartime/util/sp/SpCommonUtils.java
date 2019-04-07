package com.schoolpartime.schoolpartime.util.sp;

public class SpCommonUtils {

    private static String ONCE_START = "oncestart";
    private static String IS_LOGIN = "isLogin";
    private static String USERID = "userid";
    private static String USERTYPE = "usertype";

    public static boolean getOnceStart(){
        return SpUtils.getSharedPreferencesForBoolean(ONCE_START);
    }

    public static void setOnceStart(boolean boo){
        SpUtils.setSharedPreferences(ONCE_START,boo);
    }

    public static boolean getIsLogin(){
        return SpUtils.getSharedPreferencesForBoolean(IS_LOGIN);
    }

    public static void setIsLogin(boolean boo){
        SpUtils.setSharedPreferences(IS_LOGIN,boo);
    }

    public static void setUserId(long id){
        SpUtils.setSharedPreferences(USERID,id);
    }

    public static long getUserId(){
        return SpUtils.getSharedPreferencesForLong(USERID);
    }

    public static int getUserType(){ return SpUtils.getSharedPreferencesForInt(USERTYPE); }

    public static void setUserType(int type){ SpUtils.setSharedPreferences(USERTYPE,type); }

}
