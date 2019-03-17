package com.schoolpartime.schoolpartime.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.schoolpartime.schoolpartime.R;
import com.schoolpartime.schoolpartime.SuperActivity;
import com.schoolpartime.schoolpartime.databinding.FragmentSearchBinding;
import com.schoolpartime.schoolpartime.presenter.FrgSearchPre;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;


/**
 * Created by Auser on 2018/3/12.
 * 该Fragment页面用于展示兼职搜索
 */

public class SearchFragment extends Fragment {
   FrgSearchPre pre = new FrgSearchPre();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentSearchBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_search, container, false);
        pre.attach(binding, (SuperActivity) getActivity());
        return binding.getRoot();
    }


}
