package com.schoolpartime.schoolpartime.presenter;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;

import com.schoolpartime.schoolpartime.R;
import com.schoolpartime.schoolpartime.SuperActivity;
import com.schoolpartime.schoolpartime.adapter.MessageListAdapter;
import com.schoolpartime.schoolpartime.databinding.ActivityMymessagesBinding;
import com.schoolpartime.schoolpartime.entity.ChatMessage;

import java.util.ArrayList;

import androidx.databinding.ViewDataBinding;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class MessagePre implements Presenter, SwipeRefreshLayout.OnRefreshListener {

    private SuperActivity activity;
    private ActivityMymessagesBinding binding;


    @Override
    public void attach(ViewDataBinding binding, SuperActivity activity) {
        this.binding = (ActivityMymessagesBinding) binding;
        this.activity = activity;
        init();
    }

    private void init() {
        setRefresh();
        binding.mesList.setAdapter(new MessageListAdapter(activity,new ArrayList<ChatMessage>()));
        binding.mesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){

                }
            }
        });
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
