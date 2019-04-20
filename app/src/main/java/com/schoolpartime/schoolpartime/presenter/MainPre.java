package com.schoolpartime.schoolpartime.presenter;

import androidx.core.app.ActivityCompat;
import androidx.databinding.ViewDataBinding;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.Drawable;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.View;
import android.widget.RadioGroup;

import com.google.android.material.snackbar.Snackbar;
import com.google.zxing.integration.android.IntentIntegrator;
import com.schoolpartime.dao.entity.UserInfo;
import com.schoolpartime.dao.entity.WorkType;
import com.schoolpartime.schoolpartime.R;
import com.schoolpartime.schoolpartime.SchoolPartimeApplication;
import com.schoolpartime.schoolpartime.SuperActivity;
import com.schoolpartime.schoolpartime.activity.MainActivity;
import com.schoolpartime.schoolpartime.activity.SearchActivity;
import com.schoolpartime.schoolpartime.event.NumberController;
import com.schoolpartime.schoolpartime.chat.WebClient;
import com.schoolpartime.schoolpartime.databinding.ActivityMianBinding;
import com.schoolpartime.schoolpartime.entity.baseModel.ResultModel;
import com.schoolpartime.schoolpartime.fragment.MainFragment;
import com.schoolpartime.schoolpartime.net.interfacz.NoReadSumServer;
import com.schoolpartime.schoolpartime.net.interfacz.UserInfoServer;
import com.schoolpartime.schoolpartime.net.interfacz.WorkTypeServer;
import com.schoolpartime.schoolpartime.net.request.HttpRequest;
import com.schoolpartime.schoolpartime.net.request.base.RequestResult;
import com.schoolpartime.schoolpartime.util.LogUtil;
import com.schoolpartime.schoolpartime.util.sp.SpCommonUtils;

import java.util.ArrayList;

public class MainPre implements Presenter, View.OnClickListener, RadioGroup.OnCheckedChangeListener, WebClient.NotifyMessage, NumberController.NotifyNumber {

    private ActivityMianBinding binding;
    private SuperActivity activity;
    private FragmentManager mFm;
    private ArrayList<Fragment> mFragmentList = new ArrayList<>();
    private String[] mFragmentTagList = {"主页", "搜索", "我的"};
    private Fragment mCurrentFragmen = null; // 记录当前显示的Fragment
    private int index = 0;
    private boolean isFirst = true;
    private int number = 0;

    WebClient webClient;
    NumberController controller;

    @Override
    public void attach(ViewDataBinding binding, SuperActivity activity) {
        this.binding = (ActivityMianBinding) binding;
        this.activity = activity;
        init();
    }

    @SuppressLint("ResourceAsColor")
    private void init() {
        mFragmentList = MainActivity.getFragmentList();
        setDraws();
        getWorkType();
        binding.badge.setVisibility(View.GONE);
        mCurrentFragmen = mFragmentList.get(0);
        binding.myToolBarTitle.setText(mFragmentTagList[0]);
        Log.d("TEST", "Click1: " + mCurrentFragmen);
        // 初始化首次进入时的Fragment
        mFm = activity.getSupportFragmentManager();
        FragmentTransaction transaction = mFm.beginTransaction();
        for (int i = mFragmentList.size()-1;i >= 0;i-- ) {
            transaction.add(R.id.fl_show, mFragmentList.get(i), mFragmentTagList[i]);
            transaction.hide(mFragmentList.get(i));
        }
        transaction.show(mCurrentFragmen);
        transaction.commitAllowingStateLoss();
        binding.mainGp.setOnCheckedChangeListener(this);
        binding.netBar.setOnClickListener(this);
        binding.myToolBar.setOnClickListener(this);
        binding.myToolBarSearch.setOnClickListener(this);
        binding.myToolBarScan.setOnClickListener(this);

    }

    private void getWorkType() {
        HttpRequest.request(HttpRequest.builder().create(WorkTypeServer.class).
                        getWorkTypes(),
                new RequestResult() {
                    @Override
                    public void success(ResultModel resultModel) {
                        LogUtil.d("得到所有兼职类型----------ResultModel：" + resultModel.toString());
                        if (resultModel.code == 200) {
                            ArrayList<WorkType> workTypes = (ArrayList<WorkType>) resultModel.data;

                            SchoolPartimeApplication.getmDaoSession().getWorkTypeDao().deleteAll();
                            SchoolPartimeApplication.getmDaoSession().getWorkTypeDao().insertInTx(workTypes);
                            LogUtil.d("添加所有兼职类型---------成功");
                        }
                    }

                    @Override
                    public void fail(Throwable e) {
                        LogUtil.d("得到所有兼职类型----------失败", e);
                    }
                }, true);
    }

    @Override
    public void notifyUpdate(int code) {
        switch (code) {
            case 0:
            case 1: {
                binding.netBar.setVisibility(code);
            }
            break;
            case 7: {
                if (SpCommonUtils.getIsLogin() && isFirst) {
                    webClient = WebClient.getInstance();
                    controller = NumberController.getInstance();
                    webClient.addNotity(this);
                    controller.addNotity(this);
                    getUserInfo();
                    getNoreadSum();
                    isFirst = false;
                }
            }
            break;
            case 8: {
                webClient.removeNotity(this);
                controller.removeNotity(this);
            }
            break;
        }

    }

    private void getNoreadSum() {
        HttpRequest.request(HttpRequest.builder().create(NoReadSumServer.class).
                        getAllNoread(SpCommonUtils.getUserId()),
                new RequestResult() {
                    @Override
                    public void success(ResultModel resultModel) {
                        LogUtil.d("得到所有未读消息----------ResultModel：" + resultModel.toString());
                        if (resultModel.code == 200) {
                            double sum = (Double) resultModel.data;
                            int msum = (int) (sum + 0);
                            controller.NotifyAll(msum);
                            LogUtil.d("得到所有未读消息----------成功：" + sum);
                        }
                    }

                    @Override
                    public void fail(Throwable e) {
                        LogUtil.d("得到所有未读消息----------失败", e);
                    }
                }, true);
    }

    @SuppressLint("WrongConstant")
    public void showResult(String mes) {
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
                        LogUtil.d("得到登录用户信息----------ResultModel：" + resultModel.toString());
                        if (resultModel.code == 200) {
                            UserInfo userInfo = (UserInfo) resultModel.data;
                            SchoolPartimeApplication.getmDaoSession().getUserInfoDao().insert(userInfo);
                            LogUtil.d("数据库加入userinfo信息----------成功：" + userInfo.toString());
                            activity.dismiss();
                        } else {
                            showResult(resultModel.message);
                            SpCommonUtils.setIsLogin(false);
                            activity.dismiss();
                        }
                    }

                    @Override
                    public void fail(Throwable e) {
                        LogUtil.d("得到登录用户信息失败---》请求失败", e);
                        SpCommonUtils.setIsLogin(false);
                        activity.dismiss();
                        showResult("请求失败");
                    }
                }, true);
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
        binding.myToolBarTitle.setText(tag);
        LogUtil.d("switchFragment: ");
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
        switch (v.getId()) {
            case R.id.my_tool_bar: {
                if (index == 0) {
                    ((MainFragment) mCurrentFragmen).scroll_Start();
                }
            }
            break;
            case R.id.net_bar: {
//
            }
            break;
            case R.id.my_tool_bar_scan: {
                int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.CAMERA);
                if (permission != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.CAMERA}, 1);
                } else {
                    Scan();
                }
            }
            break;
            case R.id.my_tool_bar_search: {
                (new SearchActivity()).inToActivity(activity);
            }
            break;
        }

    }

    public void Scan() {
        new IntentIntegrator(activity).initiateScan();
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.main_rb_home: {
                binding.mainRbHome.setTextColor(Color.argb(0xFF, 0x12, 0x96, 0xdb));
                binding.mainRbFind.setTextColor(Color.BLACK);
                binding.mainRbMessage.setTextColor(Color.BLACK);
                Log.d("TEST", "Click: " + mFragmentList.size());
                if (mFragmentList.size() >= 1)
                    switchFragment(mFragmentList.get(0), mFragmentTagList[0]);
                index = 0;
            }
            break;
            case R.id.main_rb_find: {
                binding.mainRbFind.setTextColor(Color.argb(0xFF, 0x12, 0x96, 0xdb));
                binding.mainRbHome.setTextColor(Color.BLACK);
                binding.mainRbMessage.setTextColor(Color.BLACK);
                Log.d("TEST", "Click: " + mFragmentList.size());
                if (mFragmentList.size() >= 2)
                    switchFragment(mFragmentList.get(1), mFragmentTagList[1]);
                index = 1;
            }
            break;
            case R.id.main_rb_message: {
                binding.mainRbMessage.setTextColor(Color.argb(0xFF, 0x12, 0x96, 0xdb));
                binding.mainRbFind.setTextColor(Color.BLACK);
                binding.mainRbHome.setTextColor(Color.BLACK);
                Log.d("TEST", "Click: " + mFragmentList.size());
                if (mFragmentList.size() >= 3)
                    switchFragment(mFragmentList.get(2), mFragmentTagList[2]);
                index = 2;
            }
            break;
        }
    }


    @Override
    public void notify(String mes) {

        LogUtil.d("Main收到消息通知");
        number += 1;
        LogUtil.d("Main number is " + number);
        LogUtil.d("Main 设置显示数量");
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                binding.badge.setVisibility(View.VISIBLE);
                LogUtil.d("Main number is " + number);
                binding.badge.setText(number + "");
            }
        });

    }

    @Override
    public void change(int change) {
        number += change;
        if (number > 0) {
            binding.badge.setVisibility(View.VISIBLE);
            binding.badge.setText(number + "");
        }
    }

}
