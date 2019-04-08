package com.schoolpartime.schoolpartime.presenter;

import android.app.Activity;
import android.content.DialogInterface;
import android.view.View;
import android.widget.AdapterView;

import com.schoolpartime.schoolpartime.R;
import com.schoolpartime.schoolpartime.SchoolPartimeApplication;
import com.schoolpartime.schoolpartime.SuperActivity;
import com.schoolpartime.schoolpartime.activity.BeginBossActivity;
import com.schoolpartime.schoolpartime.activity.BossInfoActivity;
import com.schoolpartime.schoolpartime.activity.LoginActivity;
import com.schoolpartime.schoolpartime.activity.MyCollectionActivity;
import com.schoolpartime.schoolpartime.activity.MyMessagesActivity;
import com.schoolpartime.schoolpartime.activity.SendRecordActivity;
import com.schoolpartime.schoolpartime.activity.SettingActivity;
import com.schoolpartime.schoolpartime.activity.UserInfoActivity;
import com.schoolpartime.schoolpartime.adapter.MySelfListAdapter;
import com.schoolpartime.schoolpartime.event.NumberController;
import com.schoolpartime.schoolpartime.chat.WebClient;
import com.schoolpartime.schoolpartime.databinding.FragmentUserBinding;
import com.schoolpartime.schoolpartime.dialog.DialogUtil;
import com.schoolpartime.schoolpartime.entity.DataModel;
import com.schoolpartime.schoolpartime.util.LogUtil;
import com.schoolpartime.schoolpartime.util.sp.SpCommonUtils;

import java.util.ArrayList;
import java.util.List;

import androidx.databinding.ViewDataBinding;

public class FrgUserPre implements Presenter, View.OnClickListener, WebClient.NotifyMessage, NumberController.NotifyNumber {

    private Activity activity;
    private FragmentUserBinding binding;
    private boolean isLogin;

    WebClient webClient;
    NumberController controller;
    private MySelfListAdapter adapter;
    private List<DataModel> list;

    @Override
    public void attach(ViewDataBinding binding, SuperActivity activity) {
        this.binding = (FragmentUserBinding) binding;
        this.activity = activity;
        init();
    }

    private void init() {
        isLogin = SpCommonUtils.getIsLogin();
//        ChangeWeigetEnable(isLogin);
        list = new ArrayList<>();
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
        if (SchoolPartimeApplication.getmDaoSession().getUserInfoDao().loadAll().size() > 0)
            binding.tvLogin.setText(isLogin?SchoolPartimeApplication.getmDaoSession().getUserInfoDao().loadAll().get(0).getUsername() : "点击登陆");
        adapter = new MySelfListAdapter(list, activity);
        binding.listUser.setAdapter(adapter);
        binding.listUser.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (isLogin || position == 4) {
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
        });
        binding.tvLogin.setOnClickListener(this);
        binding.btIntoBoss.setOnClickListener(this);
        binding.bossInfo.setOnClickListener(this);
    }

    @Override
    public void notifyUpdate(int code) {
        switch (code) {
            case 0: {
                    isLogin = SpCommonUtils.getIsLogin();
                    ChangeWeigetEnable(isLogin);
            }
            break;
            case 8: {
                if (SpCommonUtils.getUserType() == 3) {
                    binding.boss.setVisibility(View.VISIBLE);
                    binding.btIntoBoss.setVisibility(View.GONE);
                }
            }
            break;
            case 6: {
                webClient.removeNotity(this);
                controller.removeNotity(this);
            }
            break;
        }

    }

    /**
     * 登录成功时会被调用
     *
     * @param flag
     */
    private void ChangeWeigetEnable(boolean flag) {
        if (flag){
            webClient = WebClient.getInstance();
            controller = NumberController.getInstance();

            if (webClient != null)
                webClient.addNotity(this);
            controller.addNotity(this);
        }
        if (SchoolPartimeApplication.getmDaoSession().getUserInfoDao().loadAll().size() > 0)
            binding.tvLogin.setText(flag?SchoolPartimeApplication.getmDaoSession().getUserInfoDao().loadAll().get(0).getUsername() : "点击登陆");
        binding.tvLogin.setEnabled(flag);
        binding.btIntoBoss.setVisibility(flag ? View.VISIBLE : View.GONE);
        if (SpCommonUtils.getUserType() == 3 && flag) {
            binding.boss.setVisibility(View.VISIBLE);
            binding.btIntoBoss.setVisibility(View.GONE);
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
        }

    }

    @Override
    public void notify(String mes) {
        LogUtil.d("Frag 收到消息通知");
        change(1);
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
}
