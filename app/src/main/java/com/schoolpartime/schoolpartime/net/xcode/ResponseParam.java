package com.schoolpartime.schoolpartime.net.xcode;

import okhttp3.Response;

public class ResponseParam {

    private String aesKey;
    private String signature;
    private String body;

    private ResponseParam(String aesKey,String signature,String body) {
        this.aesKey = aesKey;
        this.signature = signature;
        this.body = body;
    }

    public String getAesKey() {
        return aesKey;
    }

    public String getSignature() {
        return signature;
    }

    public String getBody() {
        return body;
    }

    public static ResponseParam analysis(Response response) {
        return new ResponseParam(response.header("AESKey"),response.header("signature"),response.body().toString());
    }
}
