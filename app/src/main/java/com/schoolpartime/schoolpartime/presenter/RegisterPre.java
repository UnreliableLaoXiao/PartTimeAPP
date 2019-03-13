package com.schoolpartime.schoolpartime.presenter;

import androidx.databinding.ViewDataBinding;
import com.google.android.material.snackbar.Snackbar;
import android.util.Log;
import android.view.View;

import com.schoolpartime.schoolpartime.SuperActivity;
import com.schoolpartime.schoolpartime.databinding.ActivityRegisterBinding;
import com.schoolpartime.schoolpartime.entity.baseModel.ResultModel;
import com.schoolpartime.schoolpartime.listener.TextChangedListener;
import com.schoolpartime.schoolpartime.net.interfacz.CheckIsExistServer;
import com.schoolpartime.schoolpartime.net.interfacz.UserRegisterServer;
import com.schoolpartime.schoolpartime.net.request.HttpRequest;
import com.schoolpartime.schoolpartime.net.request.base.RequestResult;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class RegisterPre implements View.OnFocusChangeListener,Presenter, View.OnClickListener {

    private String TAG = "RegisterPre";

    private ActivityRegisterBinding binding;
    private SuperActivity activity;

    private TextChangedListener textChangedListener =  new TextChangedListener(){
        @Override
        public void afterTextChange() {
            notifyUpdate(getVerifyEditTextLength()?3:4);
            notifyUpdate(confirmPsw()?1:2);
            notifyUpdate(getEditIsFill()?7:8);
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
            case 1:{
                binding.tilPswConfirm.setErrorEnabled(false);
                binding.tilPswConfirm.setError("");
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
            case 7:{
                binding.submitRegister.setEnabled(true);
            }
            break;
            case 8:{
                binding.submitRegister.setEnabled(false);
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


    private void showResult(String mes) {
        Snackbar.make(binding.rly, mes, Snackbar.LENGTH_LONG)
                .setDuration(Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (!hasFocus) {
            Map<String,String> body = new HashMap<>();
            body.put("username",Objects.requireNonNull(binding.username.getText()).toString());
            HttpRequest.request(HttpRequest.builder().create(CheckIsExistServer.class).registerUser(body),
                    new RequestResult() {
                        @Override
                        public void success(ResultModel resultModel) {
                            int code = resultModel.code;
                            if (code == 400) {
                                //return code = 5
                                notifyUpdate(5);
                            }
                        }

                        @Override
                        public void fail(Throwable e) {
                            e.printStackTrace();
                        }
                    },false);

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
        Log.d(TAG, "start: ");
        activity.show("正在注册..");
        HttpRequest.request(HttpRequest.builder().create(UserRegisterServer.class).registerUser(getRequestBody()),
                new RequestResult() {
                    @Override
                    public void success(ResultModel resultModel) {
                        Log.d(TAG, "success: ");
                        activity.dismiss();
                        if (resultModel.code == 200) {
                            showResult("注册成功!即将返回登录界面");
                            activity.handler.sendEmptyMessageDelayed(1,2000);
                        } else {
                            showResult(resultModel.message);
                        }
                    }

                    @Override
                    public void fail(Throwable e) {
                        Log.d(TAG, "failed: ",e);
                        activity.dismiss();
                        showResult("请求失败");
                    }
                },false);
    }
}
