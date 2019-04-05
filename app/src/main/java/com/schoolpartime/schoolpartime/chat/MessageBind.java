package com.schoolpartime.schoolpartime.chat;

import android.os.Binder;
import android.util.Log;

import com.google.gson.Gson;
import com.schoolpartime.schoolpartime.entity.Message;
import com.schoolpartime.schoolpartime.util.LogUtil;


public class MessageBind extends Binder implements ChatListener {

    WebClient webClient = null;

    public MessageBind() {
        webClient = WebClient.getInstance();
        LogUtil.d("WebClient---->得到连接");
    }

    @Override
    public void sendMessage(Message message) {
        Gson gson = new Gson();
        webClient.send(gson.toJson(message));
        LogUtil.d("发送消息："+message.toString());
    }
}
