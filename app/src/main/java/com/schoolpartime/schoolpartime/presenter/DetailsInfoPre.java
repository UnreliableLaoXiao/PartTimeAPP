package com.schoolpartime.schoolpartime.presenter;

import android.view.View;
import com.schoolpartime.schoolpartime.R;
import com.schoolpartime.schoolpartime.SuperActivity;
import com.schoolpartime.schoolpartime.databinding.ActivityDetailsBinding;
import com.schoolpartime.schoolpartime.dialog.DialogUtil;

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
        binding.collecte.setOnClickListener(this);
        binding.tdcode.setOnClickListener(this);
        binding.userBack.setOnClickListener(this);
    }

    @Override
    public void notifyUpdate(int code) {

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
