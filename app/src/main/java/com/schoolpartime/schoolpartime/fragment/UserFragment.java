package com.schoolpartime.schoolpartime.fragment;

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

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;


/**
 * Created by Auser on 2018/3/13.
 * 该Fragment页面用于展示个人信息
 */

public class UserFragment extends Fragment{

    private Presenter pre = new FrgUserPre();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentUserBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_user, container, false);
        pre.attach(binding, (SuperActivity) getActivity());
        binding.setHandler(new IntentOnClickListener());
        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        pre.notifyUpdate(0);
    }
}
