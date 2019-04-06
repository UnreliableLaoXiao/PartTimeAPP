package com.schoolpartime.schoolpartime.net.interfacz;

import com.schoolpartime.schoolpartime.entity.Message;
import com.schoolpartime.schoolpartime.entity.baseModel.ResultModel;

import java.util.List;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

public interface ChatMessageServer {

    @FormUrlEncoded
    @POST("/messages")
    Observable<ResultModel<List<Message>>> getMessages(@Field(value = "from") long from, @Field(value = "to") long to);



}
