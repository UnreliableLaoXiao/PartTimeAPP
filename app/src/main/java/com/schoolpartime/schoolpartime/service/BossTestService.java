package com.schoolpartime.schoolpartime.service;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.schoolpartime.schoolpartime.fragment.UserFragment;
import com.schoolpartime.schoolpartime.util.LogUtil;
import com.schoolpartime.schoolpartime.util.sp.SpCommonUtils;

import java.io.IOException;

import androidx.annotation.Nullable;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

@SuppressLint("Registered")
public class BossTestService extends Service {

    private volatile boolean isSuccess = false;

    @Override
    public void onCreate() {
        LogUtil.d("初始商家申请检测....成功");
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        new Thread(){

            @Override
            public void run() {
                while(!isSuccess) {
                    try {
                        LogUtil.d("60s后开始检测----》");
                        Thread.sleep(60000);
                        FormBody.Builder formBuilder = new FormBody.Builder();
                        formBuilder.add("id",SpCommonUtils.getUserId()+"");
                        final Request request = new Request.Builder()
                                .url("http://192.168.124.11:8080/getusertype")
                                .post(formBuilder.build())
                                .build();
                        OkHttpClient okHttpClient = new OkHttpClient();
                        okHttpClient.newCall(request).enqueue(new Callback() {
                            @Override
                            public void onFailure(Call call, IOException e) {
                                LogUtil.d("onFailure: " + e.getMessage());
                            }
                            @Override
                            public void onResponse(Call call, Response response) throws IOException {
                                LogUtil.d(response.protocol() + " " +response.code() + " " + response.message());
                                String s = response.body().string();
                                LogUtil.d("检测结果为：---》onResponse: " + s);
                                int num = Integer.valueOf(s);
                                if (num == 3){
                                    SpCommonUtils.setUserType(3);
                                    Intent intent = new Intent();
                                    intent.setAction(UserFragment.SYSTEM_EXIT);
                                    sendBroadcast(intent);
                                    isSuccess = true;
                                }
                            }
                        });
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                LogUtil.d("检测结束---》准备关闭此服务");
                BossTestService.super.onDestroy();
            }
        }.start();
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        LogUtil.d("商家申请检测关闭成功");
        super.onDestroy();
    }
}
