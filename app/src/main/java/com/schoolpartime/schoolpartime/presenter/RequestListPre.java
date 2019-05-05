package com.schoolpartime.schoolpartime.presenter;

import android.os.Bundle;
import android.view.View;

import com.schoolpartime.dao.entity.UserInfo;
import com.schoolpartime.schoolpartime.SuperActivity;
import com.schoolpartime.schoolpartime.activity.RealChatActivity;
import com.schoolpartime.schoolpartime.adapter.RequestPeopelListAdapter;
import com.schoolpartime.schoolpartime.databinding.ActivityRequestlistBinding;
import com.schoolpartime.schoolpartime.entity.ChatRecord;
import com.schoolpartime.schoolpartime.entity.baseModel.ResultModel;
import com.schoolpartime.schoolpartime.net.interfacz.ChatWithBoss;
import com.schoolpartime.schoolpartime.net.interfacz.GetRequestPeopleListServer;
import com.schoolpartime.schoolpartime.net.interfacz.RequestYesServer;
import com.schoolpartime.schoolpartime.net.request.HttpRequest;
import com.schoolpartime.schoolpartime.net.request.base.RequestResult;
import com.schoolpartime.schoolpartime.util.LogUtil;
import com.schoolpartime.schoolpartime.util.sp.SpCommonUtils;

import java.util.ArrayList;

import androidx.databinding.ViewDataBinding;

public class RequestListPre implements Presenter, View.OnClickListener, RequestPeopelListAdapter.RequestSelectListener {

    ActivityRequestlistBinding binding;
    SuperActivity activity;
    ArrayList<UserInfo> userInfos = new ArrayList<>();
    private RequestPeopelListAdapter adapter;
    private long workid;

    @Override
    public void attach(ViewDataBinding binding, SuperActivity activity) {
        this.binding = (ActivityRequestlistBinding) binding;
        this.activity = activity;
        init();
    }

    private void init() {
        workid = activity.getIntent().getLongExtra("workid", -1);
        LogUtil.d("workid="+ workid);

        adapter = new RequestPeopelListAdapter(userInfos,activity,this);
        binding.peoplelist.setAdapter(adapter);
        binding.userBack.setOnClickListener(this);
        getRequestList(workid);


    }

    private void getRequestList(long workid) {
        activity.show("正在加载...");
        HttpRequest.request(HttpRequest.builder().create(GetRequestPeopleListServer.class).getListPeople(workid),
                new RequestResult() {
                    @Override
                    public void success(ResultModel resultModel) {
                        activity.dismiss();
                        LogUtil.d(" 得到申请人列表----------ResultModel：" + resultModel.toString());
                        if (resultModel.code == 200) {
                            LogUtil.d("得到申请人列表成功");
                            userInfos.clear();
                            userInfos.addAll((ArrayList<UserInfo>)resultModel.data);
                            activity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    LogUtil.d("更新数据");
                                    adapter.notifyDataSetChanged();
                                }
                            });
                        }else {
                            LogUtil.d("得到申请人列表失败");
                        }
                    }

                    @Override
                    public void fail(Throwable e) {
                        activity.dismiss();
                        LogUtil.d("得到申请人列表异常");
                    }
                }, true);
    }

    @Override
    public void notifyUpdate(int code) {

    }

    @Override
    public void onClick(View v) {
        activity.finish();
    }

    @Override
    public void selectYes(final UserInfo userInfo) {
        LogUtil.d("点击同意："+userInfo.toString());
        requestYes(userInfo.getId(),workid,1,userInfo);
    }

    private void requestYes(Long id, long workid, int statu, final UserInfo userInfo) {
        activity.show("正在同意...");
        HttpRequest.request(HttpRequest.builder().create(RequestYesServer.class).requestyes(id,workid,statu),
                new RequestResult() {
                    @Override
                    public void success(ResultModel resultModel) {
                        activity.dismiss();
                        LogUtil.d(" 正在同意----------ResultModel：" + resultModel.toString());
                        if (resultModel.code == 200) {
                            LogUtil.d("正在同意成功");
                            userInfos.remove(userInfo);
                            activity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    adapter.notifyDataSetChanged();
                                }
                            });
                        }else {
                            LogUtil.d("正在同意失败");
                        }
                    }

                    @Override
                    public void fail(Throwable e) {
                        activity.dismiss();
                        LogUtil.d("正在同意异常");
                    }
                }, true);
    }

    @Override
    public void selectNo(UserInfo userInfo) {
        LogUtil.d("点击拒绝："+userInfo.toString());
        requestYes(userInfo.getId(),workid,2,userInfo);
    }

    @Override
    public void chat(UserInfo userInfo) {
        startChatWithBoss(userInfo.getId());
    }

    private void startChatWithBoss(long id) {
        activity.show("正在开启聊天界面...");
        LogUtil.d(""+id + " userid = " + SpCommonUtils.getUserId());
        HttpRequest.request(HttpRequest.builder().create(ChatWithBoss.class).getWithBossChatRecord(
                SpCommonUtils.getUserId(),id),
                new RequestResult() {
                    @Override
                    public void success(ResultModel resultModel) {
                        activity.dismiss();
                        LogUtil.d(" 申请聊天----------ResultModel：" + resultModel.toString());
                        if (resultModel.code == 200) {
                            LogUtil.d("申请成功");
                            ChatRecord record = (ChatRecord) resultModel.data;
                            Bundle bundle = new Bundle();
                            bundle.putLong("to",record.getOther_id());
                            bundle.putString("name",record.getName());
                            (new RealChatActivity()).inToActivity(activity,bundle);
                        }else {
                            LogUtil.d("申请失败");
                        }
                    }

                    @Override
                    public void fail(Throwable e) {
                        activity.dismiss();
                        LogUtil.d("申请异常");
                    }
                }, true);
    }
}
