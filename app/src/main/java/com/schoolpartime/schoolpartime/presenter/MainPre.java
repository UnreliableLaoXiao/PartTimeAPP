package com.schoolpartime.schoolpartime.presenter;

import androidx.databinding.ViewDataBinding;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.View;
import android.widget.RadioGroup;

import com.google.android.material.snackbar.Snackbar;
import com.schoolpartime.dao.entity.UserInfo;
import com.schoolpartime.schoolpartime.R;
import com.schoolpartime.schoolpartime.SchoolPartimeApplication;
import com.schoolpartime.schoolpartime.SuperActivity;
import com.schoolpartime.schoolpartime.activity.MainActivity;
import com.schoolpartime.schoolpartime.event.LoginStateController;
import com.schoolpartime.schoolpartime.event.NumberController;
import com.schoolpartime.schoolpartime.chat.WebClient;
import com.schoolpartime.schoolpartime.databinding.ActivityMianBinding;
import com.schoolpartime.schoolpartime.entity.baseModel.ResultModel;
import com.schoolpartime.schoolpartime.fragment.MainFragment;
import com.schoolpartime.schoolpartime.net.interfacz.UserInfoServer;
import com.schoolpartime.schoolpartime.net.request.HttpRequest;
import com.schoolpartime.schoolpartime.net.request.base.RequestResult;
import com.schoolpartime.schoolpartime.util.LogUtil;
import com.schoolpartime.schoolpartime.util.sp.SpCommonUtils;

import java.util.ArrayList;

public class MainPre implements Presenter, View.OnClickListener, RadioGroup.OnCheckedChangeListener,WebClient.NotifyMessage,NumberController.NotifyNumber {

    private ActivityMianBinding binding;
    private SuperActivity activity;
    private FragmentManager mFm;
    private ArrayList<Fragment> mFragmentList = new ArrayList<>();
    private String[] mFragmentTagList = {"主页", "搜索", "我的"};
    private Fragment mCurrentFragmen = null; // 记录当前显示的Fragment
    private int index = 0;
    private boolean isFirst = true;
    private int number= 0 ;

    WebClient webClient;
    NumberController controller;

    @Override
    public void attach(ViewDataBinding binding, SuperActivity activity) {
        this.binding = (ActivityMianBinding)binding;
        this.activity = activity;
        init();
    }

    private void init() {
        mFragmentList = MainActivity.getFragmentList();
        setDraws();
        binding.badge.setVisibility(View.GONE);
        binding.mainToobar.setTitle(mFragmentTagList[0]);
        activity.setSupportActionBar(binding.mainToobar);
        binding.mainToobar.setOnClickListener(this);
        mCurrentFragmen = mFragmentList.get(0);
        Log.d("TEST", "Click1: "+mCurrentFragmen);
        // 初始化首次进入时的Fragment
        mFm = activity.getSupportFragmentManager();
        FragmentTransaction transaction = mFm.beginTransaction();
        transaction.add(R.id.fl_show, mCurrentFragmen, mFragmentTagList[0]);
        transaction.commitAllowingStateLoss();
        binding.mainGp.setOnCheckedChangeListener(this);
        binding.netBar.setOnClickListener(this);
    }

    @Override
    public void notifyUpdate(int code) {
        switch (code) {
            case 0:
            case 1:{
                binding.netBar.setVisibility(code);
            }
            break;
            case 7:
            {
                if (SpCommonUtils.getIsLogin() && isFirst){
                    webClient = WebClient.getInstance();
                    controller = NumberController.getInstance();
                    webClient.addNotity(this);
                    controller.addNotity(this);
                    getUserInfo();
                    isFirst = false;
                }
            }
            break;
            case 8:
            {
                webClient.removeNotity(this);
                controller.removeNotity(this);
            }
            break;
        }

    }

    @SuppressLint("WrongConstant")
    private void showResult(String mes) {
        Snackbar.make(binding.rly, mes, Snackbar.LENGTH_LONG)
                .setDuration(Snackbar.LENGTH_LONG).show();
    }


    private void getUserInfo() {
        activity.show("正在加载,请稍后...");
        HttpRequest.request(HttpRequest.builder().create(UserInfoServer.class).
                        getUserInfoServer(SpCommonUtils.getUserId()),
                new RequestResult() {
                    @Override
                    public void success(ResultModel resultModel) {
                        LogUtil.d("得到登录用户信息----------ResultModel："+resultModel.toString());
                        if (resultModel.code == 200) {
                            UserInfo userInfo = (UserInfo) resultModel.data;
                            SchoolPartimeApplication.getmDaoSession().getUserInfoDao().insert(userInfo);
                            LogUtil.d("数据库加入userinfo信息----------成功："+userInfo.toString());
                            activity.dismiss();
                        } else {
                            showResult(resultModel.message);
                            SpCommonUtils.setIsLogin(false);
                            activity.dismiss();
                        }
                    }

                    @Override
                    public void fail(Throwable e) {
                        LogUtil.d("得到登录用户信息失败---》请求失败",e);
                        SpCommonUtils.setIsLogin(false);
                        activity.dismiss();
                        showResult("请求失败");
                    }
                },true);
    }

    private void setDraws() {
        Drawable drawable_home = activity.getResources().getDrawable(R.drawable.main_radio_type_home);
        //当这个图片被绘制时，给他绑定一个矩形 ltrb规定这个矩形
        drawable_home.setBounds(0, 0, 60, 60);
        //设置图片在文字的哪个方向
        binding.mainRbHome.setCompoundDrawables(null, drawable_home, null, null);

        Drawable drawable_finds = activity.getResources().getDrawable(R.drawable.main_radio_type_find);
        //当这个图片被绘制时，给他绑定一个矩形 ltrb规定这个矩形
        drawable_finds.setBounds(0, 0, 60, 60);
        //设置图片在文字的哪个方向
        binding.mainRbFind.setCompoundDrawables(null, drawable_finds, null, null);

        Drawable drawable_news = activity.getResources().getDrawable(R.drawable.main_radio_type_message);
        //当这个图片被绘制时，给他绑定一个矩形 ltrb规定这个矩形
        drawable_news.setBounds(0, 0, 60, 60);
        //设置图片在文字的哪个方向
        binding.mainRbMessage.setCompoundDrawables(null, drawable_news, null, null);
    }

    // 转换Fragment
    private void switchFragment(Fragment to, String tag) {
        if (to == null)
            return;
        binding.mainToobar.setTitle(tag);
        Log.i(MainActivity.class.getCanonicalName(), "switchFragment: ");
        if (mCurrentFragmen != to) {
            FragmentTransaction transaction = mFm.beginTransaction();
            if (!to.isAdded()) {
                // 没有添加过:
                // 隐藏当前的，添加新的，显示新的
                transaction.hide(mCurrentFragmen).add(R.id.fl_show, to, tag).show(to);
            } else {
                // 隐藏当前的，显示新的
                transaction.hide(mCurrentFragmen).show(to);
            }
            mCurrentFragmen = to;
            transaction.commitAllowingStateLoss();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.main_toobar:
            {
                if(index == 0) {
                    ((MainFragment)mCurrentFragmen).scroll_Start();
                }
            }
            break;
            case R.id.net_bar:
            {
//
            }
            break;
        }

    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.main_rb_home: {
                binding.mainRbHome.setTextColor(Color.argb(0xFF, 0x12, 0x96, 0xdb));
                binding.mainRbFind.setTextColor(Color.BLACK);
                binding.mainRbMessage.setTextColor(Color.BLACK);
                Log.d("TEST", "Click: "+mFragmentList.size());
                if (mFragmentList.size() >= 1)
                switchFragment(mFragmentList.get(0), mFragmentTagList[0]);
                index = 0;
            }
            break;
            case R.id.main_rb_find: {
                binding.mainRbFind.setTextColor(Color.argb(0xFF, 0x12, 0x96, 0xdb));
                binding.mainRbHome.setTextColor(Color.BLACK);
                binding.mainRbMessage.setTextColor(Color.BLACK);
                Log.d("TEST", "Click: "+mFragmentList.size());
                if (mFragmentList.size() >= 2)
                switchFragment(mFragmentList.get(1), mFragmentTagList[1]);
                index = 1;
            }
            break;
            case R.id.main_rb_message: {
                binding.mainRbMessage.setTextColor(Color.argb(0xFF, 0x12, 0x96, 0xdb));
                binding.mainRbFind.setTextColor(Color.BLACK);
                binding.mainRbHome.setTextColor(Color.BLACK);
                Log.d("TEST", "Click: "+mFragmentList.size());
                if (mFragmentList.size() >= 3)
                switchFragment(mFragmentList.get(2), mFragmentTagList[2]);
                index = 2;
            }
            break;
        }
    }


    @Override
    public void notify(String mes) {

    }

    @Override
    public void change(int change) {
        number +=change;
        if (number > 0 ){
            binding.badge.setVisibility(View.VISIBLE);
            binding.badge.setText(number+"");
        }
    }

}
