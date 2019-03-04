package com.schoolpartime.schoolpartime.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.schoolpartime.schoolpartime.R;
import com.schoolpartime.schoolpartime.SuperActivity;
import com.schoolpartime.schoolpartime.databinding.ActivityRegisterBinding;
import com.schoolpartime.schoolpartime.listener.IntentOnClickListener;
import com.schoolpartime.schoolpartime.listener.TextChangedListener;

import java.util.Objects;


/**
 * Created by machenike on 2018/11/5.
 * 注册账号
 */

public class RegisterActivity extends SuperActivity {

    private ActivityRegisterBinding binding;
    TextChangedListener textChangedListener =  new TextChangedListener(){
        @Override
        public void afterTextChange() {
            if(Objects.requireNonNull(binding.username.getText()).toString().length() > 0 && Objects.requireNonNull(binding.psw.getText()).toString().length() >0 &&
                    Objects.requireNonNull(binding.pswConfirm.getText()).toString().length() > 0 && Objects.requireNonNull(binding.pswVerify.getText()).toString().length() >0){
                binding.submit.setEnabled(true);
            }else{
                binding.submit.setEnabled(false);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_register);
        initView();
    }

    private void initView() {
        binding.setHandler(new IntentOnClickListener());
        binding.username.addTextChangedListener(textChangedListener);
        binding.psw.addTextChangedListener(textChangedListener);
        binding.pswConfirm.addTextChangedListener(textChangedListener);
        binding.pswVerify.addTextChangedListener(textChangedListener);
    }
}
