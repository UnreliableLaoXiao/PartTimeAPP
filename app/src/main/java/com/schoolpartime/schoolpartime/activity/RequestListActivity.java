package com.schoolpartime.schoolpartime.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;

import com.schoolpartime.schoolpartime.R;
import com.schoolpartime.schoolpartime.SuperActivity;
import com.schoolpartime.schoolpartime.databinding.ActivityDetailsBinding;
import com.schoolpartime.schoolpartime.databinding.ActivityRequestlistBinding;
import com.schoolpartime.schoolpartime.presenter.DetailsInfoPre;
import com.schoolpartime.schoolpartime.presenter.Presenter;
import com.schoolpartime.schoolpartime.presenter.RequestListPre;

import androidx.databinding.DataBindingUtil;


@SuppressLint("Registered")
public class RequestListActivity extends SuperActivity {
    Presenter pre = new RequestListPre();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityRequestlistBinding binding = DataBindingUtil.setContentView(this,R.layout.activity_requestlist);
        pre.attach(binding,this);
    }

}
