package com.schoolpartime.schoolpartime.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;

import androidx.appcompat.widget.SearchView;
import androidx.core.view.MenuItemCompat;
import androidx.databinding.DataBindingUtil;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.SimpleAdapter;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.google.zxing.integration.android.IntentIntegrator;
import com.schoolpartime.dao.entity.SearchTitle;
import com.schoolpartime.schoolpartime.R;
import com.schoolpartime.schoolpartime.SchoolPartimeApplication;
import com.schoolpartime.schoolpartime.SuperActivity;
import com.schoolpartime.schoolpartime.databinding.ActivityMianBinding;
import com.schoolpartime.schoolpartime.fragment.MainFragment;
import com.schoolpartime.schoolpartime.fragment.SearchFragment;
import com.schoolpartime.schoolpartime.fragment.UserFragment;
import com.schoolpartime.schoolpartime.presenter.MainPre;
import com.schoolpartime.schoolpartime.util.LogUtil;
import com.schoolpartime.schoolpartime.util.sp.SpCommonUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 兼职的主界面
 * <p>
 * 效果的需要导致，该APP运行的最低要求是API >= 21 ， 即安卓5.0以上
 */
public class MainActivity extends SuperActivity {

    private MainPre pre = new MainPre();
    private SearchView mSearchView;
    ActivityMianBinding binding;
    List<Map<String, String>> list = new ArrayList<>();

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_mian);
        SpCommonUtils.setOnceStart(true);
        pre.attach(binding, this);
    }

    public static ArrayList<Fragment> getFragmentList() {
        ArrayList<Fragment> mFragmentList = new ArrayList<>();
        Fragment mainFragment = new MainFragment();
        Fragment searchFragment = new SearchFragment();
        Fragment userFragment = new UserFragment();
        mFragmentList.add(mainFragment);
        mFragmentList.add(searchFragment);
        mFragmentList.add(userFragment);
        return mFragmentList;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar, menu);
        MenuItem searchItem = menu.findItem(R.id.menu_search);
        //通过MenuItem得到SearchView
        mSearchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        //设置最大宽度
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
//        SCREEN_HEIGHT = dm.heightPixels;
        mSearchView.setMaxWidth(dm.widthPixels);
        //设置是否显示搜索框展开时的提交按钮
//        mSearchView.setSubmitButtonEnabled(true);
        //设置输入框提示语
        mSearchView.setQueryHint("hint");


        //搜索框文字变化监听
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                binding.showSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        pre.showResult("你选择的是" + list.get(position).get("title"));
                        mSearchView.setQuery(list.get(position).get("title"), true);
                    }
                });

                if (s.length() >= 2) {
                    LogUtil.d("开始显示");
                    binding.showSearch.setVisibility(View.VISIBLE);
                    binding.showSearch.setAdapter(new SimpleAdapter(MainActivity.this, queryData(s),
                            R.layout.item_normal, new String[]{"title"}, new int[]{R.id.text1}));
                } else {
                    binding.showSearch.setVisibility(View.GONE);
                }


                return false;
            }
        });

        return true;
    }

    private List<Map<String, String>> queryData(String s) {
//        SELECT _id1 AS _id ,name1 , bir FROM chi"
        String sql = "select _id as _id , title from t_search_title where title like '%" + s + "%'";
        List<SearchTitle> searchTitles = SchoolPartimeApplication.getmDaoSession().getSearchTitleDao().loadAll();
        list.clear();
        for (SearchTitle searchTitle : searchTitles) {
            if (searchTitle.getTitle().contains(s)) {
                Map<String, String> map = new HashMap<>();
                map.put("title", searchTitle.getTitle());
                LogUtil.d("存储数据");
                list.add(map);
            }
        }
        return list;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.scan: {
                int permission = ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA);
                if (permission != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CAMERA}, 1);
                } else {
                    Scan();
                }
            }
            break;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 1 && grantResults[0] == 0) {
            Scan();
        }
    }

    private void Scan() {
        new IntentIntegrator(this).initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 扫描二维码/条码回传
//        if (requestCode == ScanActivity.REQUEST_CODE && resultCode == ScanActivity.RESPONSE_CODE) {
//            if (data != null) {
//                String content = data.getStringExtra(ScanActivity.DATA);
//                XhLogUtil.d("扫描结果为：" + content);
//            }
//        }
    }

    @Override
    public void onBackPressed() {
        mSearchView.onActionViewCollapsed();
        BackExit();
    }

    @Override
    protected void onResume() {
        pre.notifyUpdate(7);
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        pre.notifyUpdate(8);
        super.onDestroy();
    }
}
