package com.schoolpartime.login.view_one;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.schoolpartime.login.R;


/**
 * Created by machenike on 2018/11/5.
 * 注册账号
 */

public class RegisterActivity extends Activity implements View.OnClickListener {

//    TextView login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
    }

    private void initView() {
//        login = findViewById(R.id.tv_fg_login);
//        login.setOnClickListener(this);
    }

    public static void inToActivity(Activity activity){
        Intent intent = new Intent(activity , RegisterActivity.class);
        activity.startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.tv_rg_login) {
            LoginActivity.inToActivity(this);
            finish();
        }
    }
}
