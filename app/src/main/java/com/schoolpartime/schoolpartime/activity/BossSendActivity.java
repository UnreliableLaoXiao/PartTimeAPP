package com.schoolpartime.schoolpartime.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;

import com.schoolpartime.schoolpartime.R;
import com.schoolpartime.schoolpartime.SuperActivity;
import com.schoolpartime.schoolpartime.databinding.ActivityBosssendBinding;
import com.schoolpartime.schoolpartime.presenter.BossSendPre;
import com.schoolpartime.schoolpartime.presenter.Presenter;

import androidx.databinding.DataBindingUtil;

@SuppressLint("Registered")
public class BossSendActivity extends SuperActivity {

    Presenter pre = new BossSendPre();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityBosssendBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_bosssend);
        pre.attach(binding,this);
    }
}
