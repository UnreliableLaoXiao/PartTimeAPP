package com.schoolpartime.schoolpartime.presenter;

import androidx.databinding.ViewDataBinding;
import com.google.android.material.snackbar.Snackbar;

import android.annotation.SuppressLint;
import android.view.View;

import com.schoolpartime.schoolpartime.R;
import com.schoolpartime.schoolpartime.SuperActivity;
import com.schoolpartime.schoolpartime.databinding.ActivityRegisterBinding;
import com.schoolpartime.schoolpartime.entity.baseModel.ResultModel;
import com.schoolpartime.schoolpartime.filter.RequestHeaderFilter;
import com.schoolpartime.schoolpartime.listener.TextChangedListener;
import com.schoolpartime.schoolpartime.net.interfacz.CheckIsExistServer;
import com.schoolpartime.schoolpartime.net.interfacz.UserRegisterServer;
import com.schoolpartime.schoolpartime.net.request.HttpRequest;
import com.schoolpartime.schoolpartime.net.request.base.RequestResult;
import com.schoolpartime.schoolpartime.util.LogUtil;
import com.schoolpartime.security.aes.AESUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class RegisterPre implements View.OnFocusChangeListener,Presenter, View.OnClickListener {

    private ActivityRegisterBinding binding;
    private SuperActivity activity;

    private TextChangedListener textChangedListener =  new TextChangedListener(){
        @Override
        public void afterTextChange() {
            notifyUpdate(getVerifyEditTextLength()?3:4);
            notifyUpdate(confirmPsw()?7:2);
            binding.submitRegister.setEnabled(getEditIsFill());
        }
    };

    public void attach(ViewDataBinding binding, SuperActivity activity) {
        this.binding = (ActivityRegisterBinding) binding;
        this.activity = activity;
        init();
    }

    private void init() {
        binding.username.addTextChangedListener(textChangedListener);
        binding.psw.addTextChangedListener(textChangedListener);
        binding.pswConfirm.addTextChangedListener(textChangedListener);
        binding.pswVerify.addTextChangedListener(textChangedListener);
        binding.username.setOnFocusChangeListener(this);
        binding.submitRegister.setOnClickListener(this);
    }

    public void notifyUpdate(int code) {
        switch (code) {
            case 0:{
                showResult(activity.getResources().getString(R.string.net_disconnect));
            }
            break;
            case 1:{
//                showResult("当前网络已连接");
            }
            break;
            case 2:{
                binding.tilPswConfirm.setErrorEnabled(true);
                binding.tilPswConfirm.setError("两次密码不一致");
            }
            break;
            case 3:{
                binding.tilVerifyPsw.setErrorEnabled(true);
                binding.tilVerifyPsw.setError("请记住你的验证密码，将用于密码找回功能");
            }
            break;
            case 4:{
                binding.tilVerifyPsw.setErrorEnabled(false);
                binding.tilVerifyPsw.setError("");
            }
            break;
            case 5:{
                binding.tilUsername.setErrorEnabled(true);
                binding.tilUsername.setError("该账号已存在");
            }
            break;
            case 6:{
                binding.tilUsername.setErrorEnabled(false);
                binding.tilUsername.setError("");
            }
            break;
            case 7:{
                binding.tilPswConfirm.setErrorEnabled(false);
                binding.tilPswConfirm.setError("");
            }
            break;
            default:
                break;
        }
    }

    private boolean getEditIsFill(){
        return Objects.requireNonNull(binding.username.getText()).toString().length() > 0 && Objects.requireNonNull(binding.psw.getText()).toString().length() >0 &&
                Objects.requireNonNull(binding.pswConfirm.getText()).toString().length() > 0 && Objects.requireNonNull(binding.pswVerify.getText()).toString().length() >0;
    }

    private boolean getVerifyEditTextLength(){
        return Objects.requireNonNull(binding.pswVerify.getText()).toString().length()>0;
    }

    private boolean confirmPsw(){
        return Objects.requireNonNull(binding.psw.getText()).toString().equals(Objects.requireNonNull(binding.pswConfirm.getText()).toString()) ;
    }


    @SuppressLint("WrongConstant")
    private void showResult(String mes) {
        Snackbar.make(binding.rly, mes, Snackbar.LENGTH_LONG)
                .setDuration(Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (!hasFocus) {
            String strKeyAES = null;
            try {
                strKeyAES = AESUtil.getStrKeyAES();
            } catch (Exception e) {
                e.printStackTrace();
            }
            String body = Objects.requireNonNull(binding.username.getText()).toString();
            HttpRequest.request(HttpRequest.builder().create(CheckIsExistServer.class)
                            .registerUser(RequestHeaderFilter.getSecurityBody(body,strKeyAES),
                                    RequestHeaderFilter.getSignature(body),
                                    RequestHeaderFilter.getAESKeySecurity(strKeyAES),
                                    System.currentTimeMillis()+""),
                    new RequestResult() {
                        @Override
                        public void success(ResultModel resultModel) {
                            LogUtil.d("账号检测----------ResultModel："+resultModel.toString());
                            int code = resultModel.code;
                            if (code == 400) {
                                notifyUpdate(5);
                            }
                        }

                        @Override
                        public void fail(Throwable e) {
                        }
                    },true);

        }else {
            //return code = 6
            notifyUpdate(6);
        }
    }

    private Map<String,String> getRequestBody() {
        Map<String,String> body = new HashMap<>();
        body.put("username",Objects.requireNonNull(binding.username.getText()).toString());
        body.put("verifypsw",Objects.requireNonNull(binding.pswVerify.getText()).toString());
        body.put("password",Objects.requireNonNull(binding.psw.getText()).toString());
        return body;
    }

    @Override
    public void onClick(View v) {
        activity.show("正在注册..");
        HttpRequest.request(HttpRequest.builder().create(UserRegisterServer.class).registerUser(getRequestBody()),
                new RequestResult() {
                    @Override
                    public void success(ResultModel resultModel) {
                        activity.dismiss();
                        LogUtil.d("注册账号----------ResultModel："+resultModel.toString());
                        if (resultModel.code == 200) {
                            showResult("注册成功!即将返回登录界面");
                            activity.handler.sendEmptyMessageDelayed(1,2000);
                        } else {
                            showResult(resultModel.message);
                        }
                    }

                    @Override
                    public void fail(Throwable e) {
                        activity.dismiss();
                        showResult("请求失败");
                    }
                },false);
    }
}
