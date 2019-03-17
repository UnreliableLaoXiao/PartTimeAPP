package com.schoolpartime.schoolpartime.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;

import com.schoolpartime.schoolpartime.R;
import com.schoolpartime.schoolpartime.SuperActivity;
import com.schoolpartime.schoolpartime.databinding.ActivityUserinfoBinding;
import com.schoolpartime.schoolpartime.presenter.Presenter;
import com.schoolpartime.schoolpartime.presenter.UserInfoPre;

import androidx.databinding.DataBindingUtil;

@SuppressLint("Registered")
public class UserInfoActivity extends SuperActivity {

    Presenter pre = new UserInfoPre();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityUserinfoBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_userinfo);
        pre.attach(binding,this);
    }

}
