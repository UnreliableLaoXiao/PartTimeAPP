package com.schoolpartime.schoolpartime.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;

import com.schoolpartime.schoolpartime.R;
import com.schoolpartime.schoolpartime.SuperActivity;
import com.schoolpartime.schoolpartime.databinding.ActivityProtocolBinding;
import com.schoolpartime.schoolpartime.presenter.Presenter;
import com.schoolpartime.schoolpartime.presenter.ProtocolPre;

import androidx.databinding.DataBindingUtil;

@SuppressLint("Registered")
public class ProtocolActivity extends SuperActivity {

    Presenter pre = new ProtocolPre();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityProtocolBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_protocol);
        pre.attach(binding,this);
    }
}
