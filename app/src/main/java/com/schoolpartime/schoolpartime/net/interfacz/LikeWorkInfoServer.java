package com.schoolpartime.schoolpartime.net.interfacz;

import com.schoolpartime.schoolpartime.entity.WorkInfo;
import com.schoolpartime.schoolpartime.entity.baseModel.ResultModel;

import java.util.List;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

public interface LikeWorkInfoServer {

    @FormUrlEncoded
    @POST("/work/likework")
    Observable<ResultModel<List<WorkInfo>>> getLikeWorkInfoNormal(@Field(value = "id") long id);

}
