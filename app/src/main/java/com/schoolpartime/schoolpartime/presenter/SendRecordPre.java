package com.schoolpartime.schoolpartime.presenter;

import android.view.View;

import com.schoolpartime.schoolpartime.R;
import com.schoolpartime.schoolpartime.SuperActivity;
import com.schoolpartime.schoolpartime.adapter.FragmentAdapter;
import com.schoolpartime.schoolpartime.databinding.ActivitySendrecordBinding;
import com.schoolpartime.schoolpartime.fragment.FailedFragment;
import com.schoolpartime.schoolpartime.fragment.RequestFragment;
import com.schoolpartime.schoolpartime.fragment.SuccessFragment;

import java.util.ArrayList;
import java.util.List;

import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;

public class SendRecordPre implements Presenter, View.OnClickListener {

    private ActivitySendrecordBinding binding;
    private SuperActivity activity;

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
        fragments.add(new RequestFragment());
        fragments.add(new SuccessFragment());
        fragments.add(new FailedFragment());

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
            }
            break;
        }
    }
}
