package com.schoolpartime.schoolpartime.filter;

import com.google.gson.Gson;
import com.schoolpartime.schoolpartime.entity.baseModel.ResultModel;
import com.schoolpartime.schoolpartime.net.xcode.ResponseParam;
import com.schoolpartime.security.base64.Base64;
import com.schoolpartime.security.aes.AESUtil;
import com.schoolpartime.security.md5.Md5Util;



import javax.crypto.SecretKey;

import okhttp3.Response;

public class ResponseBodyFilter {


    public static ResultModel filter(Response response) throws Exception {


        ResponseParam param = ResponseParam.analysis(response);
        SecretKey secretKey1 = AESUtil.strKey2SecretKey(param.getAesKey());
        String mbody = new String(AESUtil.decryptAES(Base64.getDecoder().decode(param.getBody()), secretKey1), "utf-8");
        String md5 = Md5Util.build(mbody);
        if (!md5.equals(param.getSignature()))
            return new ResultModel("签名不正确，访问异常", null, "error", 500);
        Gson gson = new Gson();
        ResultModel model = gson.fromJson(mbody,ResultModel.class);
        return model;
    }

}
