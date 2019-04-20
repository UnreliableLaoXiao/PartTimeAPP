package com.schoolpartime.schoolpartime.presenter;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SearchView;
import com.schoolpartime.schoolpartime.R;
import com.schoolpartime.schoolpartime.SuperActivity;
import com.schoolpartime.schoolpartime.databinding.ActivitySearchBinding;
import com.schoolpartime.schoolpartime.util.sp.SpCommonUtils;
import java.util.ArrayList;
import java.util.List;

import androidx.databinding.ViewDataBinding;

public class SearchPre implements Presenter, View.OnClickListener {

    SuperActivity activity;
    ActivitySearchBinding binding;
    private boolean isLogin;

    @Override
    public void attach(ViewDataBinding binding, SuperActivity activity) {
        this.binding = (ActivitySearchBinding) binding;
        this.activity = activity;
        init();

    }



    private void init() {




        isLogin = SpCommonUtils.getIsLogin();
        binding.cancle.setOnClickListener(this);
        if(isLogin) {
            binding.listView.setAdapter(new ArrayAdapter<>(activity, android.R.layout.simple_list_item_1, new ArrayList<Object>()));
            binding.listView.setTextFilterEnabled(true);
        }
//        binding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                if(isLogin) {
//                    /**
//                     * 得到历史纪录
//                     */
//                }
//                activity.showResult(binding.lly,"您搜索的是："+query);
//                return true;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                return false;
//            }
//        });
    }



    @Override
    public void notifyUpdate(int code) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

//            case R.id.city:
//            {
////                startActivityForResult(new Intent(SearchActivity.this, CityPickerActivity.class), REQUEST_CODE_PICK_CITY);
//            }
//            break;
//            case R.id.cancle:
//            {
//                activity.finish();
//            }
//            break;
        }
    }
}
