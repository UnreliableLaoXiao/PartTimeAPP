package com.schoolpartime.schoolpartime.presenter;

import android.animation.ValueAnimator;
import androidx.databinding.ViewDataBinding;
import android.graphics.Color;
import androidx.core.widget.NestedScrollView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.view.View;

import com.jude.rollviewpager.hintview.ColorPointHintView;
import com.schoolpartime.schoolpartime.R;
import com.schoolpartime.schoolpartime.SuperActivity;
import com.schoolpartime.schoolpartime.activity.DetailsInfoActivity;
import com.schoolpartime.schoolpartime.adapter.LoopAdapter;
import com.schoolpartime.schoolpartime.adapter.RecyclerAdapter;
import com.schoolpartime.schoolpartime.databinding.FragmentMainBinding;
import com.schoolpartime.schoolpartime.util.sp.SpCommonUtils;

public class FrgMainPre implements Presenter,NestedScrollView.OnScrollChangeListener, SwipeRefreshLayout.OnRefreshListener {

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
                if (SpCommonUtils.getIsLogin(activity)) {
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
}
