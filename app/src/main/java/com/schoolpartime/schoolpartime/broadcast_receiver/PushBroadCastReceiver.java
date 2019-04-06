package com.schoolpartime.schoolpartime.broadcast_receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.schoolpartime.schoolpartime.util.LogUtil;

public class PushBroadCastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        LogUtil.d("收到广播:");
        String data = intent.getStringExtra("data");
        LogUtil.d("收到广播:" + data);
    }
}
