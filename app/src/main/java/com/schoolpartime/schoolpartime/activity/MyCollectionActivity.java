package com.schoolpartime.schoolpartime.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import com.schoolpartime.schoolpartime.R;
import com.schoolpartime.schoolpartime.SuperActivity;
import com.schoolpartime.schoolpartime.databinding.ActivityCollectionBinding;
import com.schoolpartime.schoolpartime.event.NetMessage;
import com.schoolpartime.schoolpartime.presenter.MyCollectionPre;
import com.schoolpartime.schoolpartime.presenter.Presenter;

import androidx.databinding.DataBindingUtil;

@SuppressLint("Registered")
public class MyCollectionActivity extends SuperActivity {

    Presenter pre = new MyCollectionPre();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCollectionBinding binding = DataBindingUtil.setContentView(this,R.layout.activity_collection);
        pre.attach(binding,this);
    }
}
