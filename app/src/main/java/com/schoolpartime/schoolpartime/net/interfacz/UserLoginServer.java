package com.schoolpartime.schoolpartime.net.interfacz;

import com.schoolpartime.schoolpartime.entity.User;
import com.schoolpartime.schoolpartime.entity.baseModel.ResultModel;

import java.util.Map;

import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;
import rx.Observable;

public interface UserLoginServer {

    @FormUrlEncoded
    @POST("/login")
    Observable<ResultModel<User>> registerUser(@Field(value = "data") String model,
                                               @Header(value = "Authentication") String signature,
                                               @Header(value = "SecurityKey") String aesKey,
                                               @Header(value = "TimesTamp") String token);



}
