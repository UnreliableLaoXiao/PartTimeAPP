package com.schoolpartime.schoolpartime.presenter;

import android.os.Bundle;
import android.view.View;

import com.schoolpartime.dao.RequestWorkDao;
import com.schoolpartime.dao.UserCollectDao;
import com.schoolpartime.dao.entity.RequestWork;
import com.schoolpartime.dao.entity.UserCollect;
import com.schoolpartime.schoolpartime.R;
import com.schoolpartime.schoolpartime.SchoolPartimeApplication;
import com.schoolpartime.schoolpartime.SuperActivity;
import com.schoolpartime.schoolpartime.activity.LoginActivity;
import com.schoolpartime.schoolpartime.activity.MyMessagesActivity;
import com.schoolpartime.schoolpartime.activity.RealChatActivity;
import com.schoolpartime.schoolpartime.activity.SendRecordActivity;
import com.schoolpartime.schoolpartime.databinding.ActivityDetailsBinding;
import com.schoolpartime.schoolpartime.dialog.DialogUtil;
import com.schoolpartime.schoolpartime.entity.ChatRecord;
import com.schoolpartime.schoolpartime.entity.WorkInfo;
import com.schoolpartime.schoolpartime.entity.baseModel.ResultModel;
import com.schoolpartime.schoolpartime.net.interfacz.ChatWithBoss;
import com.schoolpartime.schoolpartime.net.interfacz.CollectWorkInfoServer;
import com.schoolpartime.schoolpartime.net.interfacz.RequestWorkServer;
import com.schoolpartime.schoolpartime.net.request.HttpRequest;
import com.schoolpartime.schoolpartime.net.request.base.RequestResult;
import com.schoolpartime.schoolpartime.util.LogUtil;
import com.schoolpartime.schoolpartime.util.sp.SpCommonUtils;

import java.util.List;

import androidx.databinding.ViewDataBinding;

public class DetailsInfoPre implements Presenter, View.OnClickListener {

    ActivityDetailsBinding binding;
    SuperActivity activity;
    boolean isCollect = false;
    private WorkInfo workInfo;
    private UserCollect unique;
    private RequestWork requestWork;

    @Override
    public void attach(ViewDataBinding binding, SuperActivity activity) {
        this.binding = (ActivityDetailsBinding) binding;
        this.activity = activity;
        init();
    }

    private void init() {
        workInfo = activity.getIntent().getParcelableExtra("workinfo");

        requestWork = SchoolPartimeApplication.getmDaoSession().getRequestWorkDao().queryBuilder().where(RequestWorkDao.Properties.Work_id.eq(workInfo.getId())).unique();

        this.unique = SchoolPartimeApplication.getmDaoSession().getUserCollectDao().queryBuilder().where(UserCollectDao.Properties.Work_id.eq(workInfo.getId())).unique();

        if (this.unique != null){
            LogUtil.d(this.unique.toString());
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

        if (requestWork != null) {
            binding.submitSend.setText("已申请");
            binding.submitSend.setEnabled(false);
        }
        binding.collecte.setOnClickListener(this);
        binding.tdcode.setOnClickListener(this);
        binding.userBack.setOnClickListener(this);
        binding.weichat.setOnClickListener(this);
        binding.submitSend.setOnClickListener(this);
        binding.messageChat.setOnClickListener(this);
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
                if (!SpCommonUtils.getIsLogin()) {
                    (new LoginActivity()).inToActivity(activity);
                }else {
                    (new MyMessagesActivity()).inToActivity(activity);
                }
            }
            break;
            case R.id.weichat:
            {
                if (!SpCommonUtils.getIsLogin()) {
                    (new LoginActivity()).inToActivity(activity);
                }else {
                    startChatWithBoss();
                }
            }
            break;
            case R.id.submit_send:
            {
                /**
                 * 发送申请请求
                 */
                if (!SpCommonUtils.getIsLogin()){
                    (new LoginActivity()).inToActivity(activity);
                } else {
                    sendWorkRequest();
                }

            }
            break;
        }
    }

    private void startChatWithBoss() {
        activity.show("正在开启聊天界面...");
        LogUtil.d(""+workInfo.getBossId() + " userid = " + SpCommonUtils.getUserId());
        HttpRequest.request(HttpRequest.builder().create(ChatWithBoss.class).getWithBossChatRecord(
                SpCommonUtils.getUserId(),workInfo.getBossId()),
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

    private void sendWorkRequest() {
        activity.show("正在申请...");
        HttpRequest.request(HttpRequest.builder().create(RequestWorkServer.class).requestWork(SpCommonUtils.getUserId(),workInfo.getId()),
                new RequestResult() {
                    @Override
                    public void success(ResultModel resultModel) {
                        activity.dismiss();
                        LogUtil.d(" 申请兼职----------ResultModel：" + resultModel.toString());
                        if (resultModel.code == 200) {
                            activity.showResult(binding.rly,"申请成功");
                            SchoolPartimeApplication.getmDaoSession().getRequestWorkDao().insert(new RequestWork(0,SpCommonUtils.getUserId(),workInfo.getId(),0));
                        }else {
                              activity.showResult(binding.rly,"申请失败");
                        }
                    }

                    @Override
                    public void fail(Throwable e) {
                        activity.dismiss();
                        activity.showResult(binding.rly,"申请异常");
                    }
                }, true);
    }

    private void sendCollected(final boolean isCollect) {
        activity.show("正在加载...");
        if (unique == null)
            unique = new UserCollect();
        unique.setId(0);
        unique.setUser_id(SpCommonUtils.getUserId());
        unique.setWork_id(workInfo.getId());
        HttpRequest.request(HttpRequest.builder().create(CollectWorkInfoServer.class).setCollectWork(SpCommonUtils.getUserId(),workInfo.getId(),isCollect),
                new RequestResult() {
                    @Override
                    public void success(ResultModel resultModel) {
                        activity.dismiss();
                        LogUtil.d(" 收藏兼职信息----------ResultModel：" + resultModel.toString());
                        if (resultModel.code == 200) {
                            if (isCollect){
                                activity.showResult(binding.rly,"收藏成功");
                                SchoolPartimeApplication.getmDaoSession().getUserCollectDao().insert(unique);
                            }
                            else{
                                activity.showResult(binding.rly,"取消收藏");
                            }
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
