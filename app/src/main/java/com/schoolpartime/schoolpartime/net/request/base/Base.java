package com.schoolpartime.schoolpartime.net.request.base;

import com.schoolpartime.schoolpartime.config.Config;

import retrofit2.Retrofit;

class Base {


     Retrofit.Builder Builder() {

        return new Retrofit.Builder()
                .baseUrl(Config.URL)
                ;//基础URL 建议以 / 结尾

    }

}
