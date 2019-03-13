package com.schoolpartime.schoolpartime.activity;

import androidx.databinding.DataBindingUtil;
import android.os.Bundle;

import com.schoolpartime.schoolpartime.R;
import com.schoolpartime.schoolpartime.SuperActivity;
import com.schoolpartime.schoolpartime.databinding.ActivityLoginBinding;
import com.schoolpartime.schoolpartime.listener.IntentOnClickListener;
import com.schoolpartime.schoolpartime.presenter.LoginPre;
import com.schoolpartime.schoolpartime.presenter.Presenter;


/**
 * Created by machenike on 2018/11/5.
 * 用于兼职的登录界面
 */

public class LoginActivity extends SuperActivity {

    private Presenter pre = new LoginPre();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityLoginBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        pre.attach(binding,this);
        binding.setHandler(new IntentOnClickListener());
    }
}
