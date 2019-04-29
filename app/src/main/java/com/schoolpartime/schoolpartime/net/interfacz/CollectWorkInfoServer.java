package com.schoolpartime.schoolpartime.net.interfacz;

import com.schoolpartime.schoolpartime.entity.baseModel.ResultModel;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

public interface CollectWorkInfoServer {

    @FormUrlEncoded
    @POST("/work/collectwork")
    Observable<ResultModel<String>> setCollectWork(@Field(value = "userid") long userid, @Field(value = "workid") long workid ,@Field(value = "like") boolean like);

}
