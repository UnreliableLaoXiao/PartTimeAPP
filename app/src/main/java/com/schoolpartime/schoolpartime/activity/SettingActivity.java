package com.schoolpartime.schoolpartime.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import com.schoolpartime.schoolpartime.R;
import com.schoolpartime.schoolpartime.SuperActivity;
import com.schoolpartime.schoolpartime.databinding.ActivitySettingBinding;
import com.schoolpartime.schoolpartime.presenter.Presenter;
import com.schoolpartime.schoolpartime.presenter.SettingPre;

import androidx.databinding.DataBindingUtil;

@SuppressLint("Registered")
public class SettingActivity extends SuperActivity{

    Presenter pre = new SettingPre();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivitySettingBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_setting);
        pre.attach(binding,this);
    }

    @Override
    protected void onDestroy() {
        pre.notifyUpdate(8);
        super.onDestroy();
    }
}
