package com.schoolpartime.schoolpartime.presenter;

import com.schoolpartime.schoolpartime.SuperActivity;
import com.schoolpartime.schoolpartime.databinding.ActivityProtocolBinding;

import androidx.databinding.ViewDataBinding;

public class ProtocolPre implements Presenter {

    SuperActivity activity;
    ActivityProtocolBinding binding;

    @Override
    public void attach(ViewDataBinding binding, SuperActivity activity) {

        this.binding = (ActivityProtocolBinding) binding;
        this.activity = activity;
        init();
    }

    private void init() {
    }

    @Override
    public void notifyUpdate(int code) {

    }
}
