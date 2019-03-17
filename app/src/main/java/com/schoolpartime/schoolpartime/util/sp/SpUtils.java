package com.schoolpartime.schoolpartime.util.sp;

import android.annotation.SuppressLint;
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

    static boolean getSharedPreferencesForBoolean(Activity activity, String key){
        msharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(activity);
        return msharedPreferences.getBoolean(key,false);
    }

    static long getSharedPreferencesForLong(Activity activity, String key){
        msharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(activity);
        return msharedPreferences.getLong(key,0l);
    }

    @SuppressLint("ApplySharedPref")
    public static void setSharedPreferences(Context activity, String key, int value){
        msharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(activity);
        editor = msharedPreferences.edit();
        editor.putInt(key,value);
        editor.commit();
    }

    @SuppressLint("ApplySharedPref")
    public static void setSharedPreferences(Context activity, String key, String value){
        msharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(activity);
        editor = msharedPreferences.edit();
        editor.putString(key,value);
        editor.commit();
    }

    @SuppressLint("ApplySharedPref")
    static void setSharedPreferences(Context activity, String key, Boolean value){
        msharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(activity);
        editor = msharedPreferences.edit();
        editor.putBoolean(key,value);
        editor.commit();
    }

    @SuppressLint("ApplySharedPref")
    static void setSharedPreferences(Context activity, String key, long value){
        msharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(activity);
        editor = msharedPreferences.edit();
        editor.putLong(key,value);
        editor.commit();
    }


}
