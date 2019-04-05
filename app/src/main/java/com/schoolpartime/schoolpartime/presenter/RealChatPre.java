package com.schoolpartime.schoolpartime.presenter;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.text.TextUtils;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.schoolpartime.schoolpartime.R;
import com.schoolpartime.schoolpartime.SuperActivity;
import com.schoolpartime.schoolpartime.adapter.ChatAdapter;
import com.schoolpartime.schoolpartime.chat.ChatMessageService;
import com.schoolpartime.schoolpartime.chat.MessageBind;
import com.schoolpartime.schoolpartime.databinding.ActivityRealchatBinding;
import com.schoolpartime.schoolpartime.entity.Message;

import java.util.ArrayList;
import java.util.List;

import androidx.databinding.ViewDataBinding;

public class RealChatPre implements Presenter, View.OnClickListener {

    private SuperActivity activity;
    private ActivityRealchatBinding binding;
    private List<Message> personChats = new ArrayList<>();
    private ChatAdapter chatAdapter;

    private MessageBind binder;

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            binder = (MessageBind) service;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
        }
    };

    @Override
    public void attach(ViewDataBinding binding, SuperActivity activity) {
        this.binding = (ActivityRealchatBinding) binding;
        this.activity = activity;
        init();
    }

    @SuppressLint("WrongConstant")
    private void showResult(String mes) {
        Snackbar.make(binding.lly, mes, Snackbar.LENGTH_LONG)
                .setDuration(Snackbar.LENGTH_LONG).show();
    }

    private void init() {

        String name = activity.getIntent().getStringExtra("to");
        binding.chatName.setText(name);
        /**
         * 虚拟4条发送方的消息
         * */
//        Message message1 = new Message("你好呀", 7, 8, 1);
//        Message message2 = new Message("我不好！", 8, 7, 1);
//        Message message3 = new Message("你咋啦？", 7, 8, 1);
//        Message message4 = new Message("我没事！！！", 8, 7, 1);
//        Message message5 = new Message("真的？？", 7, 8, 1);
//        Message message6 = new Message("对", 8, 7, 1);
//        Message message7 = new Message("好吧，再见", 7, 8, 1);
//        personChats.add(message1);
//        personChats.add(message2);
//        personChats.add(message3);
//        personChats.add(message4);
//        personChats.add(message5);
//        personChats.add(message6);
//        personChats.add(message7);

        chatAdapter = new ChatAdapter(activity, personChats);
        binding.lvChatDialog.setAdapter(chatAdapter);
        binding.btnChatMessageSend.setOnClickListener(this);
        Intent intent = new Intent();
        intent.setClass(activity, ChatMessageService.class);
        activity.bindService(intent,connection,activity.BIND_AUTO_CREATE);
    }

    @Override
    public void notifyUpdate(int code) {
        switch (code) {
            case 3: {
                binding.lvChatDialog.setSelection(personChats.size());
            }
            break;
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_chat_message_send: {
                // TODO Auto-generated method stub
                if (TextUtils.isEmpty(binding.etChatMessage.getText().toString())) {
                    showResult("发送内容不能为空");
                    return;
                }
                Message message = new Message();
                message.setFrom(7);
                message.setTo(8);
                message.setType(1);
                message.setMes(binding.etChatMessage.getText().toString());
                //加入集合
                personChats.add(message);
                //清空输入框
                binding.etChatMessage.setText("");
                //刷新ListView
                chatAdapter.notifyDataSetChanged();
                notifyUpdate(3);
                binder.sendMessage(message);
            }
            break;
        }
    }
}
