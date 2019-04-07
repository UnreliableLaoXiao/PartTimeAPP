package com.schoolpartime.schoolpartime.presenter;

import androidx.databinding.ViewDataBinding;
import com.google.android.material.snackbar.Snackbar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.View;

import com.google.gson.Gson;
import com.schoolpartime.schoolpartime.R;
import com.schoolpartime.schoolpartime.SuperActivity;
import com.schoolpartime.schoolpartime.activity.PrefectInfoActivity;
import com.schoolpartime.schoolpartime.service.BossTestService;
import com.schoolpartime.schoolpartime.service.ChatMessageService;
import com.schoolpartime.schoolpartime.databinding.ActivityLoginBinding;
import com.schoolpartime.schoolpartime.entity.User;
import com.schoolpartime.schoolpartime.entity.baseModel.ResultModel;
import com.schoolpartime.schoolpartime.filter.RequestHeaderFilter;
import com.schoolpartime.schoolpartime.listener.TextChangedListener;
import com.schoolpartime.schoolpartime.net.interfacz.UserLoginServer;
import com.schoolpartime.schoolpartime.net.request.HttpRequest;
import com.schoolpartime.schoolpartime.net.request.base.RequestResult;
import com.schoolpartime.schoolpartime.util.LogUtil;
import com.schoolpartime.schoolpartime.util.sp.SpCommonUtils;
import com.schoolpartime.security.aes.AESUtil;

import java.util.Objects;

public class LoginPre implements Presenter, View.OnClickListener {
    private ActivityLoginBinding binding;
    private SuperActivity activity;


    @Override
    public void attach(ViewDataBinding binding, SuperActivity activity) {
        this.binding = (ActivityLoginBinding) binding;
        this.activity = activity;
        init();
    }

    private void init() {
        binding.etLgUsername.addTextChangedListener(textChangedListener);
        binding.etLgPsw.addTextChangedListener(textChangedListener);
        binding.submitLogin.setOnClickListener(this);
    }

    @Override
    public void notifyUpdate(int code) {
        switch (code) {
            case 0:{
                showResult(activity.getResources().getString(R.string.net_disconnect));
            }
            break;
            case 1:{
//                网络连接成功
            }
            break;
            case 2:{
                binding.submitLogin.setEnabled(false);
            }
            break;
            case 3:{
                binding.submitLogin.setEnabled(true);
            }
            default:
                break;
        }
    }

    private TextChangedListener textChangedListener =  new TextChangedListener(){
        @Override
        public void afterTextChange() {
            notifyUpdate(getEditIsFill()?3:2);
        }
    };

    private boolean getEditIsFill(){
        return Objects.requireNonNull(binding.etLgUsername.getText()).toString().length() > 0 && Objects.requireNonNull(binding.etLgPsw.getText()).toString().length() >0;
    }

    @SuppressLint("WrongConstant")
    private void showResult(String mes) {
        Snackbar.make(binding.rly, mes, Snackbar.LENGTH_LONG)
                .setDuration(Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void onClick(View v) {

        String strKeyAES = null;

        activity.show("正在登陆...");
        try {
            strKeyAES = AESUtil.getStrKeyAES();
        } catch (Exception e) {
            e.printStackTrace();
        }
        String body = getRequestBody();
        HttpRequest.request(HttpRequest.builder().create(UserLoginServer.class).
                        registerUser(RequestHeaderFilter.getSecurityBody(body,strKeyAES),
                                RequestHeaderFilter.getSignature(body),
                                RequestHeaderFilter.getAESKeySecurity(strKeyAES),
                                System.currentTimeMillis()+""),
                new RequestResult() {
                    @Override
                    public void success(ResultModel resultModel) {
                        activity.dismiss();
                        LogUtil.d("用户登录----------ResultModel："+resultModel.toString());
                        if (resultModel.code == 200) {
                            User user = (User) resultModel.data;
                            SpCommonUtils.setUserId(user.getId());
                            SpCommonUtils.setUserType(user.getType());
                            if (user.getType() == 0){
                                (new PrefectInfoActivity()).inToActivity(activity);
                            }else {
                                SpCommonUtils.setIsLogin(true);
                                Intent intent = new Intent();
                                intent.setClass(activity, ChatMessageService.class);
                                activity.startService(intent);
                                LogUtil.d("开启聊天服务");
                            }

                            if (user.getType() != 3){
                                Intent intent = new Intent();
                                intent.setClass(activity, BossTestService.class);
                                activity.startService(intent);
                            }
                            activity.finish();
                        } else {
                            showResult(resultModel.message);
                        }
                    }

                    @Override
                    public void fail(Throwable e) {
                        e.printStackTrace();
                        activity.dismiss();
                        showResult("请求失败");
                    }
                },true);
    }

    private String getRequestBody() {
        User user = new User(0,Objects.requireNonNull(binding.etLgUsername.getText()).toString(),Objects.requireNonNull(binding.etLgPsw.getText()).toString(),"1234",0);
        return new Gson().toJson(user);
    }
}
