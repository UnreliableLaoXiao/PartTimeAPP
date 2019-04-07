package com.schoolpartime.schoolpartime.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;

import com.schoolpartime.schoolpartime.R;
import com.schoolpartime.schoolpartime.SuperActivity;
import com.schoolpartime.schoolpartime.databinding.ActivityBossinfoBinding;
import com.schoolpartime.schoolpartime.presenter.BossInfoPre;
import com.schoolpartime.schoolpartime.presenter.Presenter;

import androidx.databinding.DataBindingUtil;

@SuppressLint("Registered")
public class BossInfoActivity extends SuperActivity {

    private Presenter pre = new BossInfoPre();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityBossinfoBinding binding =  DataBindingUtil.setContentView(this, R.layout.activity_bossinfo);
        pre.attach(binding,this);
    }
}
