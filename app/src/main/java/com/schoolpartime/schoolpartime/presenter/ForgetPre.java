package com.schoolpartime.schoolpartime.presenter;

import androidx.databinding.ViewDataBinding;
import com.google.android.material.snackbar.Snackbar;
import android.view.View;

import com.schoolpartime.schoolpartime.R;
import com.schoolpartime.schoolpartime.SuperActivity;
import com.schoolpartime.schoolpartime.databinding.ActivityForgetBinding;
import com.schoolpartime.schoolpartime.entity.baseModel.ResultModel;
import com.schoolpartime.schoolpartime.listener.TextChangedListener;
import com.schoolpartime.schoolpartime.net.interfacz.ForgetPswServer;
import com.schoolpartime.schoolpartime.net.request.HttpRequest;
import com.schoolpartime.schoolpartime.net.request.base.RequestResult;
import com.schoolpartime.schoolpartime.util.LogUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ForgetPre implements Presenter, View.OnClickListener {

    private ActivityForgetBinding binding;
    private SuperActivity activity;

    private TextChangedListener textChangedListener =  new TextChangedListener(){
        @Override
        public void afterTextChange() {
            notifyUpdate(getEditIsFill()?3:2);
        }
    };

    @Override
    public void attach(ViewDataBinding binding, SuperActivity activity) {
        this.binding = (ActivityForgetBinding) binding;
        this.activity = activity;
        init();

    }

    private void init() {
        binding.username.addTextChangedListener(textChangedListener);
        binding.pswNew.addTextChangedListener(textChangedListener);
        binding.pswVerify.addTextChangedListener(textChangedListener);
        binding.submit.setOnClickListener(this);
    }

    private boolean getEditIsFill(){
        return Objects.requireNonNull(binding.username.getText()).toString().length() > 0 && Objects.requireNonNull(binding.pswVerify.getText()).toString().length() >0
                && Objects.requireNonNull(binding.pswNew.getText()).toString().length() >0 ;
    }

    @Override
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
                binding.submit.setEnabled(false);
            }
            break;
            case 3:{
                binding.submit.setEnabled(true);
            }
            break;
            default:
                break;
        }
    }

    private void showResult(String mes) {
        Snackbar.make(binding.rly, mes, Snackbar.LENGTH_LONG)
                .setDuration(Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void onClick(View v) {
        activity.show("正在登陆...");
        HttpRequest.request(HttpRequest.builder().create(ForgetPswServer.class).forgetUser(getRequestBody()),
                new RequestResult() {
                    @Override
                    public void success(ResultModel resultModel) {
                        activity.dismiss();
                        LogUtil.d("忘记密码----------ResultModel："+resultModel.toString());
                        if (resultModel.code == 200) {
                            showResult("修改成功!即将返回登录界面");
                            activity.handler.sendEmptyMessageDelayed(1,2000);
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

    private Map<String,String> getRequestBody() {
        Map<String,String> body = new HashMap<>();
        body.put("username",Objects.requireNonNull(binding.username.getText()).toString());
        body.put("password",Objects.requireNonNull(binding.pswNew.getText()).toString());
        body.put("verifypsw",Objects.requireNonNull(binding.pswVerify.getText()).toString());
        return body;
    }
}
