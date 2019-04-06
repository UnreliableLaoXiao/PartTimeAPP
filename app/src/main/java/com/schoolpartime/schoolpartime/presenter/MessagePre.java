package com.schoolpartime.schoolpartime.presenter;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;

import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.schoolpartime.schoolpartime.R;
import com.schoolpartime.schoolpartime.SuperActivity;
import com.schoolpartime.schoolpartime.activity.RealChatActivity;
import com.schoolpartime.schoolpartime.adapter.MessageListAdapter;
import com.schoolpartime.schoolpartime.chat.WebClient;
import com.schoolpartime.schoolpartime.databinding.ActivityMymessagesBinding;
import com.schoolpartime.schoolpartime.entity.ChatRecord;
import com.schoolpartime.schoolpartime.entity.baseModel.ResultModel;
import com.schoolpartime.schoolpartime.net.interfacz.ChatRecordServer;
import com.schoolpartime.schoolpartime.net.request.HttpRequest;
import com.schoolpartime.schoolpartime.net.request.base.RequestResult;
import com.schoolpartime.schoolpartime.util.LogUtil;
import com.schoolpartime.schoolpartime.util.sp.SpCommonUtils;

import java.util.ArrayList;
import java.util.Date;

import androidx.databinding.ViewDataBinding;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class MessagePre implements Presenter, SwipeRefreshLayout.OnRefreshListener,WebClient.NotifyMessage, View.OnClickListener {

    private SuperActivity activity;
    private ActivityMymessagesBinding binding;
    private volatile ArrayList<ChatRecord> chatRecords = new ArrayList<>();
    private Gson gson = new Gson();

    private WebClient webClient;

    @Override
    public void attach(ViewDataBinding binding, SuperActivity activity) {
        this.binding = (ActivityMymessagesBinding) binding;
        this.activity = activity;
        webClient = WebClient.getInstance();
        init();
    }

    private void init() {
        getRecord();
        setRefresh();
        webClient.addNotity(this);
        binding.mesList.setAdapter(new MessageListAdapter(activity,chatRecords));
        binding.mesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                bundle.putLong("to",chatRecords.get(position).getOther_id());
                bundle.putString("name",chatRecords.get(position).getName());
                (new RealChatActivity()).inToActivity(activity,bundle);
            }
        });
        binding.userBack.setOnClickListener(this);
    }

    @SuppressLint("WrongConstant")
    private void showResult(String mes) {
        Snackbar.make(binding.lly, mes, Snackbar.LENGTH_LONG)
                .setDuration(Snackbar.LENGTH_LONG).show();
    }

    private void getRecord() {
        activity.show("正在加载...");
        HttpRequest.request(HttpRequest.builder().create(ChatRecordServer.class).
                        getChatRecord(SpCommonUtils.getUserId()),
                new RequestResult() {
                    @Override
                    public void success(ResultModel resultModel) {
                        activity.dismiss();
                        LogUtil.d("请求聊天记录列表成功----------ResultModel："+resultModel.toString());
                        if (resultModel.code == 200) {
                            chatRecords = (ArrayList<ChatRecord>) resultModel.data;
                            binding.mesList.setAdapter(new MessageListAdapter(activity,chatRecords));
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

    private void setRefresh() {
        binding.swipeLayout.setOnRefreshListener(this);
        //设置样式刷新显示的位置
        binding.swipeLayout.setProgressViewOffset(true, -10, 50);
        binding.swipeLayout.setColorSchemeResources(R.color.swiperefresh_color1,
                R.color.swiperefresh_color2,
                R.color.swiperefresh_color3,
                R.color.swiperefresh_color4);
    }

    @Override
    public void notifyUpdate(int code) {

        switch (code){
            case 4:
            {
                webClient.removeNotity(this);
            }
            break;
        }
    }

    @Override
    public void onRefresh() {
        handler.sendEmptyMessageDelayed(0,3000);
    }

    @SuppressLint("HandlerLeak")
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what == 0){
                binding.swipeLayout.setRefreshing(false);
            }
        }
    };

    @Override
    public void notify(String mes) {
        com.schoolpartime.schoolpartime.entity.Message message = gson.fromJson(mes, com.schoolpartime.schoolpartime.entity.Message.class);
        LogUtil.d("得到消息：" + message.getMsg_mes());
        for (int i = 0 ; i< chatRecords.size();i++){
            ChatRecord record = chatRecords.get(i);
            if( (record.getId() == message.getMsg_from() || record.getId() == message.getMsg_to()) &&
                    (record.getOther_id() == message.getMsg_to() || record.getOther_id() == message.getMsg_from()))
            {
                record.setMes(message.getMsg_mes());
                record.setDate((new Date()).toString());
                LogUtil.d("更新数据：--------------------》");
                break;
            }
        }
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //刷新ListView
                binding.mesList.setAdapter(new MessageListAdapter(activity,chatRecords));
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.user_back:
            {
                activity.finish();
            }
            break;
        }
    }
}
