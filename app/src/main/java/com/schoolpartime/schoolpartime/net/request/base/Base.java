package com.schoolpartime.schoolpartime.net.request.base;

import retrofit2.Retrofit;

class Base {


     Retrofit.Builder Builder() {

        return new Retrofit.Builder()
                .baseUrl("http://192.168.124.11:8080")
                ;//基础URL 建议以 / 结尾

    }

}
