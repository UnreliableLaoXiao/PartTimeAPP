package com.schoolpartime.security.md5;

import java.security.MessageDigest;

public class Md5Util {
	
	//十六进制下数字到字符的映射数组 
    private final static String[] hexDigits = {"0","1","2","3","4","5","6","7","8","9","A","B","C","D","E","F"};
    
    public static String build(String str) {
    	return encodeByMD5(str);
    }
	
	/**对字符串进行MD5编码*/ 
    private static String encodeByMD5(String originString){ 
        if (originString!=null) { 
            try { 
                //创建具有指定算法名称的信息摘要 
                MessageDigest md5 = MessageDigest.getInstance("MD5"); 
                //使用指定的字节数组对摘要进行最后更新，然后完成摘要计算 
                byte[] results = md5.digest(originString.getBytes()); 
                //将得到的字节数组变成字符串返回  
                String result = byteArrayToHexString(results); 
                return result; 
            } catch (Exception e) { 
                e.printStackTrace(); 
            } 
        } 
        return null; 
    }
    
    /** 
     * 轮换字节数组为十六进制字符串 
     * @param b 字节数组 
     * @return 十六进制字符串 
     */ 
     private static String byteArrayToHexString(byte[] b){ 
         StringBuffer resultSb = new StringBuffer(); 
         for(int i=0;i<b.length;i++){ 
             resultSb.append(byteToHexString(b[i])); 
         } 
         return resultSb.toString(); 
     } 
     
   //将一个字节转化成十六进制形式的字符串 
     private static String byteToHexString(byte b){ 
         int n = b; 
         if(n<0) 
         n=256+n; 
         int d1 = n/16; 
         int d2 = n%16; 
         return hexDigits[d1] + hexDigits[d2]; 
     }

    /**

     static String publicKeyStr = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAwdyf35NcDrYXNds5/TqCokLlBCVKpD/HBYT0yednUa"
     + "aUP6fOAC8cbp5FOanqr2CmnyqVyUvuOeGwXgiIMGqXn8OAYAoN2rUMuyDIjIQEK8zln3zJCrBaaJDi639+k711KFwxAvTRljJEH"
     + "BgR11bbAdUuEj5E7ouHWiXdXUgt/7PV9ZaRhVyKvZX7RU42Y6T8kePGQtyjbq48Ss3BNUj861PAg3oavAF2/8xvRnnCYrNKgSnb"
     + "uxHkaFjvt/PjnUUcqAnaAJ1GI5dHUGqNbyXi+Z9nyYrz+Hn15K9G7X5YaO/wKHgAqb0H1UqkdlB5ine9Dyu63rLbD6HmFpEaji"
     + "yM4wIDAQAB";

     static String privateKeyStr = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQDB3J/fk1wOthc12zn9OoKiQuUE"
     + "JUqkP8cFhPTJ52dRppQ/p84ALxxunkU5qeqvYKafKpXJS+454bBeCIgwapefw4BgCg3atQy7IMiMhAQrzOWffMkKsF"
     + "pokOLrf36TvXUoXDEC9NGWMkQcGBHXVtsB1S4SPkTui4daJd1dSC3/s9X1lpGFXIq9lftFTjZjpPyR48ZC3KNurjxKz"
     + "cE1SPzrU8CDehq8AXb/zG9GecJis0qBKdu7EeRoWO+38+OdRRyoCdoAnUYjl0dQao1vJeL5n2fJivP4efXkr0btflho"
     + "7/AoeACpvQfVSqR2UHmKd70PK7restsPoeYWkRqOLIzjAgMBAAECggEAUf0B7GeBJoOiW1elNdC6EO/jtZYj9EU44b+y"
     + "W3Wvf5vI1QceG3vRNYNgWZvgMl3Y+jXjdWfUj8xAb/SBzKA4Egx3zaZS561sffPGfY8TyIZ2krYOvKOLCPBF2D1qhgc5"
     + "dmFPJSXvQetuXMddPEpyg1rqijKlqpF+JAUhkuME+UATvtUalLYiE3heOpdxYrOnGe2jdrLE4wYoNVc4HOkTUmsRrO8GS"
     + "iemmFp7gFJEki5DbjmVYRw+EuLb0/Pntf30k2xS0PKaM1JMMErnhGpUD46gRCwW7KMnJMmpGmP0fqLTT7mmOLSbf7JP89t"
     + "QFnihAmkpvgihclCqHE8waRpW+QKBgQDrzSP1dBLJOKpu2aZwB1eSYAvUgeSgF1FNAtKeq7j3U2qo8Q0++6/B2RWzGQuu58"
     + "AvhUvM6FMYAnny0PWtw4avdM80uGeafdsnwCIEAiEGg3YuwpqVe5zjzSztkLjGL3tEXDSoYBjuTR85DRvUzGe8V+W49Rk5/9"
     + "PbeMOk91hexQKBgQDSd8wfhzf6av1uvrXcOwYkDLBMhi2dVqbETO16x2WzC3rGIIbuHtMZmE8pXEmTWff5+nagEMBgSXOszsJ"
     + "5QeKb2UTzds1t5RJ6JZqx1NEiTozA961slbCU9dE6MJEVN1QWEulDvjjWdwnGenmS6MhYBoJiFlr3XYknZY0huMF3hwKBgAiVt"
     + "WNwUGbHC11xyx91BPktSgD4oaw6bRlSqvxf4CIRBWcVL5hFbYavMp0MomJBybtxLOtO4geTv4DZnrgu0C5/IDQZKpxzTJFL63Ed"
     + "6rnj+1+EckBS+clJZQNZK4D7pY89lCU1KnMyl5pqIcNDldtDj/eF5N85syrgYK8W2j7JAoGBAKbxf2hCyZRI6V2+yGI4L1bI+c65"
     + "X9U9tmpe2sBZCcJLiMc/Zcfbi3bx6VjVa0cGRjxy/0VYBEBcAvU/y+KC8EzOunKj+a8B1PYufdYxCPI9fEhULavD0J1Xnu6ZN3u"
     + "st3YK6hxh9pOnOInG/EgbfU7VWvaS5PTxrKkjVB87fbphAoGBAIdv+4tIfElaecYYz0Qqu7TJ5KqV2qdGVzIGDGOnVEMElvOwJ"
     + "2rMGt5slpQTmdkuZG8IDBXaxK2z6T0BC3awbQMz5a3PJopEcX5TDclbcWFV1ogpe1hqgDk8obhnoZA+zNdH8APJa6WcEtqV6myz4"
     + "FQNib0fbWHiKwGXSkcXpqD8";

     public static void main(String[] args) {
     Body<String> body = new Body<String>(200, "测试数据", "数据", "json");
     Gson gson = new Gson();
     String content = gson.toJson(body);


     //生成签名

    String signature = null;
    String md5Str = Md5.build(content);
    PublicKey publicKey = null;
		try {
        publicKey = RSAUtil.string2PublicKey(publicKeyStr);
        // 公钥加密/私钥解密
        byte[] publicEncryBytes =  RSAUtil.publicEncrytype(md5Str.getBytes(), publicKey);

        signature = Base64.getEncoder().encodeToString(publicEncryBytes);
        System.out.println("公钥加密后的字符串(经BASE64处理)：" + signature);
    } catch (Exception e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }

     // 加密请求报文

    String strKeyAES = null;
    String psw = null;
		try {
        strKeyAES = AESUtil.getStrKeyAES();
        System.out.println("AES秘钥（动态）：" + strKeyAES);
        // 将 BASE64 处理之后的 AES 密钥转为 SecretKey
        SecretKey secretKey = AESUtil.strKey2SecretKey(strKeyAES);
        // 加密数据
        byte[] encryptAESbytes = AESUtil.encryptAES(content.getBytes("utf-8"), secretKey);
        psw = Base64.getEncoder().encodeToString(encryptAESbytes);
        System.out.println("加密后的数据经 BASE64 处理之后为：" + psw);

    } catch (NoSuchAlgorithmException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    } catch (Exception e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }

     // 加密AES秘钥

    byte[] publicEncryBytes;
    String AESKeySecurity = null;
		try {
        publicEncryBytes = RSAUtil.publicEncrytype(strKeyAES.getBytes(), publicKey);
        AESKeySecurity = Base64.getEncoder().encodeToString(publicEncryBytes);
        System.out.println("公钥加密后的AES字符串(经BASE64处理)：" + AESKeySecurity);
    } catch (Exception e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }


     //当前时间

//		Date date = new Date();
//		String timeTemp = date.toString();
    String timeTemp = "Wed Mar 13 16:24:37 CST 2019";
		System.out.println(timeTemp);

		System.out.println("--------------------------服务器---------------------------");

     // 解析时间戳


    Date date_old = new Date(timeTemp);
    Date date_now = new Date();
    int times = date_now.compareTo(date_old);
		System.out.println("时间戳为："+times);

     // 获取AESKey
     // 私钥解密

    PrivateKey privateKey;
    String AESKeySecurity_now = null;
		try {
        privateKey = RSAUtil.string2Privatekey(privateKeyStr);
        byte[] privateDecryBytes = RSAUtil.privateDecrypt(Base64.getDecoder().decode(AESKeySecurity), privateKey);
        AESKeySecurity_now = new String(privateDecryBytes);
        System.out.println("私钥解密后的AES原始字符串：" + AESKeySecurity_now);
    } catch (NoSuchAlgorithmException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    } catch (Exception e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }

     // 获取body

    SecretKey secretKey1 = AESUtil.strKey2SecretKey(AESKeySecurity_now);
    // 解密数据
    String body_json = null;
		try {
        body_json = new String(AESUtil.decryptAES(Base64.getDecoder().decode(psw), secretKey1), "utf-8");
        System.out.println("解密后的数据为：" + body_json);
    } catch (Exception e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }

     // 得到MD5,验证签名

    String md5_now = Md5.build(body_json);
    String md5_old = null;
		try {
        privateKey = RSAUtil.string2Privatekey(privateKeyStr);
        byte[] privateDecryBytes = RSAUtil.privateDecrypt(Base64.getDecoder().decode(signature), privateKey);
        md5_old = new String(privateDecryBytes);
        System.out.println("私钥解密后的md5原始字符串：" + AESKeySecurity_now);
    } catch (NoSuchAlgorithmException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    } catch (Exception e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }
		System.out.println("签名是否相同"+md5_now.equals(md5_old));
     */

}
