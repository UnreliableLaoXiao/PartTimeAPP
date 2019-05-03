package com.schoolpartime.schoolpartime.net.interfacz;

import com.schoolpartime.schoolpartime.entity.baseModel.ResultModel;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

public interface DelCollectWorkInfosServer {

    @FormUrlEncoded
    @POST("/work/delcollectworkinfo")
    Observable<ResultModel<String>> delCollectWorkInfo(@Field(value = "userid") long userid);

}
