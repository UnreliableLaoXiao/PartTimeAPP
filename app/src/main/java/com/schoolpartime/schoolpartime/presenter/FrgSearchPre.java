package com.schoolpartime.schoolpartime.presenter;

import android.view.View;
import android.widget.AdapterView;

import com.schoolpartime.schoolpartime.R;
import com.schoolpartime.schoolpartime.SuperActivity;

import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.schoolpartime.schoolpartime.activity.DetailsInfoActivity;
import com.schoolpartime.schoolpartime.adapter.RecyclerAdapter;
import com.schoolpartime.schoolpartime.databinding.FragmentSearchBinding;

import java.util.ArrayList;
import java.util.List;

public class FrgSearchPre implements Presenter {

    private FragmentSearchBinding binding;
    private SuperActivity activity;
    private int mscrollY;
    private boolean isScroll = false;

    @Override
    public void attach(ViewDataBinding binding, SuperActivity activity) {
        this.binding = (FragmentSearchBinding) binding;
        this.activity = activity;
        init();
    }

    private void init() {

        // 给下拉列表添加适配器
        List<String> list_city = new ArrayList<>();
        List<String> list_type = new ArrayList<>();
        List<String> list_other = new ArrayList<>();

        list_city.add("不限");
        for (int i = 0; i < activity.getResources().getStringArray(R.array.city).length; i++) {
            list_city.add(activity.getResources().getStringArray(R.array.city)[i]);
        }
        list_type.add("不限");
        for (int i = 0; i < activity.getResources().getStringArray(R.array.type).length; i++) {
            list_type.add(activity.getResources().getStringArray(R.array.type)[i]);
        }
        list_other.add("不限");
        for (int i = 0; i < activity.getResources().getStringArray(R.array.other).length; i++) {
            list_other.add(activity.getResources().getStringArray(R.array.other)[i]);
        }

        binding.searchCity.attachDataSource(list_city);
        binding.searchWorktype.attachDataSource(list_type);
        binding.searchMore.attachDataSource(list_other);

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


        binding.searchCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        binding.searchWorktype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        binding.searchMore.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void notifyUpdate(int code) {

    }
}
