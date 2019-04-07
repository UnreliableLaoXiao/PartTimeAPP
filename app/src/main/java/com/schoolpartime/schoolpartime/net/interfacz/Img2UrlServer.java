package com.schoolpartime.schoolpartime.net.interfacz;

import com.schoolpartime.schoolpartime.entity.Message;
import com.schoolpartime.schoolpartime.entity.baseModel.ResultModel;

import java.util.List;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

public interface Img2UrlServer {

    @FormUrlEncoded
    @POST("/geturl")
    Observable<ResultModel<String>> getImgUrl(@Field(value = "id") long id,@Field(value = "type") int type);



}
