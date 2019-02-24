package com.example.updateapp.util;

import android.app.Activity;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/**
 * Created by Auser on 2017/12/22.
 */

public class VersionUtil {

    private static String versionName;
    private static int versionCode;
    private static String packageNames;

    public static String getVerName(Activity activity){
        PackageInfo packageInfo;

        try {
            packageInfo= activity.getPackageManager().getPackageInfo(activity.getPackageName(),0);
            versionName=packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            versionName="null";
        }

        return versionName;
    }
}
