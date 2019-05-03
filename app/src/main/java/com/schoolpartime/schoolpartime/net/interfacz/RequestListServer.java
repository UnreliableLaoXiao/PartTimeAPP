package com.schoolpartime.schoolpartime.net.interfacz;

import com.schoolpartime.dao.entity.RequestWork;
import com.schoolpartime.schoolpartime.entity.baseModel.ResultModel;

import java.util.ArrayList;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

public interface RequestListServer {

    @FormUrlEncoded
    @POST("/work/requestlist")
    Observable<ResultModel<ArrayList<RequestWork>>> requestWorkList(@Field(value = "userid") long userid);

}
