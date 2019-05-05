package com.schoolpartime.schoolpartime.net.interfacz;

import com.schoolpartime.schoolpartime.entity.baseModel.ResultModel;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

public interface GetLikeTypeServer {

    @FormUrlEncoded
    @POST("/work/getliketype")
    Observable<ResultModel<String>> getLikeType(@Field(value = "id") long id);



}
