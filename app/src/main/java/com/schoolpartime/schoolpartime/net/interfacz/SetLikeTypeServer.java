package com.schoolpartime.schoolpartime.net.interfacz;

import com.schoolpartime.schoolpartime.entity.ChatRecord;
import com.schoolpartime.schoolpartime.entity.baseModel.ResultModel;

import java.util.List;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

public interface SetLikeTypeServer {

    @FormUrlEncoded
    @POST("/work/setliketype")
    Observable<ResultModel<String>> setLikeType(@Field(value = "id") long id,
                                               @Field(value = "type1")int type1,
                                               @Field(value = "type2")int type2,
                                               @Field(value = "type3")int type3);



}
