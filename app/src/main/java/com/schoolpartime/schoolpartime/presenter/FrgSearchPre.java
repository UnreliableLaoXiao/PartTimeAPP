package com.schoolpartime.schoolpartime.presenter;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.schoolpartime.dao.entity.City;
import com.schoolpartime.dao.entity.WorkType;
import com.schoolpartime.schoolpartime.SchoolPartimeApplication;
import com.schoolpartime.schoolpartime.SuperActivity;

import androidx.databinding.ViewDataBinding;

import com.schoolpartime.schoolpartime.activity.DetailsInfoActivity;
import com.schoolpartime.schoolpartime.adapter.WorkListAdapter;
import com.schoolpartime.schoolpartime.databinding.FragmentSearchBinding;
import com.schoolpartime.schoolpartime.entity.WorkInfo;
import com.schoolpartime.schoolpartime.entity.baseModel.ResultModel;
import com.schoolpartime.schoolpartime.net.interfacz.MainWorkInfoServer;
import com.schoolpartime.schoolpartime.net.interfacz.OtherSearchServer;
import com.schoolpartime.schoolpartime.net.interfacz.TitleSearchServer;
import com.schoolpartime.schoolpartime.net.request.HttpRequest;
import com.schoolpartime.schoolpartime.net.request.base.RequestResult;
import com.schoolpartime.schoolpartime.util.LogUtil;
import com.schoolpartime.schoolpartime.weiget.myListView.XrefershListviewListener;

import java.util.ArrayList;
import java.util.List;

public class FrgSearchPre implements Presenter ,XrefershListviewListener, AdapterView.OnItemClickListener {

    private FragmentSearchBinding binding;
    private SuperActivity activity;
    private WorkListAdapter adapter;
    ArrayList<WorkInfo> workInfos = new ArrayList<>();
    private volatile int requestTimes = 0;

    private int select_type = 0;
    private String select_city = "";
    private ArrayList<WorkType> workTypes;
    private ArrayList<City> cities;
    private List<String> list_city;
    private List<String> list_type;

    @Override
    public void attach(ViewDataBinding binding, SuperActivity activity) {
        this.binding = (FragmentSearchBinding) binding;
        this.activity = activity;
        init();
    }

    private void init() {

        // 给下拉列表添加适配器
        list_city = new ArrayList<>();
        list_type = new ArrayList<>();

        workTypes = (ArrayList<WorkType>) SchoolPartimeApplication.getmDaoSession().getWorkTypeDao().loadAll();
        cities = (ArrayList<City>) SchoolPartimeApplication.getmDaoSession().getCityDao().loadAll();

        list_city.add("城市");
        for (int i = 0; i < cities.size(); i++) {
            list_city.add(cities.get(i).getCityName());
        }
        list_type.add("类型");
        for (int i = 0; i < workTypes.size(); i++) {
            list_type.add(workTypes.get(i).getName());
        }

        binding.searchCity.attachDataSource(list_city);
        binding.searchWorktype.attachDataSource(list_type);

        initData(requestTimes);

        //设置adapter
        /**
         * 进入兼职详情页面
         */
        adapter = new WorkListAdapter(workInfos,activity);

        binding.mRecyclerView.setAdapter(adapter);
        binding.mRecyclerView.setXrefershListviewListener(this);


        binding.searchCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    select_city = cities.get(position-1).getCityName();
                    LogUtil.d("选择城市为："+cities.get(position-1).getCityName());
                }else {
                    select_city = "";
                    LogUtil.d("未选择城市为：");
                }
                searchByOther(select_type,select_city);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        binding.searchWorktype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0){
                    select_type = workTypes.get(position-1).getId();
                    LogUtil.d("选择类型为："+workTypes.get(position-1).getName());
                }else {
                    select_type = 0;
                    LogUtil.d("未选择类型为：");
                }
                searchByOther(select_type,select_city);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        binding.mRecyclerView.setOnItemClickListener(this);
    }

    private void searchByOther(int select_type, String select_city) {
        LogUtil.d("选择条件为："+select_type + "----------" + select_city);
        activity.show("正在加载...");
        HttpRequest.request(HttpRequest.builder().create(OtherSearchServer.class).getSearchWorkInfo(select_city,select_type),
                new RequestResult() {
                    @Override
                    public void success(ResultModel resultModel) {
                        activity.dismiss();
                        LogUtil.d(" 搜索兼职信息----------ResultModel：" + resultModel.toString());
                        if (resultModel.code == 200) {
                            LogUtil.d("查找成功!");
                            workInfos.clear();
                            workInfos.addAll((ArrayList<WorkInfo>)resultModel.data);
                            activity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    adapter.notifyDataSetChanged();
                                }
                            });
                        }
                    }

                    @Override
                    public void fail(Throwable e) {
                        activity.dismiss();
                        LogUtil.d("查找失败!");
                    }
                }, true);
    }

    private void initData(final int times) {
        HttpRequest.request(HttpRequest.builder().create(MainWorkInfoServer.class).getWorkInfoNormal(times),
                new RequestResult() {
                    @Override
                    public void success(ResultModel resultModel) {
                        activity.dismiss();
                        LogUtil.d("请求次数" + times + "   得到兼职信息----------ResultModel：" + resultModel.toString());
                        if (resultModel.code == 200) {
                            if (requestTimes > 0){
                                LogUtil.d("添加数据");
                                workInfos.addAll((ArrayList<WorkInfo>) resultModel.data);
                            } else {
                                LogUtil.d("更新数据数据");
                                workInfos.clear();
                                workInfos.addAll((ArrayList<WorkInfo>) resultModel.data);
                            }
                            binding.mRecyclerView.computeScroll();
                            requestTimes++;
                            LogUtil.d("requestTimes = " + requestTimes);
                            activity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    adapter.notifyDataSetChanged();
                                }
                            });
                        }
                    }

                    @Override
                    public void fail(Throwable e) {
                        LogUtil.d("得到兼职信息----------失败");
                    }
                }, true);
    }

    @Override
    public void notifyUpdate(int code) {

    }

    @Override
    public void onRefresh() {
        requestTimes = 0;
        initData(requestTimes);
    }

    @Override
    public void onLoadMore() {
        initData(requestTimes);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("workinfo",workInfos.get(position));
        (new DetailsInfoActivity()).inToActivity(activity,bundle);
    }
}
