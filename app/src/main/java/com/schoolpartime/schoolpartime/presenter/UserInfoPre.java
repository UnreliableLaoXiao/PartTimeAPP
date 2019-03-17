package com.schoolpartime.schoolpartime.presenter;

import android.app.Activity;
import android.view.View;
import android.widget.AdapterView;

import com.schoolpartime.schoolpartime.R;
import com.schoolpartime.schoolpartime.SuperActivity;
import com.schoolpartime.schoolpartime.databinding.ActivityUserinfoBinding;

import java.util.ArrayList;

import androidx.databinding.ViewDataBinding;

public class UserInfoPre implements Presenter, View.OnClickListener {

    Activity activity;
    ActivityUserinfoBinding binding;
    boolean isChange = false;

    @Override
    public void attach(ViewDataBinding binding, SuperActivity activity) {
        this.binding = (ActivityUserinfoBinding) binding;
        this.activity = activity;
        init();
    }

    private void init() {
        ArrayList<Integer> arr_age = new ArrayList();
        for (int i = 16; i < 60; i++) {
            arr_age.add(i);
        }
        binding.userAge.attachDataSource(arr_age);
        binding.userAge.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayList<String> arr_sex = new ArrayList();
        arr_sex.add("男");
        arr_sex.add("女");

        binding.userSex.attachDataSource(arr_sex);
        binding.userSex.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        binding.userBack.setOnClickListener(this);
        binding.userChange.setOnClickListener(this);
    }

    @Override
    public void notifyUpdate(int code) {

        switch (code) {
            case 0:
            {

            }
            break;
            case 1:
            {

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
                ChangeWeigetEnable(!isChange);
            }
            break;
        }
    }

    private void ChangeWeigetEnable(boolean flag) {
        binding.userName.setEnabled(flag);
        binding.userAge.setEnabled(flag);
        binding.userAddress.setEnabled(flag);
        binding.userSex.setEnabled(flag);
        binding.userPhone.setEnabled(flag);
        isChange = flag;
    }
}
