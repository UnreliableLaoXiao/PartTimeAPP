package com.schoolpartime.schoolpartime.presenter;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.SearchView;

import com.schoolpartime.schoolpartime.R;
import com.schoolpartime.schoolpartime.SuperActivity;
import com.schoolpartime.schoolpartime.activity.DetailsInfoActivity;
import com.schoolpartime.schoolpartime.adapter.WorkListAdapter;
import com.schoolpartime.schoolpartime.databinding.ActivitySearchBinding;
import com.schoolpartime.schoolpartime.entity.WorkInfo;
import com.schoolpartime.schoolpartime.entity.baseModel.ResultModel;
import com.schoolpartime.schoolpartime.net.interfacz.TitleSearchServer;
import com.schoolpartime.schoolpartime.net.request.HttpRequest;
import com.schoolpartime.schoolpartime.net.request.base.RequestResult;
import com.schoolpartime.schoolpartime.util.LogUtil;
import java.util.ArrayList;
import androidx.databinding.ViewDataBinding;

public class SearchPre implements Presenter, View.OnClickListener, AdapterView.OnItemClickListener {

    SuperActivity activity;
    ActivitySearchBinding binding;

    ArrayList<WorkInfo> workInfos = new ArrayList<>();
    private WorkListAdapter adapter;

    @Override
    public void attach(ViewDataBinding binding, SuperActivity activity) {
        this.binding = (ActivitySearchBinding) binding;
        this.activity = activity;
        init();

    }

    private void init() {
        adapter = new WorkListAdapter(workInfos,activity);
        binding.cancle.setOnClickListener(this);
        binding.listView.setAdapter(adapter);
        binding.listView.setOnItemClickListener(this);
//        binding.listView.setTextFilterEnabled(true);
        binding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                activity.showResult(binding.lly, "您搜索的是：" + query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                if (newText.length() == 0){
                    workInfos.clear();
                    adapter.notifyDataSetChanged();
                }

                if (newText.length() >= 2) {
                    showResult(newText);
                }
                return true;
            }
        });
    }

    private void showResult(String title) {
        activity.show("正在加载...");
        HttpRequest.request(HttpRequest.builder().create(TitleSearchServer.class).getTitleSearchWorkInfo(title),
                new RequestResult() {
                    @Override
                    public void success(ResultModel resultModel) {
                        activity.dismiss();
                        LogUtil.d(" 标题搜索兼职信息----------ResultModel：" + resultModel.toString());
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

    @Override
    public void notifyUpdate(int code) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cancle: {
                activity.finish();
            }
            break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("workinfo",workInfos.get(position));
        (new DetailsInfoActivity()).inToActivity(activity,bundle);
    }
}
