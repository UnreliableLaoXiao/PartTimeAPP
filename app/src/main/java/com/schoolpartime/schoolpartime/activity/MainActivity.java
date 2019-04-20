package com.schoolpartime.schoolpartime.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;

import androidx.appcompat.widget.SearchView;
import androidx.databinding.DataBindingUtil;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.google.zxing.integration.android.IntentIntegrator;
import com.schoolpartime.schoolpartime.R;
import com.schoolpartime.schoolpartime.SuperActivity;
import com.schoolpartime.schoolpartime.databinding.ActivityMianBinding;
import com.schoolpartime.schoolpartime.fragment.MainFragment;
import com.schoolpartime.schoolpartime.fragment.SearchFragment;
import com.schoolpartime.schoolpartime.fragment.UserFragment;
import com.schoolpartime.schoolpartime.presenter.MainPre;
import com.schoolpartime.schoolpartime.util.sp.SpCommonUtils;

import java.util.ArrayList;

/**
 * 兼职的主界面
 * <p>
 * 效果的需要导致，该APP运行的最低要求是API >= 21 ， 即安卓5.0以上
 */
public class MainActivity extends SuperActivity {

    private MainPre pre = new MainPre();
    ActivityMianBinding binding;

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


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 1 && grantResults[0] == 0) {
            pre.Scan();
        }
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
