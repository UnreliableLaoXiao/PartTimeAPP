package com.schoolpartime.schoolpartime.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;

import com.schoolpartime.schoolpartime.R;
import com.schoolpartime.schoolpartime.SuperActivity;
import com.schoolpartime.schoolpartime.databinding.ActivityNewworkBinding;
import com.schoolpartime.schoolpartime.presenter.NewWorkPre;
import com.schoolpartime.schoolpartime.presenter.Presenter;

import androidx.databinding.DataBindingUtil;

@SuppressLint("Registered")
public class NewWorkActivity extends SuperActivity {

    Presenter pre = new NewWorkPre();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityNewworkBinding binding = DataBindingUtil.setContentView(this,R.layout.activity_newwork);
        pre.attach(binding,this);
    }
}
