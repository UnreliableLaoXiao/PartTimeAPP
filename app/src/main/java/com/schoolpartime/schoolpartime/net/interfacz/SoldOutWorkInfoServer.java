package com.schoolpartime.schoolpartime.net.interfacz;

import com.schoolpartime.schoolpartime.entity.baseModel.ResultModel;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

public interface SoldOutWorkInfoServer {

    @FormUrlEncoded
    @POST("/work/soldoutwork")
    Observable<ResultModel<String>> soldOutWorkInfo(@Field(value = "id") long id);

}
