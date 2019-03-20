package com.schoolpartime.schoolpartime.activity;

import android.os.Bundle;
import com.schoolpartime.schoolpartime.R;
import com.schoolpartime.schoolpartime.SuperActivity;
import com.schoolpartime.schoolpartime.presenter.Presenter;
import com.schoolpartime.schoolpartime.presenter.SendRecordPre;
import com.schoolpartime.schoolpartime.databinding.ActivitySendrecordBinding;
import androidx.databinding.DataBindingUtil;

public class SendRecordActivity extends SuperActivity {

    Presenter pre = new SendRecordPre();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivitySendrecordBinding binding = DataBindingUtil.setContentView(this,R.layout.activity_sendrecord);
        pre.attach(binding,this);
    }
}
