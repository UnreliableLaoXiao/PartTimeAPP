package com.schoolpartime.schoolpartime.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.schoolpartime.schoolpartime.SchoolPartimeApplication;
import com.schoolpartime.schoolpartime.chat.MessageBind;
import com.schoolpartime.schoolpartime.chat.WebClient;
import com.schoolpartime.schoolpartime.util.LogUtil;
import com.schoolpartime.schoolpartime.util.sp.SpCommonUtils;
import androidx.annotation.Nullable;

public class ChatMessageService extends Service {

    private MessageBind bind;

    @Override
    public void onCreate() {
        LogUtil.d("初始聊天服务....成功");
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        WebClient.initWebSocket(SchoolPartimeApplication.getContext(),SpCommonUtils.getUserId());
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        LogUtil.d("绑定聊天服务....成功");
        bind = new MessageBind();
        return bind;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        LogUtil.d("解绑聊天服务....成功");
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        LogUtil.d("停止聊天服务....成功");
        bind.close(888);
        super.onDestroy();
    }
}
