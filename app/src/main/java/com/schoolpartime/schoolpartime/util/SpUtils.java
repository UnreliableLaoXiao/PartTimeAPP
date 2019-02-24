package com.schoolpartime.schoolpartime.util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by Auser on 2018/4/14.
 * 用于存取sp 中的数据
 */

public class SpUtils {

    private static SharedPreferences msharedPreferences;
    private static SharedPreferences.Editor editor;

    public static int getSharedPreferencesForInt(Activity activity,String key){
        msharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(activity);
        return msharedPreferences.getInt(key,-1);
    }

    public static String getSharedPreferencesForString(Activity activity,String key){
        msharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(activity);
        return msharedPreferences.getString(key,null);
    }

    public static boolean getSharedPreferencesForBoolean(Activity activity,String key){
        msharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(activity);
        return msharedPreferences.getBoolean(key,false);
    }

    public static void setSharedPreferences(Context activity, String key, int value){
        msharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(activity);
        editor = msharedPreferences.edit();
        editor.putInt(key,value);
        editor.commit();
    }

    public static void setSharedPreferences(Context activity, String key, String value){
        msharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(activity);
        editor = msharedPreferences.edit();
        editor.putString(key,value);
        editor.commit();
    }

    public static void setSharedPreferences(Context activity, String key, Boolean value){
        msharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(activity);
        editor = msharedPreferences.edit();
        editor.putBoolean(key,value);
        editor.commit();
    }


}
