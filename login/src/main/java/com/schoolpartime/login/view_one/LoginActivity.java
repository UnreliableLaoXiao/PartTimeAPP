package com.schoolpartime.login.view_one;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.schoolpartime.login.R;
import com.schoolpartime.login.weight.ClearEditText;


/**
 * Created by machenike on 2018/11/5.
 * 用于兼职的登录界面
 */

public class LoginActivity extends Activity implements TextWatcher, View.OnClickListener {

    ClearEditText et_lg_username;
    ClearEditText et_lg_psw;
    Button bt_lg_submit;
    TextView tv_lg_forget;
    TextView tv_lg_register;
    RelativeLayout rly;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
    }

    private void initView() {
        et_lg_psw = findViewById(R.id.et_lg_psw);
        et_lg_username = findViewById(R.id.et_lg_username);
        bt_lg_submit = findViewById(R.id.bt_lg_submit);
        tv_lg_forget = findViewById(R.id.tv_lg_forget);
        tv_lg_register = findViewById(R.id.tv_lg_register);
        rly = findViewById(R.id.rly);
        et_lg_username.addTextChangedListener(this);
        et_lg_psw.addTextChangedListener(this);
        tv_lg_forget.setOnClickListener(this);
        tv_lg_register.setOnClickListener(this);
        bt_lg_submit.setOnClickListener(this);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if(et_lg_username.getText().toString().length() > 0 && et_lg_psw.getText().toString().length() >0){
            bt_lg_submit.setEnabled(true);
        }else{
            bt_lg_submit.setEnabled(false);
        }
    }


    @Override
    public void onClick(View v) {

        int i = v.getId();// identifier为用户名，userSig 为用户登录凭证
        if (i == R.id.tv_lg_forget) {
            ForgetPswActivity.inToActivity(this);

        } else if (i == R.id.tv_lg_register) {
            RegisterActivity.inToActivity(this);

        } else if (i == R.id.bt_lg_submit) {

        }
    }

    public static void inToActivity(Activity activity){
        Intent intent = new Intent(activity , LoginActivity.class);
        activity.startActivity(intent);
    }
}
