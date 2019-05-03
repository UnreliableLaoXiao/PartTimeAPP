package com.schoolpartime.schoolpartime.net.request.base;

import retrofit2.Retrofit;

class Base {


     Retrofit.Builder Builder() {

        return new Retrofit.Builder()
                .baseUrl("http://172.28.131.4:8080")
                ;//基础URL 建议以 / 结尾

    }

}
