package com.schoolpartime.schoolpartime.net.interfacz;

import com.schoolpartime.dao.entity.City;
import com.schoolpartime.schoolpartime.entity.baseModel.ResultModel;

import java.util.List;

import retrofit2.http.POST;
import rx.Observable;

public interface CitysServer {

    @POST("/work/citys")
    Observable<ResultModel<List<City>>> getCitys();

}
