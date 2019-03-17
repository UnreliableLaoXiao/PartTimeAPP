package com.schoolpartime.schoolpartime.activity;

import androidx.databinding.DataBindingUtil;
import android.os.Bundle;

import com.schoolpartime.schoolpartime.R;
import com.schoolpartime.schoolpartime.SuperActivity;
import com.schoolpartime.schoolpartime.databinding.ActivityRegisterBinding;
import com.schoolpartime.schoolpartime.listener.IntentOnClickListener;
import com.schoolpartime.schoolpartime.presenter.Presenter;
import com.schoolpartime.schoolpartime.presenter.RegisterPre;

/**
 * Created by machenike on 2018/11/5.
 * 注册账号
 */

public class RegisterActivity extends SuperActivity {
    private ActivityRegisterBinding binding;

    Presenter pre = new RegisterPre();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_register);
        pre.attach(binding,this);
        binding.setHandler(new IntentOnClickListener());
    }
}
