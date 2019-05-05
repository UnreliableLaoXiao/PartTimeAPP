package com.schoolpartime.schoolpartime.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;

import com.schoolpartime.schoolpartime.R;
import com.schoolpartime.schoolpartime.SuperActivity;
import com.schoolpartime.schoolpartime.databinding.ActivityBossrequestBinding;
import com.schoolpartime.schoolpartime.presenter.BossRequestPre;
import com.schoolpartime.schoolpartime.presenter.Presenter;

import androidx.databinding.DataBindingUtil;

@SuppressLint("Registered")
public class BossRequestActivity extends SuperActivity {

    private Presenter pre = new BossRequestPre();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityBossrequestBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_bossrequest);
        pre.attach(binding,this);
    }


}
