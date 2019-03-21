package com.schoolpartime.schoolpartime.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;

import com.schoolpartime.schoolpartime.R;
import com.schoolpartime.schoolpartime.SuperActivity;
import com.schoolpartime.schoolpartime.databinding.ActivityCheckupdateBinding;
import com.schoolpartime.schoolpartime.presenter.CheckUpdatePre;
import com.schoolpartime.schoolpartime.presenter.Presenter;

import androidx.databinding.DataBindingUtil;

@SuppressLint("Registered")
public class CheckUpdateActivity extends SuperActivity {

    Presenter pre = new CheckUpdatePre();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCheckupdateBinding binding =  DataBindingUtil.setContentView(this,R.layout.activity_checkupdate);
        pre.attach(binding,this);
    }
}
