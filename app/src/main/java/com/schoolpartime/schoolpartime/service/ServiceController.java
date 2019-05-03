package com.schoolpartime.schoolpartime.service;

import android.content.Intent;

import com.schoolpartime.schoolpartime.SchoolPartimeApplication;
import com.schoolpartime.schoolpartime.util.LogUtil;

public class ServiceController {

    public static void startBossCheckService(){
        Intent intent = new Intent();
        intent.setClass(SchoolPartimeApplication.getContext(), BossTestService.class);
        SchoolPartimeApplication.getContext().startService(intent);
    }

    public static void stopBossCheckService(){
        Intent intent = new Intent();
        intent.setClass(SchoolPartimeApplication.getContext(), BossTestService.class);
        SchoolPartimeApplication.getContext().stopService(intent);
    }

    public static void startWeiChatService(){
        Intent intent = new Intent();
        intent.setClass(SchoolPartimeApplication.getContext(), ChatMessageService.class);
        SchoolPartimeApplication.getContext().startService(intent);
    }

    public static void stopWeiChatService(){
        Intent intent = new Intent();
        intent.setClass(SchoolPartimeApplication.getContext(), ChatMessageService.class);
        SchoolPartimeApplication.getContext().stopService(intent);
    }


}
