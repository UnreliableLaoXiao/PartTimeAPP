package com.schoolpartime.schoolpartime.net.interfacz;

import com.schoolpartime.dao.entity.UserCollect;
import com.schoolpartime.schoolpartime.entity.baseModel.ResultModel;

import java.util.ArrayList;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

public interface RequestWorkServer {

    @FormUrlEncoded
    @POST("/work/addRequest")
    Observable<ResultModel<String>> requestWork(@Field(value = "userid") long userid,@Field(value = "workid") long work_id);

}
