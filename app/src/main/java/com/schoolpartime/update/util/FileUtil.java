package com.schoolpartime.update.util;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;

import java.io.File;

import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

/**
 * Created by Auser on 2017/12/22.
 * 用于文件操作
 */

public class FileUtil {

    // Storage Permissions
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static final int REQUEST_CALL_PHONE = 2;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    private static String[] PERMISSIONS_CALLPHONE= {
            Manifest.permission.CALL_PHONE,
    };

    public static File getFilePath(Activity activity){
        String filepath ;
        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            filepath = Environment.getExternalStorageDirectory().getAbsolutePath();
        }else{
            filepath = activity.getFilesDir().getAbsolutePath();
        }
        return new File(filepath,"download.apk");
    }

    public static void install(Activity activity) {
        File apkFile = getFilePath(activity);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Uri contentUri = FileProvider.getUriForFile( activity ,activity.getPackageName()+".fileprovider" , apkFile);
            intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
        } else {
            intent.setDataAndType(Uri.fromFile(apkFile), "application/vnd.android.package-archive");
        }
        activity.startActivity(intent);
        activity.finish();
    }



    //动态获取内存存储权限
    public static boolean verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE);
            return true;
        }else {
            return true;
        }
    }

    //动态获取拨打电话的权限
    public static boolean callPhonePermissions(Activity activity) {
            int permission = ActivityCompat.checkSelfPermission(activity,
                    Manifest.permission.CALL_PHONE);
            if (permission != PackageManager.PERMISSION_GRANTED) {
                // We don't have permission so prompt the user
                ActivityCompat.requestPermissions(activity, PERMISSIONS_CALLPHONE,
                        REQUEST_CALL_PHONE);
                return false;
            } else {
                return true;
            }
    }

    public static void delDownLaodFile(Activity activity){
        File file = getFilePath(activity);
        if (file.exists()){
            file.delete();
        }
    }
}
