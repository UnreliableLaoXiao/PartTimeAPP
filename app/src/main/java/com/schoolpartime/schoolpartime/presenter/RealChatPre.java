package com.schoolpartime.schoolpartime.presenter;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.text.TextUtils;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.schoolpartime.schoolpartime.R;
import com.schoolpartime.schoolpartime.SuperActivity;
import com.schoolpartime.schoolpartime.adapter.ChatAdapter;
import com.schoolpartime.schoolpartime.adapter.MessageListAdapter;
import com.schoolpartime.schoolpartime.chat.ChatMessageService;
import com.schoolpartime.schoolpartime.chat.MessageBind;
import com.schoolpartime.schoolpartime.chat.WebClient;
import com.schoolpartime.schoolpartime.databinding.ActivityRealchatBinding;
import com.schoolpartime.schoolpartime.entity.ChatRecord;
import com.schoolpartime.schoolpartime.entity.Message;
import com.schoolpartime.schoolpartime.entity.baseModel.ResultModel;
import com.schoolpartime.schoolpartime.net.interfacz.ChatMessageServer;
import com.schoolpartime.schoolpartime.net.interfacz.ChatRecordServer;
import com.schoolpartime.schoolpartime.net.request.HttpRequest;
import com.schoolpartime.schoolpartime.net.request.base.RequestResult;
import com.schoolpartime.schoolpartime.util.LogUtil;
import com.schoolpartime.schoolpartime.util.sp.SpCommonUtils;

import java.util.ArrayList;
import java.util.List;

import androidx.databinding.ViewDataBinding;

public class RealChatPre implements Presenter, View.OnClickListener,WebClient.NotifyMessage {

    private SuperActivity activity;
    private ActivityRealchatBinding binding;
    private List<Message> personChats = new ArrayList<>();
    private ChatAdapter chatAdapter;
    private Gson gson = new Gson();

    private MessageBind binder;

    private long from = 0;
    private long to = 0;

    private WebClient webClient;

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
        webClient = WebClient.getInstance();
        init();
    }

    @SuppressLint("WrongConstant")
    private void showResult(String mes) {
        Snackbar.make(binding.lly, mes, Snackbar.LENGTH_LONG)
                .setDuration(Snackbar.LENGTH_LONG).show();
    }

    private void init() {

        Bundle bundle = activity.getIntent().getExtras();
        binding.chatName.setText(bundle.getString("name"));
        from = SpCommonUtils.getUserId();
        to = bundle.getLong("to");
        getMessages();
        chatAdapter = new ChatAdapter(activity, personChats);
        binding.lvChatDialog.setAdapter(chatAdapter);
        binding.btnChatMessageSend.setOnClickListener(this);
        Intent intent = new Intent();
        intent.setClass(activity, ChatMessageService.class);
        activity.bindService(intent,connection,activity.BIND_AUTO_CREATE);
        webClient.addNotity(this);
        binding.chatBack.setOnClickListener(this);
    }

    private void getMessages() {
        HttpRequest.request(HttpRequest.builder().create(ChatMessageServer.class).
                        getMessages(from,to),
                new RequestResult() {
                    @Override
                    public void success(ResultModel resultModel) {
                        activity.dismiss();
                        LogUtil.d("请求聊天记录----------ResultModel："+resultModel.toString());
                        if (resultModel.code == 200) {
                            personChats.addAll((ArrayList<Message>)resultModel.data);
                            chatAdapter.notifyDataSetChanged();
                            notifyUpdate(3);
                        } else {
                            showResult(resultModel.message);
                        }
                    }

                    @Override
                    public void fail(Throwable e) {
                        LogUtil.d("请求聊天记录列表成功---------",e);
                        activity.dismiss();
                        showResult("请求失败");
                    }
                },true);
    }

    @Override
    public void notifyUpdate(int code) {
        switch (code) {
            case 3: {
                binding.lvChatDialog.setSelection(personChats.size());
            }
            break;
            case 4: {
                webClient.removeNotity(this);
            }
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
                message.setMsg_from(from);
                message.setMsg_to(to);
                message.setMsg_type(1);
                message.setMsg_state(0);
                message.setMsg_mes(binding.etChatMessage.getText().toString());
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
            case R.id.chat_back:{
                activity.finish();
            }
            break;
        }
    }

    @Override
    public void notify(final String mes) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Message message1 = gson.fromJson(mes, Message.class);
                LogUtil.d("得到消息：" + message1.getMsg_mes());
                personChats.add(message1);
                //刷新ListView
                chatAdapter.notifyDataSetChanged();
                notifyUpdate(3);
            }
        });

    }

}
