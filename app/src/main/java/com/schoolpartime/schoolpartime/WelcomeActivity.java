package com.schoolpartime.schoolpartime;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.updateapp.util.FileUtil;
import com.schoolpartime.login.view_one.LoginActivity;
import com.schoolpartime.schoolpartime.adapter.ViewPagerAdapter;
import com.schoolpartime.schoolpartime.contant.SpStr;
import com.schoolpartime.schoolpartime.util.SpUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

/**
 * 兼职的客户端的欢迎界面
 */

public class WelcomeActivity extends SuperActivity implements ViewPager.OnPageChangeListener {

    ViewPager viewPager;
    private List<View> views; //放置需要切换的页面
    private TextView tv;

    private boolean isOnce = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        isOnce = SpUtils.getSharedPreferencesForBoolean(this, SpStr.ONCE_START);
        SpUtils.setSharedPreferences(this,SpStr.IS_LOGIN,false);

        if (isOnce) {
            setContentView(R.layout.activity_welcome_once);
            if(FileUtil.verifyStoragePermissions(this)){
                initViewsOnce(); //初始化组件
            }
        } else {
            setContentView(R.layout.activity_welcome);
            if(FileUtil.verifyStoragePermissions(this)){
                initViews();
            }
        }
        ButterKnife.bind(this);
    }



    boolean flag = true;

    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        @SuppressLint("SetTextI18n")
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 2 && flag) {
                tv.setText("跳过" + msg.what + "s");
                handler.sendEmptyMessageDelayed(msg.what - 1, 1000);
            } else if (msg.what == 1 && flag) {
                tv.setText("跳过" + msg.what + "s");
                handler.sendEmptyMessageDelayed(msg.what - 1, 1000);
            } else if (msg.what == 0 && flag) {
                flag = false;
                LoginActivity.inToActivity(WelcomeActivity.this);
                finish();
//                MainActivity.inToActivity(WelcomeActivity.this);
            }
        }
    };


    private void initViews() {

        tv = findViewById(R.id.welcome_tv);
        handler.sendEmptyMessageDelayed(2, 1000);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handler.sendEmptyMessage(0);
            }
        });
    }

    void initViewsOnce() {
        viewPager = findViewById(R.id.splash_viewpager);
        views = GetWelcomeViews(this);
        //适配器
        ViewPagerAdapter vpAdater = new ViewPagerAdapter(views, this);
        viewPager.setAdapter(vpAdater); //为viewPager设置适配器
        //同时需要监听ViewPager滑动的情况，根据滑动的状态设置导航点
        viewPager.setOnPageChangeListener(this);
        Button enterButton = (views.get(views.size() - 1)).findViewById(R.id.enter_button);
        enterButton.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        LoginActivity.inToActivity(WelcomeActivity.this);
                        finish();
//                        MainActivity.inToActivity(WelcomeActivity.this);
                    }
                }
        );
    }

    public static List<View> GetWelcomeViews(Activity Activity){
        List<View> views = new ArrayList<>();
        LayoutInflater inflater = LayoutInflater. from(Activity);
        int[] layoutIds = new int[]{R.layout.welcome_screen_1, R.layout.welcome_screen_2, R.layout.welcome_screen_3};
        for( int i=0; i< layoutIds.length; i++){
            views.add(inflater.inflate( layoutIds[i], null));
        }
        return views;
    }

    @Override
    public void onPageScrollStateChanged(int arg0) {
    }

    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {
    }

    //根据选定的页面状态设置导航点
    public void onPageSelected(int arg0) {
        ImageView[] dots = GetWelcomeDots(this);
        //选定arg0位置的页面
        for (int i = 0; i < views.size(); i++) {
            if (i == arg0) {
                dots[i].setImageResource(R.drawable.wihte_circle);
            } else {
                dots[i].setImageResource(R.drawable.gray_circle);
            }
        }
    }

    public static ImageView[] GetWelcomeDots(Activity Activity){
        int[] dotsIds = new int[]{R.id.dot_01, R.id. dot_02, R.id.dot_03,};
        ImageView[] dots = new ImageView[ dotsIds. length];
        for( int i=0; i< dotsIds. length; i++){
            dots[i] = Activity.findViewById(dotsIds[i]);
        }
        return dots;
    }
}
