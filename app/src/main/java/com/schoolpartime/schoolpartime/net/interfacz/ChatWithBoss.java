package com.schoolpartime.schoolpartime.net.interfacz;

import com.schoolpartime.schoolpartime.entity.ChatRecord;
import com.schoolpartime.schoolpartime.entity.baseModel.ResultModel;

import java.util.List;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

public interface ChatWithBoss {

    @FormUrlEncoded
    @POST("/chatwithboss")
    Observable<ResultModel<ChatRecord>> getWithBossChatRecord(@Field(value = "userid") long userid,@Field(value = "bossid") long bossid);

}
