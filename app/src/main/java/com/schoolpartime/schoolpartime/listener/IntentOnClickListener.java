package com.schoolpartime.schoolpartime.listener;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.view.View;

import com.schoolpartime.schoolpartime.R;
import com.schoolpartime.schoolpartime.activity.ForgetPswActivity;
import com.schoolpartime.schoolpartime.activity.LoginActivity;
import com.schoolpartime.schoolpartime.activity.RegisterActivity;
import com.schoolpartime.schoolpartime.util.sp.SpCommonUtils;

import java.util.Objects;

public class IntentOnClickListener {

    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_lg_forget:
            {
                (new ForgetPswActivity()).inToActivity(getActivityFromView(view));
            }
            break;

            case R.id.tv_lg_register:
            {
                (new RegisterActivity()).inToActivity(getActivityFromView(view));
            }
            break;

            case R.id.tv_fg_login:
            {
                Objects.requireNonNull(getActivityFromView(view)).finish();
            }
            break;

            case R.id.tv_rg_login:
            {
                Objects.requireNonNull(getActivityFromView(view)).finish();
            }
            break;

            case R.id.go_login:
            {
                if (!SpCommonUtils.getIsLogin()){
                    (new LoginActivity()).inToActivity(getActivityFromView(view));
                }else {
                    /**
                     * 职位选择
                     */
                }
            }
            break;
        }
    }

    private Activity getActivityFromView(View view) {
        Context context = view.getContext();
        while (context instanceof ContextWrapper) {
            if (context instanceof Activity) {
                return (Activity) context;
            }
            context = ((ContextWrapper) context).getBaseContext();
        }
        return null;
    }
}
