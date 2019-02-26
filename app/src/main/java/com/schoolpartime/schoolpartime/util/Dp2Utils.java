package com.schoolpartime.schoolpartime.util;

import android.content.Context;

/**
 * Created by Auser on 2018/4/13.
 * 说明 ： 此方法用于安卓中尺寸单位的换算
 * dp ---> px
 * px ---> dp
 */

public class Dp2Utils {

    //dp转化为px
    public static int dp2px(float dipValue, Context activity) {
        final float scale = activity.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    //px转化为dp
    public static int px2dp(float pxValue, Context activity) {
        final float scale = activity.getResources().getDisplayMetrics().density;
        return (int) ((pxValue - 0.5f) / scale);
    }


}
