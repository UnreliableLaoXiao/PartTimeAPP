package com.schoolpartime.schoolpartime.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.schoolpartime.schoolpartime.R;
import com.schoolpartime.schoolpartime.SuperActivity;
import com.schoolpartime.schoolpartime.databinding.FragmentUserBinding;
import com.schoolpartime.schoolpartime.listener.IntentOnClickListener;
import com.schoolpartime.schoolpartime.presenter.FrgUserPre;
import com.schoolpartime.schoolpartime.presenter.Presenter;
import com.schoolpartime.schoolpartime.util.LogUtil;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;


/**
 * Created by Auser on 2018/3/13.
 * 该Fragment页面用于展示个人信息
 */

public class UserFragment extends Fragment{

    public static final String SYSTEM_EXIT = "com.example.boss";
    private Presenter pre = new FrgUserPre();
    private MyReceiver receiver;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentUserBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_user, container, false);
        pre.attach(binding, (SuperActivity) getActivity());
        //注册广播，用于退出程序
        IntentFilter filter = new IntentFilter();
        filter.addAction(SYSTEM_EXIT);
        receiver = new MyReceiver();
        getActivity().registerReceiver(receiver, filter);
        return binding.getRoot();
    }

    private class MyReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            pre.notifyUpdate(8);
        }
    }

    @Override
    public void onResume() {
        pre.notifyUpdate(0);
        LogUtil.d("FragUser onResume");
        super.onResume();

    }

    @Override
    public void onPause() {
        pre.notifyUpdate(0);
        LogUtil.d("FragUser onPause");
        super.onPause();
    }

    @Override
    public void onDestroy() {
        pre.notifyUpdate(6);
        getActivity().unregisterReceiver(receiver);
        super.onDestroy();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        if (hidden) {
            pre.notifyUpdate(0);
        } else {
            pre.notifyUpdate(0);
        }
    }
}
