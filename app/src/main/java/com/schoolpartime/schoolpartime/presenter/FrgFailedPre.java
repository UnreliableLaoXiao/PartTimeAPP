package com.schoolpartime.schoolpartime.presenter;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.schoolpartime.schoolpartime.SuperActivity;
import com.schoolpartime.schoolpartime.activity.DetailsInfoActivity;
import com.schoolpartime.schoolpartime.adapter.WorkListAdapter;
import com.schoolpartime.schoolpartime.databinding.FragmentFailedBinding;
import com.schoolpartime.schoolpartime.databinding.FragmentRequestBinding;
import com.schoolpartime.schoolpartime.entity.WorkInfo;
import com.schoolpartime.schoolpartime.entity.baseModel.ResultModel;
import com.schoolpartime.schoolpartime.net.interfacz.GetRequestServer;
import com.schoolpartime.schoolpartime.net.request.HttpRequest;
import com.schoolpartime.schoolpartime.net.request.base.RequestResult;
import com.schoolpartime.schoolpartime.util.LogUtil;
import com.schoolpartime.schoolpartime.util.sp.SpCommonUtils;

import java.util.ArrayList;

import androidx.databinding.ViewDataBinding;

public class FrgFailedPre implements Presenter, AdapterView.OnItemClickListener {

    SuperActivity activity;
    FragmentFailedBinding binding;
    ArrayList<WorkInfo> workInfos = new ArrayList<>();
    private WorkListAdapter adapter;

    @Override
    public void attach(ViewDataBinding binding, SuperActivity activity) {
        this.binding = (FragmentFailedBinding) binding;
        this.activity = activity;
        init();

    }

    private void init() {
        adapter = new WorkListAdapter(workInfos,activity);
        binding.requestList.setAdapter(adapter);
        binding.requestList.setOnItemClickListener(this);
        getRequest();
    }

    private void getRequest() {
        HttpRequest.request(HttpRequest.builder().create(GetRequestServer.class).getRequest(SpCommonUtils.getUserId(),2),
                new RequestResult() {
                    @Override
                    public void success(ResultModel resultModel) {
                        activity.dismiss();
                        LogUtil.d("得到申请列表----------ResultModel："+resultModel.toString());
                        if (resultModel.code == 200) {
                            LogUtil.d("得到申请列表成功");
                            workInfos.clear();
                            workInfos.addAll((ArrayList<WorkInfo>) resultModel.data);
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
                        LogUtil.d("得到申请列表异常",e);
                    }
                },true);
    }

    @Override
    public void notifyUpdate(int code) {
        switch (code){
            case 4:{
                workInfos.clear();
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.notifyDataSetChanged();
                    }
                });
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
