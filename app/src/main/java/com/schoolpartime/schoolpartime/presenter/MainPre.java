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
import com.schoolpartime.dao.entity.RequestWork;
import com.schoolpartime.dao.entity.UserCollect;
import com.schoolpartime.schoolpartime.R;
import com.schoolpartime.schoolpartime.SchoolPartimeApplication;
import com.schoolpartime.schoolpartime.SuperActivity;
import com.schoolpartime.schoolpartime.activity.MainActivity;
import com.schoolpartime.schoolpartime.activity.SearchActivity;
import com.schoolpartime.schoolpartime.event.LoginStateController;
import com.schoolpartime.schoolpartime.event.NumberController;
import com.schoolpartime.schoolpartime.databinding.ActivityMianBinding;
import com.schoolpartime.schoolpartime.entity.baseModel.ResultModel;
import com.schoolpartime.schoolpartime.fragment.MainFragment;
import com.schoolpartime.schoolpartime.net.interfacz.GetCollectWorkInfoServer;
import com.schoolpartime.schoolpartime.net.interfacz.NoReadSumServer;
import com.schoolpartime.schoolpartime.net.interfacz.RequestListServer;
import com.schoolpartime.schoolpartime.net.request.HttpRequest;
import com.schoolpartime.schoolpartime.net.request.base.RequestResult;
import com.schoolpartime.schoolpartime.util.LogUtil;
import com.schoolpartime.schoolpartime.util.sp.SpCommonUtils;

import java.util.ArrayList;

/**
 * 进入之后的主界面
 * 相关说明：
 * 1、NotifyNumber用于监听消息数量的变化
 * 2、NotifyLoginState用于监听登录状态的变化
 */
public class MainPre implements Presenter, View.OnClickListener, RadioGroup.OnCheckedChangeListener , NumberController.NotifyNumber ,
        LoginStateController.NotifyLoginState {

    private ActivityMianBinding binding;
    private SuperActivity activity;
    private FragmentManager mFm;
    private ArrayList<Fragment> mFragmentList = new ArrayList<>();
    private String[] mFragmentTagList = {"主页", "搜索", "我的"};
    private Fragment mCurrentFragmen = null; // 记录当前显示的Fragment
    private int index = 0;
    private int number = 0;

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

        binding.badge.setVisibility(View.GONE);
        mCurrentFragmen = mFragmentList.get(0);
        binding.myToolBarTitle.setText(mFragmentTagList[0]);
        mFm = activity.getSupportFragmentManager();
        FragmentTransaction transaction = mFm.beginTransaction();
        for (int i = mFragmentList.size()-1;i >= 0;i-- ) {
            transaction.add(R.id.fl_show, mFragmentList.get(i), mFragmentTagList[i]);
            transaction.hide(mFragmentList.get(i));
        }
        transaction.show(mCurrentFragmen);
        transaction.commitAllowingStateLoss();


        /**
         * 设置相关观察者
         */
        NumberController.getInstance().addNotity(this);
        LoginStateController.getInstance().addNotity(this);

        binding.mainGp.setOnCheckedChangeListener(this);
        binding.netBar.setOnClickListener(this);
        binding.myToolBar.setOnClickListener(this);
        binding.myToolBarSearch.setOnClickListener(this);
        binding.myToolBarScan.setOnClickListener(this);

    }



    @Override
    public void notifyUpdate(int code) {
        switch (code) {
            case 0:
            case 1: {
                binding.netBar.setVisibility(code);
            }
            break;
            case 8: {
                NumberController.getInstance().removeNotity(this);
                LoginStateController.getInstance().removeNotity(this);
            }
            break;
        }

    }

    private void getRequestList() {
        HttpRequest.request(HttpRequest.builder().create(RequestListServer.class).
                        requestWorkList(SpCommonUtils.getUserId()),
                new RequestResult() {
                    @Override
                    public void success(ResultModel resultModel) {
                        LogUtil.d("得到已申请兼职----------ResultModel：" + resultModel.toString());
                        if (resultModel.code == 200) {
                            SchoolPartimeApplication.getmDaoSession().getUserCollectDao().deleteAll();
                            ArrayList<RequestWork> requestWorks = (ArrayList<RequestWork>) resultModel.data;
                            SchoolPartimeApplication.getmDaoSession().getRequestWorkDao().insertInTx(requestWorks);
                            LogUtil.d("已申请兼职同步成功");
                        }
                    }

                    @Override
                    public void fail(Throwable e) {
                        LogUtil.d("得到收藏的兼职----------失败", e);
                    }
                }, true);
    }

    private void getUserCollect() {
        HttpRequest.request(HttpRequest.builder().create(GetCollectWorkInfoServer.class).
                        getCollectWork(SpCommonUtils.getUserId()),
                new RequestResult() {
                    @Override
                    public void success(ResultModel resultModel) {
                        LogUtil.d("得到收藏的兼职----------ResultModel：" + resultModel.toString());
                        if (resultModel.code == 200) {
                            SchoolPartimeApplication.getmDaoSession().getUserCollectDao().deleteAll();
                            ArrayList<UserCollect> userCollects = (ArrayList<UserCollect>) resultModel.data;
                            SchoolPartimeApplication.getmDaoSession().getUserCollectDao().insertInTx(userCollects);
                            LogUtil.d("收藏兼职同步成功");
                        }
                    }

                    @Override
                    public void fail(Throwable e) {
                        LogUtil.d("得到收藏的兼职----------失败", e);
                    }
                }, true);
    }

    private void getNoreadSum() {
        LogUtil.d("得到所有未读消息");
        HttpRequest.request(HttpRequest.builder().create(NoReadSumServer.class).
                        getAllNoread(SpCommonUtils.getUserId()),
                new RequestResult() {
                    @Override
                    public void success(ResultModel resultModel) {
                        LogUtil.d("得到所有未读消息----------ResultModel：" + resultModel.toString());
                        if (resultModel.code == 200) {
                            double sum = (Double) resultModel.data;
                            int msum = (int) (sum + 0);
                            NumberController.getInstance().NotifyAll(msum);
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
    public void change(int change) {
        LogUtil.d("监听到消息数量变化");
        number += change;
        if (number > 0) {
            binding.badge.setVisibility(View.VISIBLE);
            binding.badge.setText(number + "");
        }
    }

    @Override
    public void loginStateChange(boolean state) {
        LogUtil.d("监听到登录状态变化");
        if (state){
                LogUtil.d("MainPre -- >登录成功 ---> 加载数据");
                getNoreadSum();
                getUserCollect();
                getRequestList();
            }

        if (!state){
            binding.badge.setVisibility(View.GONE);
            number = 0;
        }
    }
}
