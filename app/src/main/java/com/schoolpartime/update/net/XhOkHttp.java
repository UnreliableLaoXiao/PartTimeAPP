package com.schoolpartime.update.net;

import android.app.Activity;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.schoolpartime.update.entity.Version;
import com.schoolpartime.update.minterface.NetInterface;
import com.schoolpartime.update.util.FileUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by machenike on 2018/10/16.
 * 用于下载回调
 */

public class XhOkHttp {

    public static void GetVersion(String url, Activity activity, final NetInterface netInterface){
        Request.Builder builder = new Request.Builder().url(url);
        builder.method("GET",null);
        Request request = builder.build();
        Call call = GetOkHttpClient(activity).newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                netInterface.fail();
            }
            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                String str= null;
                try {
                    str = response.body().string();
                    if(str == null || TextUtils.isEmpty(str)){
                        netInterface.fail();
                    }else {
                        Gson gson = new Gson();
                        Type type = new TypeToken<Version>() {
                        }.getType();
                        Version version = gson.fromJson(str, type);
                        netInterface.complied(version);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    netInterface.fail();
                }
            }
        });
    }


    public static void DownFile(String url, final Activity activity, final NetInterface netInterface){
        Request request = new Request.Builder().url(url).build();
        GetOkHttpClient(activity).newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                netInterface.fail();
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                InputStream inputStream = response.body().byteStream();
                int length = (int) response.body().contentLength();
                int count = 0;
                FileOutputStream fileOutputStream = null;
                try{
                    File file = FileUtil.getFilePath(activity);
                    if(null != file){
                        fileOutputStream = new FileOutputStream(file);
                        byte[] buffer = new byte[1024];
                        do
                        {
                            int numread = inputStream.read(buffer);
                            count += numread;
                             netInterface.progress((int) (((float) count / length) * 100));
                            if (numread <= 0)
                            {
                                // 下载完成
                                netInterface.complied(null);
                                break;
                            }
                            // 写入文件
                            fileOutputStream.write(buffer, 0, numread);
                        } while (true);// 点击取消就停止下载.
                        fileOutputStream.flush();
                    }
                }catch (IOException e){
                    e.printStackTrace();
                    netInterface.fail();
                }
            }
        });
    }

    private static OkHttpClient GetOkHttpClient(Activity activity){
        File sdcache = activity.getExternalCacheDir();
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(5, TimeUnit.SECONDS)
                .writeTimeout(20,TimeUnit.SECONDS)
                .readTimeout(20,TimeUnit.SECONDS)
                .cache(new Cache(sdcache.getAbsoluteFile(),10*1024*1024));
        return builder.build();
    }

}
