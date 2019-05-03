package com.schoolpartime.schoolpartime.presenter;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.schoolpartime.schoolpartime.R;
import com.schoolpartime.schoolpartime.SuperActivity;
import com.schoolpartime.schoolpartime.activity.DetailsInfoActivity;
import com.schoolpartime.schoolpartime.adapter.WorkListWithStatuAdapter;
import com.schoolpartime.schoolpartime.databinding.ActivityCollectionBinding;
import com.schoolpartime.schoolpartime.dialog.DialogUtil;
import com.schoolpartime.schoolpartime.entity.WorkInfo;
import com.schoolpartime.schoolpartime.entity.baseModel.ResultModel;
import com.schoolpartime.schoolpartime.net.interfacz.DelCollectWorkInfosServer;
import com.schoolpartime.schoolpartime.net.interfacz.GetCollectWorkInfosServer;
import com.schoolpartime.schoolpartime.net.request.HttpRequest;
import com.schoolpartime.schoolpartime.net.request.base.RequestResult;
import com.schoolpartime.schoolpartime.util.LogUtil;
import com.schoolpartime.schoolpartime.util.sp.SpCommonUtils;
import com.schoolpartime.schoolpartime.weiget.myListView.XrefershListviewListener;
import java.util.ArrayList;

import androidx.databinding.ViewDataBinding;

/**
 * 兼职信息收藏信息显示的页面
 * 功能：
 *     1、加载自己收藏的信息
 *     2、删除所有收藏的信息
 */
public class MyCollectionPre implements Presenter, View.OnClickListener ,XrefershListviewListener ,AdapterView.OnItemClickListener {

    ActivityCollectionBinding binding;
    SuperActivity activity;
    ArrayList<WorkInfo> workInfos = new ArrayList<>();
    private WorkListWithStatuAdapter adapter;

    @Override
    public void attach(ViewDataBinding binding, SuperActivity activity) {

        this.binding = (ActivityCollectionBinding) binding;
        this.activity = activity;
        init();
    }

    private void init() {

        adapter = new WorkListWithStatuAdapter(workInfos, activity);
        binding.mySend.setAdapter(adapter);
        binding.mySend.setXrefershListviewListener(this);
        binding.userBack.setOnClickListener(this);
        binding.userChange.setOnClickListener(this);
        binding.mySend.setOnItemClickListener(this);

        /**
         * 网络请求显示收藏的兼职，点击查看详情
         */
        refreshData();
    }

    private void refreshData() {
        activity.show("正在记载... ");
        HttpRequest.request(HttpRequest.builder().create(GetCollectWorkInfosServer.class).getCollectWorkInfo(SpCommonUtils.getUserId()),
                new RequestResult() {
                    @Override
                    public void success(ResultModel resultModel) {
                        activity.dismiss();
                        LogUtil.d(" 收藏兼职信息----------ResultModel：" + resultModel.toString());
                        if (resultModel.code == 200) {
                            LogUtil.d("更新数据数据");
                            workInfos.clear();
                            workInfos.addAll((ArrayList<WorkInfo>) resultModel.data);
                            binding.mySend.computeScroll();
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
                    }
                }, true);
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
                if (workInfos.size() > 0){

                    DialogUtil.select2Dialog(activity, "提示：", "确定清空我的收藏？", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            clearAllCollection();
                        }
                    });
                }

            }
            break;
        }
    }

    private void clearAllCollection() {
        activity.show("正在加载... ");
        HttpRequest.request(HttpRequest.builder().create(DelCollectWorkInfosServer.class).delCollectWorkInfo(SpCommonUtils.getUserId()),
                new RequestResult() {
                    @Override
                    public void success(ResultModel resultModel) {
                        activity.dismiss();
                        LogUtil.d(" 收藏兼职信息----------ResultModel：" + resultModel.toString());
                        if (resultModel.code == 200) {
                            workInfos.clear();
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
                    }
                }, true);
    }

    @Override
    public void onRefresh() {
        refreshData();
    }

    @Override
    public void onLoadMore() {
        refreshData();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("workinfo",workInfos.get(position-1));
        (new DetailsInfoActivity()).inToActivity(activity,bundle);
    }
}
