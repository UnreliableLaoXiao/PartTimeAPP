package com.schoolpartime.schoolpartime.presenter;

import android.animation.ValueAnimator;
import androidx.databinding.ViewDataBinding;
import android.graphics.Color;
import androidx.core.widget.NestedScrollView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import com.jude.rollviewpager.hintview.ColorPointHintView;
import com.schoolpartime.schoolpartime.R;
import com.schoolpartime.schoolpartime.SuperActivity;
import com.schoolpartime.schoolpartime.activity.DetailsInfoActivity;
import com.schoolpartime.schoolpartime.adapter.LoopAdapter;
import com.schoolpartime.schoolpartime.adapter.RecyclerAdapter;
import com.schoolpartime.schoolpartime.databinding.FragmentMainBinding;
import com.schoolpartime.schoolpartime.util.sp.SpCommonUtils;
import com.zaaach.citypicker.CityPicker;
import com.zaaach.citypicker.adapter.OnPickListener;
import com.zaaach.citypicker.model.City;
import com.zaaach.citypicker.model.LocateState;
import com.zaaach.citypicker.model.LocatedCity;

public class FrgMainPre implements Presenter,NestedScrollView.OnScrollChangeListener, SwipeRefreshLayout.OnRefreshListener, View.OnClickListener {

    private FragmentMainBinding binding;
    private SuperActivity activity;
    private int mscrollY;
    private boolean isScroll = false;

    @Override
    public void attach(ViewDataBinding binding, SuperActivity activity) {
        this.binding = (FragmentMainBinding) binding;
        this.activity = activity;
        init();

    }

    private void init() {
        setRefresh();
        openRollPageView();
        binding.fgMainScroll.setOnScrollChangeListener(this);
        binding.rcyShow.setNestedScrollingEnabled(false);
        binding.rcyShow.setLayoutManager(new LinearLayoutManager(activity));
        binding.citypicker.setOnClickListener(this);
        binding.rcyShow.setAdapter(new RecyclerAdapter(activity, new RecyclerAdapter.MyOnItemClickListener() {   //设置adapter
            @Override
            public void onItemClick(View view) {
                /**
                 * 进入兼职详情页面
                 */
                (new DetailsInfoActivity()).inToActivity(activity);
            }

            @Override
            public void onItemLongClick(View view) {
                /**
                 * 长按操作兼职列表
                 */

            }
        }));
    }

    @Override
    public void notifyUpdate(int code) {
        switch (code) {
            case 0:{
                if (SpCommonUtils.getIsLogin()) {
                    binding.goLogin.setText("我订阅的职位");
                } else {
                    binding.goLogin.setText("立即登录");
                }
            }
            break;
            case 1:{
                if (isScroll)
                scroll_Start();
            }
            default:
                break;
        }
    }

    private void scroll_Start() {
        ValueAnimator valueAnimator = ValueAnimator.ofInt(mscrollY,0);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (int) animation.getAnimatedValue();
                binding.fgMainScroll.scrollTo(0,value);
            }
        });
        valueAnimator.setDuration(1000);
        valueAnimator.start();
    }

    private void setRefresh() {
        binding.swipeLayout.setOnRefreshListener(this);
        //设置样式刷新显示的位置
        binding.swipeLayout.setProgressViewOffset(true, -10, 50);
        binding.swipeLayout.setColorSchemeResources(R.color.swiperefresh_color1, R.color.swiperefresh_color2, R.color.swiperefresh_color3, R.color.swiperefresh_color4);
    }

    private void openRollPageView() {
        binding.rollviewpager.setAdapter(new LoopAdapter(binding.rollviewpager));
        binding.rollviewpager.setPlayDelay(3000);
        binding.rollviewpager.setHintView(new ColorPointHintView(activity, Color.RED, Color.GRAY));
    }

    @Override
    public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
        mscrollY = scrollY;
        if(scrollY > 800){
            isScroll = true;
        }
        if (scrollY < 20){
            isScroll = false;
        }
    }

    @Override
    public void onRefresh() {
        /**
         * 逻辑
         */
        binding.swipeLayout.setRefreshing(false);
    }

    @Override
    public void onClick(View v) {

        CityPicker.getInstance()
                .setFragmentManager(activity.getSupportFragmentManager())  //此方法必须调用
                .enableAnimation(true)  //启用动画效果
//                .setAnimationStyle(anim)  //自定义动画
                .setLocatedCity(new LocatedCity("杭州", "浙江", "101210101"))  //APP自身已定位的城市，默认为null（定位失败）
//                .setHotCities(hotCities)  //指定热门城市
                .setOnPickListener(new OnPickListener() {
                    @Override
                    public void onPick(int position, City data) {
                        binding.citypicker.setText("当前城市:"+data.getName());
                    }

                    @Override
                    public void onLocate() {
                        //开始定位，这里模拟一下定位
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                //定位完成之后更新数据
                                CityPicker.getInstance()
                                        .locateComplete(new LocatedCity("深圳", "广东", "101280601"), LocateState.SUCCESS);
                            }
                        }, 2000);
                    }
                })
                .show();
    }
}
