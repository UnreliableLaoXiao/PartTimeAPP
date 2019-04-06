package com.schoolpartime.schoolpartime.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import com.schoolpartime.schoolpartime.R;
import com.schoolpartime.schoolpartime.SuperActivity;
import com.schoolpartime.schoolpartime.databinding.ActivityMymessagesBinding;
import com.schoolpartime.schoolpartime.presenter.MessagePre;
import com.schoolpartime.schoolpartime.presenter.Presenter;

import androidx.databinding.DataBindingUtil;


@SuppressLint("Registered")
public class MyMessagesActivity extends SuperActivity {

    Presenter pre = new MessagePre();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMymessagesBinding binding =  DataBindingUtil.setContentView(this,R.layout.activity_mymessages);
        pre.attach(binding,this);
    }

    @Override
    protected void onDestroy() {
        pre.notifyUpdate(4);
        super.onDestroy();
    }
}
