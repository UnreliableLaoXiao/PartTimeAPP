package com.schoolpartime.schoolpartime.presenter;

import com.schoolpartime.schoolpartime.SuperActivity;
import com.schoolpartime.schoolpartime.databinding.ActivityAboutBinding;

import androidx.databinding.ViewDataBinding;

public class AboutPre implements Presenter {

    ActivityAboutBinding binding;
    SuperActivity activity;

    @Override
    public void attach(ViewDataBinding binding, SuperActivity activity) {
        this.binding = (ActivityAboutBinding) binding;
        this.activity = activity;
        init();
    }

    private void init() {
    }

    @Override
    public void notifyUpdate(int code) {

    }
}
