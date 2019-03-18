package com.schoolpartime.schoolpartime.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;

import com.schoolpartime.schoolpartime.R;
import com.schoolpartime.schoolpartime.SuperActivity;
import com.schoolpartime.schoolpartime.databinding.ActivityDetailsBinding;
import com.schoolpartime.schoolpartime.presenter.DetailsInfoPre;
import com.schoolpartime.schoolpartime.presenter.Presenter;

import androidx.databinding.DataBindingUtil;


@SuppressLint("Registered")
public class DetailsInfoActivity extends SuperActivity {
    Presenter pre = new DetailsInfoPre();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityDetailsBinding binding = DataBindingUtil.setContentView(this,R.layout.activity_details);
        pre.attach(binding,this);
    }

}
