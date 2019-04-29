package com.schoolpartime.schoolpartime.net.interfacz;

import com.schoolpartime.dao.entity.UserCollect;
import com.schoolpartime.schoolpartime.entity.baseModel.ResultModel;

import java.util.ArrayList;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

public interface GetCollectWorkInfoServer {

    @FormUrlEncoded
    @POST("/work/getcollectwork")
    Observable<ResultModel<ArrayList<UserCollect>>> getCollectWork(@Field(value = "userid") long userid);

}
