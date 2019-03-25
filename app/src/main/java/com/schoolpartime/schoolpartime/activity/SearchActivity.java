package com.schoolpartime.schoolpartime.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import com.schoolpartime.schoolpartime.R;
import com.schoolpartime.schoolpartime.SuperActivity;
import com.schoolpartime.schoolpartime.databinding.ActivitySearchBinding;
import com.schoolpartime.schoolpartime.presenter.Presenter;
import com.schoolpartime.schoolpartime.presenter.SearchPre;

import androidx.databinding.DataBindingUtil;

/**
 * Created by Auser on 2018/3/12.
 * 展现搜索的界面
 */

@SuppressLint("Registered")
public class SearchActivity extends SuperActivity {

    Presenter pre = new SearchPre();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivitySearchBinding binding = DataBindingUtil.setContentView(this,R.layout.activity_search);
        pre.attach(binding,this);
    }

    private static final int REQUEST_CODE_PICK_CITY = 0; //启动

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_PICK_CITY && resultCode == RESULT_OK){
            if (data != null){
//                mcity = data.getStringExtra(CityPickerActivity.KEY_PICKED_CITY)+"市";
//                city.setText(mcity);
            }
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
