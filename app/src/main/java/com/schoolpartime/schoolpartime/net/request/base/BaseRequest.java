package com.schoolpartime.schoolpartime.net.request.base;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class BaseRequest extends Base {

    Retrofit.Builder GetRequestWithAll(){
        return Builder()
                .addConverterFactory(GsonConverterFactory.create())//设置 Json 转换器
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create());//RxJava 适配器;
    }

    Retrofit.Builder GetRequestWithGson(){
        return Builder()
                .addConverterFactory(GsonConverterFactory.create());
    }

    Retrofit.Builder GetRequestWithRxjava(){
        return Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create());
    }

    public Retrofit.Builder GetRequest(){
        return Builder();
    }


}
