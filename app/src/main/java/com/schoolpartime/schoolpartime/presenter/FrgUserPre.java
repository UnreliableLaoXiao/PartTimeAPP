package com.schoolpartime.schoolpartime.presenter;

import android.app.Activity;
import android.view.View;
import android.widget.AdapterView;

import com.schoolpartime.schoolpartime.R;
import com.schoolpartime.schoolpartime.SuperActivity;
import com.schoolpartime.schoolpartime.activity.LoginActivity;
import com.schoolpartime.schoolpartime.activity.MyCollectionActivity;
import com.schoolpartime.schoolpartime.activity.MyMessagesActivity;
import com.schoolpartime.schoolpartime.activity.SendRecordActivity;
import com.schoolpartime.schoolpartime.activity.SettingActivity;
import com.schoolpartime.schoolpartime.activity.UserInfoActivity;
import com.schoolpartime.schoolpartime.adapter.MySelfListAdapter;
import com.schoolpartime.schoolpartime.databinding.FragmentUserBinding;
import com.schoolpartime.schoolpartime.entity.DataModel;
import com.schoolpartime.schoolpartime.util.sp.SpCommonUtils;

import java.util.ArrayList;
import java.util.List;

import androidx.databinding.ViewDataBinding;

public class FrgUserPre implements Presenter, View.OnClickListener {

    /**
     *
     */
    private Activity activity;
    private FragmentUserBinding binding;
    private boolean isLogin;

    @Override
    public void attach(ViewDataBinding binding, SuperActivity activity) {
        this.binding = (FragmentUserBinding) binding;
        this.activity = activity;
        init();
    }

    private void init() {
        isLogin = SpCommonUtils.getIsLogin();
        List<DataModel> list = new ArrayList<>();
        DataModel user_selfinfo = new DataModel("个人信息",R.drawable.myinfo);
        DataModel user_mymessage = new DataModel("我的消息",R.drawable.message_1);
        DataModel user_mycollect = new DataModel("我的收藏",R.drawable.collect);
        DataModel user_sendrecord = new DataModel("投递记录",R.drawable.record);
        DataModel user_set = new DataModel("设置",R.drawable.set);
        list.add(user_selfinfo);
        list.add(user_mymessage);
        list.add(user_mycollect);
        list.add(user_sendrecord);
        list.add(user_set);

        binding.listUser.setAdapter(new MySelfListAdapter(list, activity));
        binding.listUser.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                 if (isLogin || position == 4) {
                     switch (position) {
                         case 0:{
                             (new UserInfoActivity()).inToActivity(activity);
                        }
                        break;
                         case 1:{
                             (new MyMessagesActivity()).inToActivity(activity);
                         }
                         break;
                         case 2:{
                             (new MyCollectionActivity()).inToActivity(activity);
                         }
                         break;
                         case 3:{
                             (new SendRecordActivity()).inToActivity(activity);
                         }
                         break;
                         case 4:{
                             (new SettingActivity()).inToActivity(activity);
                         }
                         break;
                     }
                 }
            }
        });


        binding.tvLogin.setOnClickListener(this);
    }

    @Override
    public void notifyUpdate(int code) {
        switch (code) {
            case 0:
            {
                isLogin = SpCommonUtils.getIsLogin();
                ChangeWeigetEnable(isLogin);
            }
            break;
        }

    }

    private void ChangeWeigetEnable(boolean flag) {
        binding.tvLogin.setText(flag?"xiaohei":"点击登陆");
        binding.tvLogin.setEnabled(!flag);
        binding.btIntoBoss.setVisibility(flag?View.VISIBLE:View.GONE);
    }

    @Override
    public void onClick(View v) {
        (new LoginActivity()).inToActivity(activity);
    }
}
