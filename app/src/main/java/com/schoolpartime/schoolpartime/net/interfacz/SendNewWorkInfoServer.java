package com.schoolpartime.schoolpartime.net.interfacz;

import com.schoolpartime.schoolpartime.entity.baseModel.ResultModel;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

public interface SendNewWorkInfoServer {

    @FormUrlEncoded
    @POST("/work/newwork")
    Observable<ResultModel<String>> addWorkInfo(@Field(value = "data") String data);

}
