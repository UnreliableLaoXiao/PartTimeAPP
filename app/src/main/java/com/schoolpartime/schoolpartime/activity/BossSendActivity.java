package com.schoolpartime.schoolpartime.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import com.schoolpartime.schoolpartime.R;
import com.schoolpartime.schoolpartime.SuperActivity;
import com.schoolpartime.schoolpartime.databinding.ActivityBosssendBinding;
import com.schoolpartime.schoolpartime.entity.WorkInfo;
import com.schoolpartime.schoolpartime.presenter.BossSendPre;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

@SuppressLint("Registered")
public class BossSendActivity extends SuperActivity {

    BossSendPre pre = new BossSendPre();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityBosssendBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_bosssend);
        pre.attach(binding,this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 1 && resultCode == 1) {
            WorkInfo workInfo = data.getParcelableExtra("newworkinfo");
            pre.notifyUpdate(workInfo);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
