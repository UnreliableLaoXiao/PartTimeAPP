package com.schoolpartime.schoolpartime.chat;

import android.os.Binder;

import com.google.gson.Gson;
import com.schoolpartime.schoolpartime.entity.Message;
import com.schoolpartime.schoolpartime.util.LogUtil;


public class MessageBind extends Binder implements ChatListener {

    public WebClient webClient = null;
    Gson gson = new Gson();

    public MessageBind() {
        webClient = WebClient.getInstance();
        LogUtil.d("WebClient---->得到连接");
    }

    @Override
    public void sendMessage(Message message){
        webClient.send(gson.toJson(message));
        LogUtil.d("发送消息："+message.toString());
    }
}
