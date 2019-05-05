package com.schoolpartime.schoolpartime.net.interfacz;

import com.schoolpartime.schoolpartime.entity.WorkInfo;
import com.schoolpartime.schoolpartime.entity.baseModel.ResultModel;

import java.util.ArrayList;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

public interface GetRequestServer {

    @FormUrlEncoded
    @POST("/work/getrequest")
    Observable<ResultModel<ArrayList<WorkInfo>>> getRequest(@Field(value = "userid") long userid,@Field(value = "statu") int statu);

}
