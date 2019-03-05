package com.schoolpartime.schoolpartime.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.schoolpartime.schoolpartime.R;
import com.schoolpartime.schoolpartime.SuperActivity;
import com.schoolpartime.schoolpartime.databinding.ActivityLoginBinding;
import com.schoolpartime.schoolpartime.listener.IntentOnClickListener;
import com.schoolpartime.schoolpartime.listener.ResultOnClickListener;
import com.schoolpartime.schoolpartime.listener.TextChangedListener;

import java.util.Objects;

/**
 * Created by machenike on 2018/11/5.
 * 用于兼职的登录界面
 */

public class LoginActivity extends SuperActivity {

    private ActivityLoginBinding binding;
    TextChangedListener textChangedListener =  new TextChangedListener(){
        @Override
        public void afterTextChange() {
            if(Objects.requireNonNull(binding.etLgUsername.getText()).toString().length() > 0 && Objects.requireNonNull(binding.etLgPsw.getText()).toString().length() >0){
                binding.submitLogin.setEnabled(true);
            }else{
                binding.submitLogin.setEnabled(false);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_login);
        initView();
    }

    private void initView() {
        binding.setHandler(new IntentOnClickListener());
        binding.setCallback(new ResultOnClickListener(new ResultOnClickListener.ResultCallback<String>() {
            @Override
            public void start() {
                show("正在登陆...");
            }

            @Override
            public void failed() {
                dismiss();
            }

            @Override
            public void success(String o) {
                dismiss();
                showResult(binding.rly,o);
            }
        }));
        binding.etLgUsername.addTextChangedListener(textChangedListener);
        binding.etLgPsw.addTextChangedListener(textChangedListener);
    }

    @Override
    public void onBackPressed() {
        BackExit();
    }
}
