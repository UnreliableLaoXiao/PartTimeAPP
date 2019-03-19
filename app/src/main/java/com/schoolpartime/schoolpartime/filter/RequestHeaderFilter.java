package com.schoolpartime.schoolpartime.filter;

import android.util.Log;

import com.schoolpartime.schoolpartime.util.LogUtil;
import com.schoolpartime.security.base64.Base64;
import com.schoolpartime.security.aes.AESUtil;
import com.schoolpartime.security.md5.Md5Util;
import com.schoolpartime.security.rsa.RSAUtil;

import java.security.PublicKey;

import javax.crypto.SecretKey;

public class RequestHeaderFilter {

    private static String publicKeyStr = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAwdyf35NcDrYXNds5/TqCokLlBCVKpD/HBYT0yednUa"
            + "aUP6fOAC8cbp5FOanqr2CmnyqVyUvuOeGwXgiIMGqXn8OAYAoN2rUMuyDIjIQEK8zln3zJCrBaaJDi639+k711KFwxAvTRljJEH"
            + "BgR11bbAdUuEj5E7ouHWiXdXUgt/7PV9ZaRhVyKvZX7RU42Y6T8kePGQtyjbq48Ss3BNUj861PAg3oavAF2/8xvRnnCYrNKgSnb"
            + "uxHkaFjvt/PjnUUcqAnaAJ1GI5dHUGqNbyXi+Z9nyYrz+Hn15K9G7X5YaO/wKHgAqb0H1UqkdlB5ine9Dyu63rLbD6HmFpEaji"
            + "yM4wIDAQAB";

    public static String getSignature(String json) {
        String signature = null;
        String md5Str = Md5Util.build(json);
        PublicKey publicKey = null;
        try {
            publicKey = RSAUtil.string2PublicKey(publicKeyStr);
            // 公钥加密/私钥解密
            byte[] publicEncryBytes =  RSAUtil.publicEncrytype(md5Str.getBytes(), publicKey);
            signature = Base64.getEncoder().encodeToString(publicEncryBytes);
            LogUtil.d("签名Signature: "+signature);
            return signature;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            LogUtil.d("签名错误",e);
            return null;
        }
    }

    public static String getSecurityBody(String body,String strKeyAES){
        String psw = null;
        try {
            LogUtil.d("AES秘钥（动态）：" + strKeyAES);
            // 将 BASE64 处理之后的 AES 密钥转为 SecretKey
            SecretKey secretKey = AESUtil.strKey2SecretKey(strKeyAES);
            // 加密数据
            byte[] encryptAESbytes = AESUtil.encryptAES(body.getBytes("utf-8"), secretKey);
            psw = Base64.getEncoder().encodeToString(encryptAESbytes);
            LogUtil.d("加密后的数据经 BASE64 处理之后为：" + psw);
            return psw;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            LogUtil.d("getSecurityBody错误",e);
            return null;
        }

    }

    public static String getAESKeySecurity(String strKeyAES){
        byte[] publicEncryBytes;
        String AESKeySecurity = null;
        try {
            PublicKey publicKey = RSAUtil.string2PublicKey(publicKeyStr);
            publicEncryBytes = RSAUtil.publicEncrytype(strKeyAES.getBytes(), publicKey);
            AESKeySecurity = Base64.getEncoder().encodeToString(publicEncryBytes);
            LogUtil.d( "公钥加密后的AES字符串(经BASE64处理)：" + AESKeySecurity);
            return AESKeySecurity;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            LogUtil.d("getAESKeySecurity错误",e);
            return null;
        }
    }


}
