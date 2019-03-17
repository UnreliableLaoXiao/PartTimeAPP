package com.schoolpartime.schoolpartime.presenter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.schoolpartime.dao.entity.UserInfo;
import com.schoolpartime.schoolpartime.R;
import com.schoolpartime.schoolpartime.SchoolPartimeApplication;
import com.schoolpartime.schoolpartime.SuperActivity;
import com.schoolpartime.schoolpartime.activity.LoginActivity;
import com.schoolpartime.schoolpartime.activity.MainActivity;
import com.schoolpartime.schoolpartime.activity.PrefectInfoActivity;
import com.schoolpartime.schoolpartime.databinding.ActivityPrefectinfoBinding;
import com.schoolpartime.schoolpartime.entity.User;
import com.schoolpartime.schoolpartime.entity.baseModel.ResultModel;
import com.schoolpartime.schoolpartime.listener.TextChangedListener;
import com.schoolpartime.schoolpartime.net.interfacz.PrefectInfoServer;
import com.schoolpartime.schoolpartime.net.request.HttpRequest;
import com.schoolpartime.schoolpartime.net.request.base.RequestResult;
import com.schoolpartime.schoolpartime.util.sp.SpCommonUtils;

import java.util.ArrayList;
import java.util.List;

import androidx.databinding.ViewDataBinding;

public class PrefectInfoPre implements Presenter, View.OnClickListener {

    private static String TAG = "PrefectInfoPre";

    ActivityPrefectinfoBinding binding;
    SuperActivity activity;
    UserInfo info = new UserInfo();

    private TextChangedListener textChangedListener =  new TextChangedListener(){
        @Override
        public void afterTextChange() {
            binding.submitRegister.setEnabled(isPrefectInfo());
        }
    };

    @Override
    public void attach(ViewDataBinding binding, SuperActivity activity) {
        this.binding = (ActivityPrefectinfoBinding) binding;
        this.activity = activity;
        init();

    }

    private void init() {
        ArrayList<Integer> arr_age = new ArrayList();
        for (int i = 18; i < 60; i++) {
            arr_age.add(i);
        }

        info.setUserage(18);
        binding.userAge.attachDataSource(arr_age);
        binding.userAge.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                info.setUserage(position + 18);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                binding.userAge.setSelectedIndex(0);
            }
        });

        final ArrayList<String> arr_sex = new ArrayList();
        arr_sex.add("男");
        arr_sex.add("女");

        info.setUsersex(arr_sex.get(0));

        binding.userSex.attachDataSource(arr_sex);
        binding.userSex.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                info.setUsersex(arr_sex.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                binding.userSex.setSelectedIndex(0);
            }
        });
        binding.submitRegister.setOnClickListener(this);
        binding.userBack.setOnClickListener(this);
        binding.userName.addTextChangedListener(textChangedListener);
        binding.userPhone.addTextChangedListener(textChangedListener);
        binding.userAddress.addTextChangedListener(textChangedListener);
    }

    private boolean isPrefectInfo(){
        return binding.userName.getText().toString().length() > 0 && binding.userAddress.getText().toString().length() > 0 && binding.userPhone.getText().toString().length() >0 ;
    }

    @Override
    public void notifyUpdate(int code) {
        switch (code){
            case 1:{
                back();
            }
            break;
        }

    }

    @SuppressLint("WrongConstant")
    private void showResult(String mes) {
        Snackbar.make(binding.lly, mes, Snackbar.LENGTH_LONG)
                .setDuration(Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_back:{
                back();
            }
            break;
            case R.id.submit_register:{
                completed();
            }
            break;
        }
    }




    private void back() {
        SpCommonUtils.setIsLogin(activity,false);
        (new LoginActivity()).inToActivity(activity);
        activity.finish();
    }

    private void completed() {
        activity.show("正在前往挣钱的路上...");
        HttpRequest.request(HttpRequest.builder().create(PrefectInfoServer.class).
                        prefectinfo(getData()),
                new RequestResult() {
                    @Override
                    public void success(ResultModel resultModel) {
                        Log.d(TAG, "success: ");
                        activity.dismiss();
                        if (resultModel.code == 200) {
                            UserInfo userInfo = (UserInfo) resultModel.data;
                            Log.d(TAG, "success: "+userInfo.toString());
                            SchoolPartimeApplication.getmDaoSession().getUserInfoDao().insert(userInfo);
                            List<UserInfo> userInfos = SchoolPartimeApplication.getmDaoSession().getUserInfoDao().loadAll();
                            Log.d(TAG, "success Size: "+userInfos.size());
                            (new MainActivity()).inToActivity(activity);
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

    private String getData() {
        info.setId(SpCommonUtils.getUserId(activity));
        info.setUsername(binding.userName.getText().toString());
        info.setAddress(binding.userAddress.getText().toString());
        info.setPhonenumber(binding.userPhone.getText().toString());
        Gson gson = new Gson();
        return gson.toJson(info);
    }


}
