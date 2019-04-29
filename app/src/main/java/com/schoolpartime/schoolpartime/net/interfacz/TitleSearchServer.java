package com.schoolpartime.schoolpartime.net.interfacz;

import com.schoolpartime.schoolpartime.entity.WorkInfo;
import com.schoolpartime.schoolpartime.entity.baseModel.ResultModel;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

public interface TitleSearchServer {

    @FormUrlEncoded
    @POST("/work/searchtitle")
    Observable<ResultModel<ArrayList<WorkInfo>>> getTitleSearchWorkInfo(@Field(value = "title") String title);

}
