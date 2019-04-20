package com.schoolpartime.schoolpartime.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import com.schoolpartime.schoolpartime.R;
import com.schoolpartime.schoolpartime.SuperActivity;
import com.schoolpartime.schoolpartime.databinding.ActivityMyLoveTypeBinding;
import com.schoolpartime.schoolpartime.presenter.MyLoveTypePre;
import com.schoolpartime.schoolpartime.presenter.Presenter;

import androidx.databinding.DataBindingUtil;


@SuppressLint("Registered")
public class MyLoveTypeActivity extends SuperActivity {

    private Presenter pre = new MyLoveTypePre();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMyLoveTypeBinding binding =  DataBindingUtil.setContentView(this,R.layout.activity_my_love_type);
        pre.attach(binding,this);
    }
}
