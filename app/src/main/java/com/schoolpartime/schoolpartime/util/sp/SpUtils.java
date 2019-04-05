package com.schoolpartime.schoolpartime.util.sp;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.schoolpartime.schoolpartime.SchoolPartimeApplication;

/**
 * Created by Auser on 2018/4/14.
 * 用于存取sp 中的数据
 */

public class SpUtils {

    private static SharedPreferences msharedPreferences;
    private static SharedPreferences.Editor editor;

    public static int getSharedPreferencesForInt(String key){
        msharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(SchoolPartimeApplication.getContext());
        return msharedPreferences.getInt(key,-1);
    }

    public static String getSharedPreferencesForString(String key){
        msharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(SchoolPartimeApplication.getContext());
        return msharedPreferences.getString(key,null);
    }

    static boolean getSharedPreferencesForBoolean(String key){
        msharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(SchoolPartimeApplication.getContext());
        return msharedPreferences.getBoolean(key,false);
    }

    static long getSharedPreferencesForLong(String key){
        msharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(SchoolPartimeApplication.getContext());
        return msharedPreferences.getLong(key,0l);
    }

    @SuppressLint("ApplySharedPref")
    public static void setSharedPreferences(String key, int value){
        msharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(SchoolPartimeApplication.getContext());
        editor = msharedPreferences.edit();
        editor.putInt(key,value);
        editor.commit();
    }

    @SuppressLint("ApplySharedPref")
    public static void setSharedPreferences(String key, String value){
        msharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(SchoolPartimeApplication.getContext());
        editor = msharedPreferences.edit();
        editor.putString(key,value);
        editor.commit();
    }

    @SuppressLint("ApplySharedPref")
    static void setSharedPreferences(String key, Boolean value){
        msharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(SchoolPartimeApplication.getContext());
        editor = msharedPreferences.edit();
        editor.putBoolean(key,value);
        editor.commit();
    }

    @SuppressLint("ApplySharedPref")
    static void setSharedPreferences(String key, long value){
        msharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(SchoolPartimeApplication.getContext());
        editor = msharedPreferences.edit();
        editor.putLong(key,value);
        editor.commit();
    }


}
