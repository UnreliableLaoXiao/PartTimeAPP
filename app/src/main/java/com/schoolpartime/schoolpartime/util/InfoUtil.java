package com.schoolpartime.schoolpartime.util;

import com.schoolpartime.schoolpartime.SchoolPartimeApplication;
import com.schoolpartime.schoolpartime.event.LoginStateController;
import com.schoolpartime.schoolpartime.util.sp.SpCommonUtils;

public class InfoUtil {

    public static void clearAllInfo(){
        /**
         * 初始化所有信息
         */
        SpCommonUtils.setIsLogin(false);
        SpCommonUtils.setUserId(0);
        SpCommonUtils.setUserType(0);
        SchoolPartimeApplication.getmDaoSession().getUserInfoDao().deleteAll();
        SchoolPartimeApplication.getmDaoSession().getRequestWorkDao().deleteAll();
        SchoolPartimeApplication.getmDaoSession().getUserCollectDao().deleteAll();
        LogUtil.d("数据初始化--------》成功");
        LoginStateController.getInstance().NotifyAll(false);
    }

}
