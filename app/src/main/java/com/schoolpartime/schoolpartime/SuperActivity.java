package com.schoolpartime.schoolpartime;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.schoolpartime.schoolpartime.dialog.DialogUtil;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Created by machenike on 2018/10/12.
 * 所有类的基类
 * 功能：
 * 1、可以控制程序整体退出
 * 2、提供退出方法
 * 3、提供Activity的跳转方法
 */

@SuppressLint("Registered")
public class SuperActivity extends AppCompatActivity {

    public static final String TAG = "DEBUG";

    /**
     * 广播action
     */
    public static final String SYSTEM_EXIT = "com.example.exitsystem.system_exit";
    /**
     * 接收器
     */
    private MyReceiver receiver;

    Dialog dialog = null;

    @SuppressLint("HandlerLeak")
    public Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
                    finish();

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //注册广播，用于退出程序
        IntentFilter filter = new IntentFilter();
        filter.addAction(SYSTEM_EXIT);
        receiver = new MyReceiver();
        this.registerReceiver(receiver, filter);
    }

    @Override
    protected void onDestroy() {
        //记得取消广播注册
        this.unregisterReceiver(receiver);
        super.onDestroy();
    }


    private class MyReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            finish();
        }
    }

    public void show(String mes){
        if( dialog == null ){
            dialog = DialogUtil.loadingDialog(this,mes);
        }
        dialog.show();
    }

    public void dismiss(){
        if( dialog!= null)
            dialog.dismiss();
    }

    public void exit() {
        Intent intent = new Intent();
        intent.setAction(SuperActivity.SYSTEM_EXIT);
        sendBroadcast(intent);
    }

    long exitTime = 0;
    public void BackExit(){
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            Toast.makeText(getApplicationContext(), "再按一次退出程序",
                    Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else {
            exit();
        }
    }

    @SuppressLint("WrongConstant")
    public void showResult(RelativeLayout layout, String mes) {
        Snackbar.make(layout, mes, Snackbar.LENGTH_LONG)
                .setDuration(Snackbar.LENGTH_LONG).show();
    }

    @SuppressLint("WrongConstant")
    public void showResult(LinearLayout layout, String mes) {
        Snackbar.make(layout, mes, Snackbar.LENGTH_LONG)
                .setDuration(Snackbar.LENGTH_LONG).show();
    }

    public void inToActivity(Activity activity){
        Intent intent = new Intent(activity , this.getClass());
        activity.startActivity(intent);
    }

    public void inToActivityForResult(Activity activity,int request){
        Intent intent = new Intent(activity , this.getClass());
        activity.startActivityForResult(intent,request);
    }

    public void inToActivity(Activity activity,Bundle data){
        Intent intent = new Intent(activity , this.getClass());
        intent.putExtras(data);
        activity.startActivity(intent);
    }

    public void inToActivityForResult(Activity activity,Bundle data,int request){
        Intent intent = new Intent(activity , this.getClass());
        intent.putExtras(data);
        activity.startActivityForResult(intent,request);
    }



}