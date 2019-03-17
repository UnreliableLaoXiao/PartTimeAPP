package com.schoolpartime.schoolpartime.presenter;

import androidx.databinding.ViewDataBinding;
import com.google.android.material.snackbar.Snackbar;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;
import com.schoolpartime.schoolpartime.SuperActivity;
import com.schoolpartime.schoolpartime.activity.PrefectInfoActivity;
import com.schoolpartime.schoolpartime.databinding.ActivityLoginBinding;
import com.schoolpartime.schoolpartime.entity.User;
import com.schoolpartime.schoolpartime.entity.baseModel.ResultModel;
import com.schoolpartime.schoolpartime.filter.RequestHeaderFilter;
import com.schoolpartime.schoolpartime.listener.TextChangedListener;
import com.schoolpartime.schoolpartime.net.interfacz.UserLoginServer;
import com.schoolpartime.schoolpartime.net.request.HttpRequest;
import com.schoolpartime.schoolpartime.net.request.base.RequestResult;
import com.schoolpartime.schoolpartime.util.sp.SpCommonUtils;
import com.schoolpartime.security.aes.AESUtil;

import java.util.Objects;

public class LoginPre implements Presenter, View.OnClickListener {

    private String TAG = "LoginPre";

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
            case 1:{
                binding.submitLogin.setEnabled(true);
            }
            break;
            case 2:{
                binding.submitLogin.setEnabled(false);
            }
            default:
                break;
        }
    }

    private TextChangedListener textChangedListener =  new TextChangedListener(){
        @Override
        public void afterTextChange() {
            notifyUpdate(getEditIsFill()?1:2);
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
                        Log.d(TAG, "success: ");
                        activity.dismiss();
                        if (resultModel.code == 200) {
                            User user = (User) resultModel.data;
                            SpCommonUtils.setIsLogin(activity,true);
                            SpCommonUtils.setUserId(activity,user.getId());
                            if (user.getType() == 0){
                                showResult("欢迎你："+user.getUsername());
                                (new PrefectInfoActivity()).inToActivity(activity);
                            }else {
                                showResult("欢迎回来："+user.getUsername());
                            }
                            activity.finish();
                        } else {
                            showResult(resultModel.message);
                        }
                    }

                    @Override
                    public void fail(Throwable e) {
                        e.printStackTrace();
                        Log.d(TAG, "failed: ",e);
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
