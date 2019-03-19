package com.schoolpartime.schoolpartime;

import android.annotation.SuppressLint;
import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import androidx.viewpager.widget.ViewPager;
import android.view.View;
import android.view.WindowManager;

import com.example.updateapp.util.FileUtil;
import com.schoolpartime.schoolpartime.activity.MainActivity;
import com.schoolpartime.schoolpartime.adapter.ViewPagerAdapter;
import com.schoolpartime.schoolpartime.databinding.ActivityWelcomeBinding;
import com.schoolpartime.schoolpartime.databinding.ActivityWelcomeOnceBinding;
import com.schoolpartime.schoolpartime.event.NetMessage;
import com.schoolpartime.schoolpartime.util.sp.SpCommonUtils;
import com.schoolpartime.schoolpartime.weiget.data.Views;

import java.util.List;


/**
 * 兼职的客户端的欢迎界面
 */

public class WelcomeActivity extends SuperActivity implements ViewPager.OnPageChangeListener {

    private boolean flag = true;
    private ActivityWelcomeOnceBinding binding_once;
    private ActivityWelcomeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        SpCommonUtils.setIsLogin(this,true);
        if (!SpCommonUtils.getOnceStart(this)) {
            binding_once = DataBindingUtil.setContentView(this, R.layout.activity_welcome_once);
            if(FileUtil.verifyStoragePermissions(this)){
                initViewsOnce(); //初始化组件
            }
        } else {
            binding = DataBindingUtil.setContentView(this, R.layout.activity_welcome);
            if(FileUtil.verifyStoragePermissions(this)){
                initViews();
            }
        }
    }

    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        @SuppressLint("SetTextI18n")
        @Override
        public void handleMessage(Message msg) {
            if (msg.what <= 2 && flag && msg.what > 0) {
                binding.setShow("跳过" + msg.what + "s");
                handler.sendEmptyMessageDelayed(msg.what - 1, 1000);
            } else if (msg.what == 0 && flag) {
                flag = false;
                (new MainActivity()).inToActivity(WelcomeActivity.this);
                finish();
            }
        }
    };


    private void initViews() {
        binding.setShow("跳过3s");
        handler.sendEmptyMessageDelayed(2, 1000);
        binding.welcomeTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handler.sendEmptyMessage(0);
            }
        });
    }

    void initViewsOnce() {
        List<View> list_views = Views.GetWelcomeViews(this, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                (new MainActivity()).inToActivity(WelcomeActivity.this);
                flag = false;
                finish();
            }
        });
        binding_once.splashViewpager.setAdapter(new ViewPagerAdapter(list_views, this)); //为viewPager设置适配器
        //同时需要监听ViewPager滑动的情况，根据滑动的状态设置导航点
        binding_once.splashViewpager.setOnPageChangeListener(this);
    }

    @Override
    public void onPageScrollStateChanged(int arg0) {}

    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {}

    //根据选定的页面状态设置导航点
    public void onPageSelected(int arg0) {
         Views.SetWelcomeDots(this,arg0);
    }
}
