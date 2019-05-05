package com.schoolpartime.schoolpartime.presenter;

import android.app.Activity;
import android.content.DialogInterface;
import android.view.View;
import android.widget.AdapterView;

import com.schoolpartime.dao.entity.UserInfo;
import com.schoolpartime.schoolpartime.R;
import com.schoolpartime.schoolpartime.SchoolPartimeApplication;
import com.schoolpartime.schoolpartime.SuperActivity;
import com.schoolpartime.schoolpartime.activity.BeginBossActivity;
import com.schoolpartime.schoolpartime.activity.BossInfoActivity;
import com.schoolpartime.schoolpartime.activity.BossRequestActivity;
import com.schoolpartime.schoolpartime.activity.BossSendActivity;
import com.schoolpartime.schoolpartime.activity.LoginActivity;
import com.schoolpartime.schoolpartime.activity.MyCollectionActivity;
import com.schoolpartime.schoolpartime.activity.MyMessagesActivity;
import com.schoolpartime.schoolpartime.activity.SendRecordActivity;
import com.schoolpartime.schoolpartime.activity.SettingActivity;
import com.schoolpartime.schoolpartime.activity.UserInfoActivity;
import com.schoolpartime.schoolpartime.adapter.MySelfListAdapter;
import com.schoolpartime.schoolpartime.entity.baseModel.ResultModel;
import com.schoolpartime.schoolpartime.event.LoginStateController;
import com.schoolpartime.schoolpartime.event.NumberController;
import com.schoolpartime.schoolpartime.databinding.FragmentUserBinding;
import com.schoolpartime.schoolpartime.dialog.DialogUtil;
import com.schoolpartime.schoolpartime.entity.DataModel;
import com.schoolpartime.schoolpartime.net.interfacz.UserInfoServer;
import com.schoolpartime.schoolpartime.net.request.HttpRequest;
import com.schoolpartime.schoolpartime.net.request.base.RequestResult;
import com.schoolpartime.schoolpartime.util.LogUtil;
import com.schoolpartime.schoolpartime.util.sp.SpCommonUtils;

import java.util.ArrayList;
import java.util.List;

import androidx.databinding.ViewDataBinding;

public class FrgUserPre implements Presenter, View.OnClickListener,  NumberController.NotifyNumber ,LoginStateController.NotifyLoginState,
        AdapterView.OnItemClickListener {

    private Activity activity;
    private FragmentUserBinding binding;


    private MySelfListAdapter adapter;
    private List<DataModel> list = new ArrayList<>();

    @Override
    public void attach(ViewDataBinding binding, SuperActivity activity) {
        this.binding = (FragmentUserBinding) binding;
        this.activity = activity;
        init();
    }

    private void init() {
        NumberController.getInstance().addNotity(this);
        LoginStateController.getInstance().addNotity(this);
        initBaseData();
        adapter = new MySelfListAdapter(list, activity);
        binding.listUser.setAdapter(adapter);
        binding.listUser.setOnItemClickListener(this);
        binding.tvLogin.setOnClickListener(this);
        binding.btIntoBoss.setOnClickListener(this);
        binding.bossInfo.setOnClickListener(this);
        binding.bossSend.setOnClickListener(this);
        binding.bossRequest.setOnClickListener(this);
    }

    private void initBaseData() {
        DataModel user_selfinfo = new DataModel("个人信息", R.drawable.myinfo, 0);
        DataModel user_mymessage = new DataModel("我的消息", R.drawable.message_1, 0);
        DataModel user_mycollect = new DataModel("我的收藏", R.drawable.collect, 0);
        DataModel user_sendrecord = new DataModel("投递记录", R.drawable.record, 0);
        DataModel user_set = new DataModel("设置", R.drawable.set, 0);
        list.add(user_selfinfo);
        list.add(user_mymessage);
        list.add(user_mycollect);
        list.add(user_sendrecord);
        list.add(user_set);
    }

    @Override
    public void notifyUpdate(int code) {
        switch (code) {

            case 6:{
                NumberController.getInstance().removeNotity(this);
                LoginStateController.getInstance().removeNotity(this);
            }
            break;
            case 8: {
                if (SpCommonUtils.getUserType() == 3) {
                    binding.boss.setVisibility(View.VISIBLE);
                    binding.btIntoBoss.setVisibility(View.GONE);
                }
            }
            break;
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_login: {
                (new LoginActivity()).inToActivity(activity);
            }
            break;
            case R.id.bt_into_boss: {

                if (SpCommonUtils.getUserType() == 4) {
                    DialogUtil.selectDialog(activity, "提示：", "请耐心等待相关人员审核，通过后即可使用商家功能。", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                } else {
                    (new BeginBossActivity()).inToActivity(activity);
                }
            }
            break;
            case R.id.boss_info: {
                (new BossInfoActivity()).inToActivity(activity);
            }
            break;
            case R.id.boss_send: {
                (new BossSendActivity()).inToActivity(activity);
            }
            break;
            case R.id.boss_request: {
                (new BossRequestActivity()).inToActivity(activity);
            }
            break;
        }

    }

    @Override
    public void change(int change) {
        LogUtil.d("Frag number is " + change);
        list.get(1).number += change;
        LogUtil.d("Frag 更新数量");
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void loginStateChange(boolean state) {
        if (state)
            getUserInfo();
        else
            binding.tvLogin.setText("点击登陆");
        binding.tvLogin.setEnabled(!state);

        if (SpCommonUtils.getUserType() == 3) {
            binding.boss.setVisibility(View.VISIBLE);
            binding.btIntoBoss.setVisibility(View.GONE);
        }else{
            binding.btIntoBoss.setVisibility(state ? View.VISIBLE : View.GONE);
        }

        if (!state){
            binding.boss.setVisibility(View.GONE);
            list.get(1).number = 0;
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    adapter.notifyDataSetChanged();
                }
            });
        }

    }

    private void getUserInfo() {
        LogUtil.d("请求登录用户信息");
        HttpRequest.request(HttpRequest.builder().create(UserInfoServer.class).
                        getUserInfoServer(SpCommonUtils.getUserId()),
                new RequestResult() {
                    @Override
                    public void success(ResultModel resultModel) {
                        if (resultModel.code == 200) {
                            LogUtil.d("请求登录用户信息成功");
                            UserInfo userInfo = (UserInfo) resultModel.data;
                            SchoolPartimeApplication.getmDaoSession().getUserInfoDao().insert(userInfo);
                            binding.tvLogin.setText(userInfo.getUsername());
                            LogUtil.d("数据库更新用户信息----------成功：" + userInfo.toString());
                        } else {
                            LogUtil.d("请求登录用户信息失败");
                            SpCommonUtils.setIsLogin(false);
                        }
                    }

                    @Override
                    public void fail(Throwable e) {
                        LogUtil.d("请求登录用户信息异常",e);
                        SpCommonUtils.setIsLogin(false);
                    }
                }, true);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (SpCommonUtils.getIsLogin() || position == 4) {
            switch (position) {
                case 0: {
                    (new UserInfoActivity()).inToActivity(activity);
                }
                break;
                case 1: {
                    (new MyMessagesActivity()).inToActivity(activity);
                }
                break;
                case 2: {
                    (new MyCollectionActivity()).inToActivity(activity);
                }
                break;
                case 3: {
                    (new SendRecordActivity()).inToActivity(activity);
                }
                break;
                case 4: {
                    (new SettingActivity()).inToActivity(activity);
                }
                break;
            }
        }
    }
}
