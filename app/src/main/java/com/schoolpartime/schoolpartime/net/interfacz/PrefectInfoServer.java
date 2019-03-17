package com.schoolpartime.schoolpartime.net.interfacz;

import com.schoolpartime.dao.entity.UserInfo;
import com.schoolpartime.schoolpartime.entity.baseModel.ResultModel;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

public interface PrefectInfoServer {

    @FormUrlEncoded
    @POST("/prefectinfo")
    Observable<ResultModel<UserInfo>> prefectinfo(@Field(value = "data") String model);

}
