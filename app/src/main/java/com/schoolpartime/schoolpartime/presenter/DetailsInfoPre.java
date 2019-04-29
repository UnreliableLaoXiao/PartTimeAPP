package com.schoolpartime.schoolpartime.presenter;

import android.view.View;

import com.schoolpartime.dao.UserCollectDao;
import com.schoolpartime.dao.entity.UserCollect;
import com.schoolpartime.schoolpartime.R;
import com.schoolpartime.schoolpartime.SchoolPartimeApplication;
import com.schoolpartime.schoolpartime.SuperActivity;
import com.schoolpartime.schoolpartime.activity.LoginActivity;
import com.schoolpartime.schoolpartime.activity.SendRecordActivity;
import com.schoolpartime.schoolpartime.databinding.ActivityDetailsBinding;
import com.schoolpartime.schoolpartime.dialog.DialogUtil;
import com.schoolpartime.schoolpartime.entity.WorkInfo;
import com.schoolpartime.schoolpartime.entity.baseModel.ResultModel;
import com.schoolpartime.schoolpartime.net.interfacz.CollectWorkInfoServer;
import com.schoolpartime.schoolpartime.net.interfacz.TitleSearchServer;
import com.schoolpartime.schoolpartime.net.request.HttpRequest;
import com.schoolpartime.schoolpartime.net.request.base.RequestResult;
import com.schoolpartime.schoolpartime.util.LogUtil;
import com.schoolpartime.schoolpartime.util.sp.SpCommonUtils;

import java.util.ArrayList;

import androidx.databinding.ViewDataBinding;

public class DetailsInfoPre implements Presenter, View.OnClickListener {

    ActivityDetailsBinding binding;
    SuperActivity activity;
    boolean isCollect = false;
    private WorkInfo workInfo;

    @Override
    public void attach(ViewDataBinding binding, SuperActivity activity) {
        this.binding = (ActivityDetailsBinding) binding;
        this.activity = activity;
        init();
    }

    private void init() {
        workInfo = activity.getIntent().getParcelableExtra("workinfo");

        UserCollect unique = SchoolPartimeApplication.getmDaoSession().getUserCollectDao().queryBuilder().where(UserCollectDao.Properties.Work_id.eq(workInfo.getId())).unique();

        if (unique != null){
            LogUtil.d(unique.toString());
            binding.collecte.setImageDrawable(activity.getResources().getDrawable(R.drawable.collected));
            isCollect = true;
        }

        binding.workTitle.setText(workInfo.getWorkTitle());
        binding.workMoney.setText(workInfo.getMoney());
        binding.workEndWay.setText(workInfo.getEnd_way());
        binding.workCity.setText(workInfo.getCity());
        binding.workContants.setText(workInfo.getContacts());
        binding.workPhone.setText(workInfo.getContactsWay());
        binding.workCreateTime.setText(workInfo.getCreateTime());
        binding.workAddress.setText(workInfo.getAddress());
        binding.workContext.setText(workInfo.getWorkContext());
        if (workInfo.getWorkStatu() == 1) {
            binding.tdcode.setEnabled(false);
            binding.collecte.setEnabled(false);
            binding.submitSend.setText("已下架，无法报名");
            binding.submitSend.setEnabled(false);
        }
        binding.collecte.setOnClickListener(this);
        binding.tdcode.setOnClickListener(this);
        binding.userBack.setOnClickListener(this);
        binding.weichat.setOnClickListener(this);
    }

    @Override
    public void notifyUpdate(int code) {

        switch (code) {
            case 0:
            case 1:{
                binding.netBar.setVisibility(code);
            }
            break;
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.user_back:
            {
                activity.finish();
            }
            break;
            case R.id.tdcode:
            {
                DialogUtil.showQRCode(activity,"test");
            }
            break;
            case R.id.collecte:
            {
                if (!SpCommonUtils.getIsLogin()) {
                    (new LoginActivity()).inToActivity(activity);
                }else {
                    if(!isCollect){
                        binding.collecte.setImageDrawable(activity.getResources().getDrawable(R.drawable.collected));
                        isCollect = true;
                    }else{
                        binding.collecte.setImageDrawable(activity.getResources().getDrawable(R.drawable.collecte));
                        isCollect = false;
                    }
                    sendCollected(isCollect);
                }
            }
            break;
            case R.id.message_chat:
            {

            }
            break;
            case R.id.weichat:
            {
                if (!SpCommonUtils.getIsLogin()) {
                    (new LoginActivity()).inToActivity(activity);
                }else {
                    (new SendRecordActivity()).inToActivity(activity);
                }
            }
            break;
            case R.id.submit_send:
            {


            }
            break;
        }
    }

    private void sendCollected(final boolean isCollect) {
        activity.show("正在加载...");
        HttpRequest.request(HttpRequest.builder().create(CollectWorkInfoServer.class).setCollectWork(SpCommonUtils.getUserId(),workInfo.getId(),isCollect),
                new RequestResult() {
                    @Override
                    public void success(ResultModel resultModel) {
                        activity.dismiss();
                        LogUtil.d(" 收藏兼职信息----------ResultModel：" + resultModel.toString());
                        if (resultModel.code == 200) {
                            if (isCollect)
                                activity.showResult(binding.rly,"收藏成功");
                            else
                                activity.showResult(binding.rly,"取消收藏");
                        }else {
                            activity.showResult(binding.rly,"收藏失败");
                        }
                    }

                    @Override
                    public void fail(Throwable e) {
                        activity.dismiss();
                        activity.showResult(binding.rly,"收藏异常");
                    }
                }, true);
    }
}
