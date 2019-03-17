package com.schoolpartime.schoolpartime.activity;

import android.os.Bundle;

import com.schoolpartime.schoolpartime.R;
import com.schoolpartime.schoolpartime.SuperActivity;
import com.schoolpartime.schoolpartime.databinding.ActivityPrefectinfoBinding;
import com.schoolpartime.schoolpartime.presenter.PrefectInfoPre;
import com.schoolpartime.schoolpartime.presenter.Presenter;

import androidx.databinding.DataBindingUtil;

public class PrefectInfoActivity extends SuperActivity {
    Presenter pre = new PrefectInfoPre();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityPrefectinfoBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_prefectinfo);
        pre.attach(binding,this);
    }

    @Override
    public void onBackPressed() {
        pre.notifyUpdate(1);
    }
}
