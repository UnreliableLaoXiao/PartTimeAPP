package com.schoolpartime.schoolpartime.presenter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.View;
import android.widget.AdapterView;

import com.google.android.material.snackbar.Snackbar;
import com.schoolpartime.schoolpartime.R;
import com.schoolpartime.schoolpartime.SuperActivity;
import com.schoolpartime.schoolpartime.activity.AboutActivity;
import com.schoolpartime.schoolpartime.activity.CheckUpdateActivity;
import com.schoolpartime.schoolpartime.activity.LoginActivity;
import com.schoolpartime.schoolpartime.activity.ProtocolActivity;
import com.schoolpartime.schoolpartime.adapter.MySelfListAdapter;
import com.schoolpartime.schoolpartime.databinding.ActivitySettingBinding;
import com.schoolpartime.schoolpartime.entity.DataModel;
import com.schoolpartime.schoolpartime.event.LoginStateController;
import com.schoolpartime.schoolpartime.service.ServiceController;
import com.schoolpartime.schoolpartime.util.InfoUtil;
import com.schoolpartime.schoolpartime.util.LogUtil;
import com.schoolpartime.schoolpartime.util.sp.SpCommonUtils;

import java.util.ArrayList;
import java.util.List;

import androidx.databinding.ViewDataBinding;

public class SettingPre implements Presenter, View.OnClickListener , LoginStateController.NotifyLoginState, AdapterView.OnItemClickListener {

    ActivitySettingBinding binding;
    Activity activity;
    private boolean isLogin;

    @Override
    public void attach(ViewDataBinding binding, SuperActivity activity) {
        this.binding = (ActivitySettingBinding) binding;
        this.activity = activity;
        init();
    }

    private void init() {
        LoginStateController.getInstance().addNotity(this);
        List<DataModel> list = new ArrayList<>();
        binding.userChange.setText(SpCommonUtils.getIsLogin()?"退出登录":"登录");
        DataModel about = new DataModel("关于我们",R.drawable.about,0);
        DataModel checkupdate = new DataModel("检查更新",R.drawable.checkupdate,0);
        DataModel protocol = new DataModel("用户协议",R.drawable.protocol,0);
        list.add(about);
        list.add(checkupdate);
        list.add(protocol);
        binding.listUser.setAdapter(new MySelfListAdapter(list,activity));
        binding.listUser.setOnItemClickListener(this);
        binding.userChange.setOnClickListener(this);
        binding.userBack.setOnClickListener(this);
    }

    @SuppressLint("WrongConstant")
    private void showResult(String mes) {
        Snackbar.make(binding.lly, mes, Snackbar.LENGTH_LONG)
                .setDuration(Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void notifyUpdate(int code) {
        switch (code) {
            case 8:{
                LoginStateController.getInstance().removeNotity(this);
            }
            break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_back:{
                activity.finish();
            }
            break;
            case R.id.user_change:{
                if (!SpCommonUtils.getIsLogin()){
                    (new LoginActivity()).inToActivity(activity);
                }else{
                    LogUtil.d("登录测试-->退出成功");
                    showResult("退出登陆成功");
                    InfoUtil.clearAllInfo();
                    ServiceController.stopBossCheckService();
                    ServiceController.startWeiChatService();
                }
            }
            break;
        }
    }

    @Override
    public void loginStateChange(boolean state) {
        binding.userChange.setText(state?"退出登录":"登录");
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position){
            case 0:
            {
                (new AboutActivity()).inToActivity(activity);
            }
            break;
            case 1:
            {
                (new CheckUpdateActivity()).inToActivity(activity);
            }
            break;
            case 2:
            {
                (new ProtocolActivity()).inToActivity(activity);
            }
            break;
        }
    }
}
