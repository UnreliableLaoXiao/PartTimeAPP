package com.schoolpartime.schoolpartime.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.adapter.LoopPagerAdapter;
import com.schoolpartime.schoolpartime.R;

/**
 * Created by machenike on 2018/10/15.
 * 用于主页面图片轮询
 */

public class LoopAdapter extends LoopPagerAdapter {

    private int[] imgs = {
            R.drawable.play_1,
            R.drawable.play_2
    };

    public LoopAdapter(RollPagerView viewPager) {
        super(viewPager);
    }

    @Override
    public View getView(ViewGroup container, int position) {
        ImageView view = new ImageView(container.getContext());
        view.setImageResource(imgs[position]);
        view.setScaleType(ImageView.ScaleType.CENTER_CROP);
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        return view;
    }

    @Override
    public int getRealCount() {
        return imgs.length;
    }

}
