package com.schoolpartime.schoolpartime.presenter;

import androidx.databinding.ViewDataBinding;

import com.schoolpartime.schoolpartime.SuperActivity;

public interface Presenter {
    void attach(ViewDataBinding binding, SuperActivity activity);
    void notifyUpdate(int code);
}
