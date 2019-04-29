package com.schoolpartime.schoolpartime.net.interfacz;

import com.schoolpartime.schoolpartime.entity.WorkInfo;
import com.schoolpartime.schoolpartime.entity.baseModel.ResultModel;

import java.util.ArrayList;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

public interface OtherSearchServer {

    @FormUrlEncoded
    @POST("/work/search")
    Observable<ResultModel<ArrayList<WorkInfo>>> getSearchWorkInfo(@Field(value = "city") String city,@Field(value = "work_type_id") int worktypeid);

}
