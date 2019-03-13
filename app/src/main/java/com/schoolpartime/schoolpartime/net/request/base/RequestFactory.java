package com.schoolpartime.schoolpartime.net.request.base;

import retrofit2.Retrofit;

public class RequestFactory {

    private static RequestFactory instance = new RequestFactory();

    //让构造函数为 private，这样该类就不会被实例化
    private RequestFactory(){}

    //获取唯一可用的对象
    public static RequestFactory getInstance(){
        return instance;
    }

    public Retrofit Build(TYPE type){
        if (type == TYPE.ALL) {
            return new BaseRequest().GetRequestWithAll().build();
        } else if (type == TYPE.GSON) {
            return new BaseRequest().GetRequestWithGson().build();
        } else if(type == TYPE.RXJAVA){
            return new BaseRequest().GetRequestWithRxjava().build();
        } else {
            return new BaseRequest().GetRequest().build();
        }
    }

    public enum TYPE{
        GSON,
        RXJAVA,
        ALL;
    }
}
