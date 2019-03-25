package com.schoolpartime.schoolpartime.presenter;

import androidx.databinding.ViewDataBinding;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import q.rorbin.badgeview.QBadgeView;

import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.RadioGroup;

import com.schoolpartime.schoolpartime.R;
import com.schoolpartime.schoolpartime.SuperActivity;
import com.schoolpartime.schoolpartime.activity.MainActivity;
import com.schoolpartime.schoolpartime.databinding.ActivityMianBinding;
import com.schoolpartime.schoolpartime.fragment.MainFragment;

import java.util.ArrayList;

public class MainPre implements Presenter, View.OnClickListener, RadioGroup.OnCheckedChangeListener {

    private ActivityMianBinding binding;
    private SuperActivity activity;
    private FragmentManager mFm;
    private ArrayList<Fragment> mFragmentList = new ArrayList<>();
    private String[] mFragmentTagList = {"主页", "搜索", "我的"};
    private Fragment mCurrentFragmen = null; // 记录当前显示的Fragment
    private int index = 0;

    @Override
    public void attach(ViewDataBinding binding, SuperActivity activity) {
        this.binding = (ActivityMianBinding)binding;
        this.activity = activity;
        init();
    }

    private void init() {

        mFragmentList = MainActivity.getFragmentList();
        setDraws();
        binding.mainToobar.setTitle(mFragmentTagList[0]);
        activity.setSupportActionBar(binding.mainToobar);
        binding.mainToobar.setOnClickListener(this);
        mCurrentFragmen = mFragmentList.get(0);
        Log.d("TEST", "Click1: "+mCurrentFragmen);
        // 初始化首次进入时的Fragment
        mFm = activity.getSupportFragmentManager();
        FragmentTransaction transaction = mFm.beginTransaction();
        transaction.add(R.id.fl_show, mCurrentFragmen, mFragmentTagList[0]);
        transaction.commitAllowingStateLoss();
        binding.mainGp.setOnCheckedChangeListener(this);
        binding.netBar.setOnClickListener(this);
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

    private void setDraws() {
        Drawable drawable_home = activity.getResources().getDrawable(R.drawable.main_radio_type_home);
        //当这个图片被绘制时，给他绑定一个矩形 ltrb规定这个矩形
        drawable_home.setBounds(0, 0, 60, 60);
        //设置图片在文字的哪个方向
        binding.mainRbHome.setCompoundDrawables(null, drawable_home, null, null);

        Drawable drawable_finds = activity.getResources().getDrawable(R.drawable.main_radio_type_find);
        //当这个图片被绘制时，给他绑定一个矩形 ltrb规定这个矩形
        drawable_finds.setBounds(0, 0, 60, 60);
        //设置图片在文字的哪个方向
        binding.mainRbFind.setCompoundDrawables(null, drawable_finds, null, null);

        Drawable drawable_news = activity.getResources().getDrawable(R.drawable.main_radio_type_message);
        //当这个图片被绘制时，给他绑定一个矩形 ltrb规定这个矩形
        drawable_news.setBounds(0, 0, 60, 60);
        //设置图片在文字的哪个方向
        binding.mainRbMessage.setCompoundDrawables(null, drawable_news, null, null);
    }

    // 转换Fragment
    private void switchFragment(Fragment to, String tag) {
        if (to == null)
            return;
        binding.mainToobar.setTitle(tag);
        Log.i(MainActivity.class.getCanonicalName(), "switchFragment: ");
        if (mCurrentFragmen != to) {
            FragmentTransaction transaction = mFm.beginTransaction();
            if (!to.isAdded()) {
                // 没有添加过:
                // 隐藏当前的，添加新的，显示新的
                transaction.hide(mCurrentFragmen).add(R.id.fl_show, to, tag).show(to);
            } else {
                // 隐藏当前的，显示新的
                transaction.hide(mCurrentFragmen).show(to);
            }
            mCurrentFragmen = to;
            transaction.commitAllowingStateLoss();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.main_toobar:
            {
                if(index == 0) {
                    ((MainFragment)mCurrentFragmen).scroll_Start();
                }
            }
            break;
            case R.id.net_bar:
            {
//
            }
            break;
        }

    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.main_rb_home: {
                binding.mainRbHome.setTextColor(Color.argb(0xFF, 0x12, 0x96, 0xdb));
                binding.mainRbFind.setTextColor(Color.BLACK);
                binding.mainRbMessage.setTextColor(Color.BLACK);
                Log.d("TEST", "Click: "+mFragmentList.size());
                if (mFragmentList.size() >= 1)
                switchFragment(mFragmentList.get(0), mFragmentTagList[0]);
                index = 0;
            }
            break;
            case R.id.main_rb_find: {
                binding.mainRbFind.setTextColor(Color.argb(0xFF, 0x12, 0x96, 0xdb));
                binding.mainRbHome.setTextColor(Color.BLACK);
                binding.mainRbMessage.setTextColor(Color.BLACK);
                Log.d("TEST", "Click: "+mFragmentList.size());
                if (mFragmentList.size() >= 2)
                switchFragment(mFragmentList.get(1), mFragmentTagList[1]);
                index = 1;
            }
            break;
            case R.id.main_rb_message: {
                binding.mainRbMessage.setTextColor(Color.argb(0xFF, 0x12, 0x96, 0xdb));
                binding.mainRbFind.setTextColor(Color.BLACK);
                binding.mainRbHome.setTextColor(Color.BLACK);
                Log.d("TEST", "Click: "+mFragmentList.size());
                if (mFragmentList.size() >= 3)
                switchFragment(mFragmentList.get(2), mFragmentTagList[2]);
                index = 2;
            }
            break;
        }
    }
}
