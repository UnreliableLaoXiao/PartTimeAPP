package com.schoolpartime.schoolpartime.presenter;

import android.view.View;

import com.schoolpartime.schoolpartime.R;
import com.schoolpartime.schoolpartime.SuperActivity;
import com.schoolpartime.schoolpartime.adapter.FragmentAdapter;
import com.schoolpartime.schoolpartime.databinding.ActivitySendrecordBinding;
import com.schoolpartime.schoolpartime.entity.baseModel.ResultModel;
import com.schoolpartime.schoolpartime.fragment.FailedFragment;
import com.schoolpartime.schoolpartime.fragment.RequestFragment;
import com.schoolpartime.schoolpartime.fragment.SuccessFragment;
import com.schoolpartime.schoolpartime.net.interfacz.ClearAllRequestServer;
import com.schoolpartime.schoolpartime.net.request.HttpRequest;
import com.schoolpartime.schoolpartime.net.request.base.RequestResult;
import com.schoolpartime.schoolpartime.util.LogUtil;
import com.schoolpartime.schoolpartime.util.sp.SpCommonUtils;

import java.util.ArrayList;
import java.util.List;

import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;

public class SendRecordPre implements Presenter, View.OnClickListener {

    private ActivitySendrecordBinding binding;
    private SuperActivity activity;
    private ArrayList<ClearAllListener> listeners = new ArrayList<>();

    @Override
    public void attach(ViewDataBinding binding, SuperActivity activity) {
        this.activity = activity;
        this.binding = (ActivitySendrecordBinding) binding;
        init();
    }

    private void init() {
        List<String> titles = new ArrayList<>();
        titles.add("投递成功");
        titles.add("合适");
        titles.add("不合适");

        for (int i=0; i <titles.size() ; i++) {
            binding.tab.addTab(binding.tab.newTab().setText(titles.get(i)));
        }

        List<Fragment> fragments = new ArrayList<>();
        RequestFragment requestFragment = new RequestFragment();
        SuccessFragment successFragment = new SuccessFragment();
        FailedFragment failedFragment = new FailedFragment();

        listeners.add(requestFragment);
        listeners.add(successFragment);
        listeners.add(failedFragment);

        fragments.add(requestFragment);
        fragments.add(successFragment);
        fragments.add(failedFragment);

        FragmentAdapter fragmentAdapter = new FragmentAdapter(activity.getSupportFragmentManager(),fragments,titles);
        binding.viewpager.setAdapter(fragmentAdapter);
        binding.tab.setupWithViewPager(binding.viewpager);
        binding.tab.setTabsFromPagerAdapter(fragmentAdapter);

        binding.userBack.setOnClickListener(this);
        binding.userChange.setOnClickListener(this);
    }

    @Override
    public void notifyUpdate(int code) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_back:{
                activity.finish();
            }
            break;
            case R.id.user_change:{
                /**
                 * 清空操作
                 */
                clearAll();
            }
            break;
        }
    }

    public interface ClearAllListener {
        void clearAll();
    }

    private void clearAll() {
        activity.show("正在清除...");
        HttpRequest.request(HttpRequest.builder().create(ClearAllRequestServer.class).clearAll(SpCommonUtils.getUserId()),
                new RequestResult() {
                    @Override
                    public void success(ResultModel resultModel) {
                        activity.dismiss();
                        LogUtil.d("清空列表----------ResultModel："+resultModel.toString());
                        if (resultModel.code == 200) {
                            LogUtil.d("清空列表成功");
                            notifyAllClear();
                        }
                    }

                    @Override
                    public void fail(Throwable e) {
                        LogUtil.d("清空列表异常",e);
                    }
                },true);
    }

    private void notifyAllClear() {

        for (ClearAllListener listener:listeners){
            listener.clearAll();
        }
    }
}
