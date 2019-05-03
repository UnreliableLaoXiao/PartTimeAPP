package com.schoolpartime.schoolpartime.presenter;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.schoolpartime.schoolpartime.R;
import com.schoolpartime.schoolpartime.SuperActivity;
import com.schoolpartime.schoolpartime.activity.DetailsInfoActivity;
import com.schoolpartime.schoolpartime.activity.NewWorkActivity;
import com.schoolpartime.schoolpartime.adapter.WorkListWithStatuAdapter;
import com.schoolpartime.schoolpartime.databinding.ActivityBosssendBinding;
import com.schoolpartime.schoolpartime.dialog.DialogUtil;
import com.schoolpartime.schoolpartime.entity.WorkInfo;
import com.schoolpartime.schoolpartime.entity.baseModel.ResultModel;
import com.schoolpartime.schoolpartime.net.interfacz.MySendWorkInfoServer;
import com.schoolpartime.schoolpartime.net.interfacz.SoldOutWorkInfoServer;
import com.schoolpartime.schoolpartime.net.request.HttpRequest;
import com.schoolpartime.schoolpartime.net.request.base.RequestResult;
import com.schoolpartime.schoolpartime.util.LogUtil;
import com.schoolpartime.schoolpartime.util.sp.SpCommonUtils;
import com.schoolpartime.schoolpartime.weiget.myListView.XrefershListviewListener;

import java.util.ArrayList;

import androidx.databinding.ViewDataBinding;

public class BossSendPre implements Presenter, View.OnClickListener, XrefershListviewListener, AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {

    private ActivityBosssendBinding binding;
    private SuperActivity activity;
    ArrayList<WorkInfo> workInfos = new ArrayList<>();
    private WorkListWithStatuAdapter adapter;

    @Override
    public void attach(ViewDataBinding binding, SuperActivity activity) {
        this.binding = (ActivityBosssendBinding) binding;
        this.activity = activity;
        init();

    }

    private void init() {
        initData(SpCommonUtils.getUserId());
        adapter = new WorkListWithStatuAdapter(workInfos, activity);
        binding.mySend.setAdapter(adapter);
        binding.userBack.setOnClickListener(this);
        binding.addWork.setOnClickListener(this);
        binding.mySend.setXrefershListviewListener(this);
        binding.mySend.setOnItemClickListener(this);
        binding.mySend.setOnItemLongClickListener(this);


    }

    private void initData(long userId) {
        HttpRequest.request(HttpRequest.builder().create(MySendWorkInfoServer.class).getMySendWorkInfo(userId),
                new RequestResult() {
                    @Override
                    public void success(ResultModel resultModel) {
                        activity.dismiss();
                        LogUtil.d(" 得到我发布的兼职信息----------ResultModel：" + resultModel.toString());
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
                        LogUtil.d("得到我发布的兼职信息----------失败");
                    }
                }, true);
    }

    @Override
    public void notifyUpdate(int code) {

    }

    public void notifyUpdate(WorkInfo workInfo) {
        workInfos.add(workInfo);
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                adapter.notifyDataSetChanged();
            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_back: {
                activity.finish();
            }
            break;
            case R.id.add_work: {
                (new NewWorkActivity()).inToActivityForResult(activity,1);
            }
            break;
        }
    }

    @Override
    public void onRefresh() {
        initData(SpCommonUtils.getUserId());
    }

    @Override
    public void onLoadMore() {
        initData(SpCommonUtils.getUserId());
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("workinfo",workInfos.get(position-1));
        (new DetailsInfoActivity()).inToActivity(activity,bundle);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
        if (workInfos.get(position-1).getWorkStatu() == 0){
            DialogUtil.select2Dialog(activity, "提示：", "确定将此兼职下架？", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    soldOutWorkInfo(position-1);
                }
            });
        }
        return true;
    }



    private void soldOutWorkInfo(final int position) {

        activity.show("正在发布...");
        HttpRequest.request(HttpRequest.builder().create(SoldOutWorkInfoServer.class).soldOutWorkInfo(workInfos.get(position).getId()),
                new RequestResult() {
                    @Override
                    public void success(ResultModel resultModel) {
                        activity.dismiss();
                        LogUtil.d(" 下架兼职信息----------ResultModel：" + resultModel.toString());
                        if (resultModel.code == 200) {
                            activity.showResult(binding.lly,"下架成功!");
                            workInfos.get(position).setWorkStatu(1);
                            activity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    adapter.notifyDataSetChanged();
                                }
                            });
                        } else {
                            activity.showResult(binding.lly,"下架失败!");
                        }
                    }

                    @Override
                    public void fail(Throwable e) {
                        activity.dismiss();
                        activity.showResult(binding.lly,"下架失败!");
                    }
                }, true);
    }
}
