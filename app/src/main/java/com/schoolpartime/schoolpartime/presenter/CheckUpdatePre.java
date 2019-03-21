package com.schoolpartime.schoolpartime.presenter;

import com.schoolpartime.schoolpartime.SuperActivity;
import com.schoolpartime.schoolpartime.databinding.ActivityCheckupdateBinding;

import androidx.databinding.ViewDataBinding;

public class CheckUpdatePre implements Presenter {

    ActivityCheckupdateBinding binding;
    SuperActivity activity;

    @Override
    public void attach(ViewDataBinding binding, SuperActivity activity) {
        this.binding = (ActivityCheckupdateBinding) binding;
        this.activity = activity;
        init();

    }

    private void init() {
    }

    @Override
    public void notifyUpdate(int code) {

    }
}
