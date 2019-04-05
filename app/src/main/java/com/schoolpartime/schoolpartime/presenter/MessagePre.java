package com.schoolpartime.schoolpartime.presenter;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;

import com.google.android.material.snackbar.Snackbar;
import com.schoolpartime.schoolpartime.R;
import com.schoolpartime.schoolpartime.SuperActivity;
import com.schoolpartime.schoolpartime.activity.RealChatActivity;
import com.schoolpartime.schoolpartime.adapter.MessageListAdapter;
import com.schoolpartime.schoolpartime.databinding.ActivityMymessagesBinding;
import com.schoolpartime.schoolpartime.entity.ChatRecord;
import com.schoolpartime.schoolpartime.entity.baseModel.ResultModel;
import com.schoolpartime.schoolpartime.net.interfacz.ChatRecordServer;
import com.schoolpartime.schoolpartime.net.request.HttpRequest;
import com.schoolpartime.schoolpartime.net.request.base.RequestResult;
import com.schoolpartime.schoolpartime.util.LogUtil;
import com.schoolpartime.schoolpartime.util.sp.SpCommonUtils;

import java.util.ArrayList;

import androidx.databinding.ViewDataBinding;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class MessagePre implements Presenter, SwipeRefreshLayout.OnRefreshListener {

    private SuperActivity activity;
    private ActivityMymessagesBinding binding;
    ArrayList<ChatRecord> chatRecords = new ArrayList<>();


    @Override
    public void attach(ViewDataBinding binding, SuperActivity activity) {
        this.binding = (ActivityMymessagesBinding) binding;
        this.activity = activity;
        init();
    }

    private void init() {
        getRecord();
        setRefresh();
        binding.mesList.setAdapter(new MessageListAdapter(activity,chatRecords));
        binding.mesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                (new RealChatActivity()).inToActivity(activity,chatRecords.get(position).getName());
            }
        });
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
}
