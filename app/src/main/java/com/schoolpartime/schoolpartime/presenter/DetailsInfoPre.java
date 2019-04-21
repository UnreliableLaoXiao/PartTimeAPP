package com.schoolpartime.schoolpartime.presenter;

import android.view.View;
import com.schoolpartime.schoolpartime.R;
import com.schoolpartime.schoolpartime.SuperActivity;
import com.schoolpartime.schoolpartime.databinding.ActivityDetailsBinding;
import com.schoolpartime.schoolpartime.dialog.DialogUtil;
import com.schoolpartime.schoolpartime.entity.WorkInfo;

import androidx.databinding.ViewDataBinding;

public class DetailsInfoPre implements Presenter, View.OnClickListener {

    ActivityDetailsBinding binding;
    SuperActivity activity;
    boolean isCollect = false;

    @Override
    public void attach(ViewDataBinding binding, SuperActivity activity) {
        this.binding = (ActivityDetailsBinding) binding;
        this.activity = activity;
        init();
    }

    private void init() {
        WorkInfo workInfo = activity.getIntent().getParcelableExtra("workinfo");

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
                if(!isCollect){
                    binding.collecte.setImageDrawable(activity.getResources().getDrawable(R.drawable.collected));
                    isCollect = true;
                }else{
                    binding.collecte.setImageDrawable(activity.getResources().getDrawable(R.drawable.collecte));
                    isCollect = false;
                }
            }
            break;
            case R.id.message_chat:
            {

            }
            break;
            case R.id.weichat:
            {

            }
            break;
            case R.id.submit_send:
            {

            }
            break;
        }
    }
}
