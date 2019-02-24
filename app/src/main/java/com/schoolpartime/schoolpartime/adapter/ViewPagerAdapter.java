package com.schoolpartime.schoolpartime.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import java.util.List;

/**
 * Created by Auser on 2018/5/29.
 */

public class ViewPagerAdapter extends PagerAdapter {
    List<View> views; //用于放置导航页
    private Context context;

    public ViewPagerAdapter(List<View> views, Context context) {
        super();
        this.views = views;
        this. context = context;
    }

    @Override
    public int getCount() {
        return views.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object o) {
        return view == o;
    }

    //   超出了缓存数量，销毁
    @Override
    public void destroyItem(View container, int position, Object object) {
        // TODO Auto-generated method stub
        ((ViewPager)container).removeView( views.get(position));
    }

    //   初始化显示加载图片
    @Override
    public Object instantiateItem(View container, int position) {
        ((ViewPager)container).addView( views.get(position));
        return views.get(position);
    }
}
