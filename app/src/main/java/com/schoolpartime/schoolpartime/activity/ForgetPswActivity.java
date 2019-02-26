package com.schoolpartime.schoolpartime.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.schoolpartime.schoolpartime.R;
import com.schoolpartime.schoolpartime.SuperActivity;


/**
 * Created by machenike on 2018/11/5.
 * 忘记密码
 */

public class ForgetPswActivity extends SuperActivity implements View.OnClickListener {

    TextView login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget);
        initView();
    }

    private void initView() {
        login = findViewById(R.id.tv_fg_login);
        login.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.tv_fg_login) {
            LoginActivity.inToActivity(this);
            finish();

        }
    }

    public static void inToActivity(Activity activity){
        Intent intent = new Intent(activity , ForgetPswActivity.class);
        activity.startActivity(intent);
    }
}
