package com.schoolpartime.schoolpartime.net.interfacz;

import com.schoolpartime.dao.entity.UserInfo;
import com.schoolpartime.schoolpartime.entity.baseModel.ResultModel;

import java.util.ArrayList;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

public interface GetRequestPeopleListServer {

    @FormUrlEncoded
    @POST("/work/getpeoplelist")
    Observable<ResultModel<ArrayList<UserInfo>>> getListPeople(@Field(value = "workid") long workid);

}
