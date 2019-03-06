package com.schoolpartime.schoolpartime.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.util.Log;

import com.schoolpartime.schoolpartime.R;
import com.schoolpartime.schoolpartime.SuperActivity;
import com.schoolpartime.schoolpartime.databinding.ActivityLoginBinding;
import com.schoolpartime.schoolpartime.entity.User;
import com.schoolpartime.schoolpartime.listener.IntentOnClickListener;
import com.schoolpartime.schoolpartime.listener.ResultOnClickListener;
import com.schoolpartime.schoolpartime.listener.TextChangedListener;
import com.schoolpartime.schoolpartime.net.base.RequestBase;
import com.schoolpartime.schoolpartime.net.interfacz.UserRegisterServer;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by machenike on 2018/11/5.
 * 用于兼职的登录界面
 */

public class LoginActivity extends SuperActivity {

    private final String TAG = "LoginActivity";

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
        binding.setCallback(new ResultOnClickListener(new ResultOnClickListener.ResultCallback<User>() {
            @Override
            public Observable start() {
                Log.d(TAG, "start: ");
                show("正在登陆...");
                return RequestBase.Builder("http://192.168.124.7:8080")
                        .create(UserRegisterServer.class)
                        .registerUser(getRequestBody())
                        .subscribeOn(Schedulers.io())//IO线程加载数据
                        .observeOn(AndroidSchedulers.mainThread());//主线程显示数据;
            }

            @Override
            public void failed() {
                Log.d(TAG, "failed: ");
                dismiss();
                showResult(binding.rly,"请求失败");
            }

            @Override
            public void success(User user) {
                Log.d(TAG, "success: ");
                dismiss();
                showResult(binding.rly,user.getUsername());
            }
        }));
        binding.etLgUsername.addTextChangedListener(textChangedListener);
        binding.etLgPsw.addTextChangedListener(textChangedListener);
    }

    private Map<String,String> getRequestBody() {
        Map<String,String> body = new HashMap<>();
        body.put("username",binding.etLgUsername.getText().toString());
        body.put("token","哈哈  测试成功了");
        body.put("password",binding.etLgPsw.getText().toString());
        return body;
    }

    @Override
    public void onBackPressed() {
        BackExit();
    }
}
