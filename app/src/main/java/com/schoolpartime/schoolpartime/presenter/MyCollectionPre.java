package com.schoolpartime.schoolpartime.presenter;

import android.view.View;

import com.schoolpartime.schoolpartime.R;
import com.schoolpartime.schoolpartime.SuperActivity;
import com.schoolpartime.schoolpartime.activity.DetailsInfoActivity;
import com.schoolpartime.schoolpartime.adapter.RecyclerAdapter;
import com.schoolpartime.schoolpartime.databinding.ActivityCollectionBinding;

import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.LinearLayoutManager;

public class MyCollectionPre implements Presenter, View.OnClickListener {

    ActivityCollectionBinding binding;
    SuperActivity activity;

    @Override
    public void attach(ViewDataBinding binding, SuperActivity activity) {

        this.binding = (ActivityCollectionBinding) binding;
        this.activity = activity;
        init();
    }

    private void init() {
        binding.mRecyclerView.setLayoutManager(new LinearLayoutManager(activity));
        binding.mRecyclerView.setAdapter(new RecyclerAdapter(activity, new RecyclerAdapter.MyOnItemClickListener() {
            @Override
            public void onItemClick(View view) {
                (new DetailsInfoActivity()).inToActivity(activity);
            }

            @Override
            public void onItemLongClick(View view) {

            }
        }));//设置adapter

        binding.userBack.setOnClickListener(this);
        binding.userChange.setOnClickListener(this);
    }

    @Override
    public void notifyUpdate(int code) {

        switch (code){
            case 0:
            case 1:{
                binding.netBar.setVisibility(code);
            }
            break;
        }
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
                 * 修改操作
                 */

            }
            break;
        }
    }
}
