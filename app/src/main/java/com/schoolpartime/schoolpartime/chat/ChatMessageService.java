package com.schoolpartime.schoolpartime.chat;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.schoolpartime.schoolpartime.SchoolPartimeApplication;
import com.schoolpartime.schoolpartime.util.LogUtil;
import com.schoolpartime.schoolpartime.util.sp.SpCommonUtils;
import androidx.annotation.Nullable;

public class ChatMessageService extends Service {

    @Override
    public void onCreate() {
        LogUtil.d("初始聊天服务....成功");
        WebClient.initWebSocket(SchoolPartimeApplication.getContext(),SpCommonUtils.getUserId());
        super.onCreate();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        MessageBind bind = new MessageBind();
        return bind;
    }
}
