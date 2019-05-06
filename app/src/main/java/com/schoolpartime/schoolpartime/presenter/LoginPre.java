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
import com.schoolpartime.schoolpartime.event.LoginStateController;
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
import com.schoolpartime.schoolpartime.service.ServiceController;
import com.schoolpartime.schoolpartime.util.LogUtil;
import com.schoolpartime.schoolpartime.util.sp.SpCommonUtils;
import com.schoolpartime.security.aes.AESUtil;

import java.util.Objects;

/**
 * 登录时操作的页面
 */
public class LoginPre implements Presenter, View.OnClickListener {
    private ActivityLoginBinding binding;
    private SuperActivity activity;


    @Override
    public void attach(ViewDataBinding binding, SuperActivity activity) {
        this.binding = (ActivityLoginBinding) binding;
        this.activity = activity;
        init();
    }

    /**
     * 设置监听
     */
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

    /**
     * 设置相关的监听事件，用来控制用户的输入完成情况
     */
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

    /**
     *
     * 登录事件：
     * 采用的加密方式如 folder 文件中的图示
     * @param v
     */
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

                            /**
                             * 此时进行身份判断：
                             * 0  代表已注册账号  但是未完善信息
                             * 1  代表已完善信息  但是未注册商家
                             * 3  代表已注册商家  但是还未通过审核
                             * 4  代表已经审核通过
                             */
                            if (user.getType() == 0){
                                (new PrefectInfoActivity()).inToActivity(activity);
                            }else {
                                SpCommonUtils.setIsLogin(true);
                                ServiceController.startWeiChatService();
                                LoginStateController.getInstance().NotifyAll(true);
                            }

                            /**
                             * 当用户未审核通过时，开始定时检测，确认通过
                             */
                            if (user.getType() == 4){
                                ServiceController.startBossCheckService();
                            }
                            activity.finish();
                        } else {
                            showResult("账号或密码错误");
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
