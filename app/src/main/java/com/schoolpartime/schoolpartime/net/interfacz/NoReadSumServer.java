package com.schoolpartime.schoolpartime.net.interfacz;

import com.schoolpartime.schoolpartime.entity.baseModel.ResultModel;

import java.util.Map;

import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

public interface NoReadSumServer {

    @FormUrlEncoded
    @POST("/getAllNoread")
    Observable<ResultModel> getAllNoread(@Field(value = "id") long id);

}
