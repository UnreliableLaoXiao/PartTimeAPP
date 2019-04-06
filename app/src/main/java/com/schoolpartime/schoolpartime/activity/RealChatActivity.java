package com.schoolpartime.schoolpartime.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;

import com.schoolpartime.schoolpartime.R;
import com.schoolpartime.schoolpartime.SuperActivity;
import com.schoolpartime.schoolpartime.databinding.ActivityRealchatBinding;
import com.schoolpartime.schoolpartime.presenter.Presenter;
import com.schoolpartime.schoolpartime.presenter.RealChatPre;

import androidx.databinding.DataBindingUtil;

@SuppressLint("Registered")
public class RealChatActivity extends SuperActivity {

    private Presenter pre = new RealChatPre();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityRealchatBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_realchat);
        pre.attach(binding,this);
    }

    @Override
    protected void onDestroy() {
        pre.notifyUpdate(4);
        super.onDestroy();
    }
}
