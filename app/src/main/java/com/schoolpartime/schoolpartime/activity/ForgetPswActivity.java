package com.schoolpartime.schoolpartime.activity;

import androidx.databinding.DataBindingUtil;
import android.os.Bundle;

import com.schoolpartime.schoolpartime.R;
import com.schoolpartime.schoolpartime.SuperActivity;
import com.schoolpartime.schoolpartime.databinding.ActivityForgetBinding;
import com.schoolpartime.schoolpartime.listener.IntentOnClickListener;
import com.schoolpartime.schoolpartime.presenter.ForgetPre;


/**
 * Created by machenike on 2018/11/5.
 * 忘记密码
 */

public class ForgetPswActivity extends SuperActivity {

    private ActivityForgetBinding binding;
    private ForgetPre pre = new ForgetPre();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_forget);
        pre.attach(binding,this);
        binding.setHandler(new IntentOnClickListener());
    }
}
