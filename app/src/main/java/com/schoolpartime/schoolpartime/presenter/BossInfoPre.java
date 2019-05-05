package com.schoolpartime.schoolpartime.presenter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;
import com.schoolpartime.dao.entity.UserInfo;
import com.schoolpartime.schoolpartime.R;
import com.schoolpartime.schoolpartime.SchoolPartimeApplication;
import com.schoolpartime.schoolpartime.SuperActivity;
import com.schoolpartime.schoolpartime.config.Config;
import com.schoolpartime.schoolpartime.databinding.ActivityBossinfoBinding;
import com.schoolpartime.schoolpartime.entity.baseModel.ResultModel;
import com.schoolpartime.schoolpartime.net.interfacz.Img2UrlServer;
import com.schoolpartime.schoolpartime.net.request.HttpRequest;
import com.schoolpartime.schoolpartime.net.request.base.RequestResult;
import com.schoolpartime.schoolpartime.util.LogUtil;
import com.schoolpartime.schoolpartime.util.sp.SpCommonUtils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import androidx.databinding.ViewDataBinding;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class BossInfoPre implements Presenter, View.OnClickListener {

    ActivityBossinfoBinding binding;
    SuperActivity activity;

    @Override
    public void attach(ViewDataBinding binding, SuperActivity activity) {
        this.binding = (ActivityBossinfoBinding) binding;
        this.activity = activity;
        init();
    }

    private void init() {
        UserInfo info = SchoolPartimeApplication.getmDaoSession().getUserInfoDao().loadAll().get(0);
        binding.userName.setText(info.getUsername());
        binding.userAge.setText(info.getUserage() + "");
        binding.userSex.setText(info.getUsersex());
        binding.userAddress.setText(info.getAddress());
        binding.userPhone.setText(info.getPhonenumber());
        binding.userBack.setOnClickListener(this);
        HttpRequest.request(HttpRequest.builder().create(Img2UrlServer.class).getImgUrl(SpCommonUtils.getUserId(), 2),
                new RequestResult() {
                    @Override
                    public void success(ResultModel resultModel) {
                        activity.dismiss();
                        LogUtil.d("得到Url----------ResultModel：" + resultModel.toString());
                        if (resultModel.code == 200) {
                            String url = (String) resultModel.data;
                            File file = new File(activity.getExternalCacheDir(), url);
                            if (file.exists()) {
                                binding.showimg.setImageBitmap(loadBitmapFromSDCard(file));
                            } else {
                                getImg(url);
                            }
                        } else {
                            showResult(resultModel.message);
                        }
                    }

                    @Override
                    public void fail(Throwable e) {
                        LogUtil.d("得到Url----------失败", e);
                        showResult("请求失败");
                    }
                }, true);
    }

    // 从SD卡获取文件
    public static byte[] loadFileFromSDCard(File file) {
        BufferedInputStream bis = null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        try {
            bis = new BufferedInputStream(
                    new FileInputStream(file));
            byte[] buffer = new byte[8 * 1024];
            int c = 0;
            while ((c = bis.read(buffer)) != -1) {
                baos.write(buffer, 0, c);
                baos.flush();
            }
            return baos.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                baos.close();
                bis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    // 从SDCard中寻找指定目录下的文件，返回Bitmap
    public Bitmap loadBitmapFromSDCard(File file) {
        byte[] data = loadFileFromSDCard(file);
        if (data != null) {
            Bitmap bm = BitmapFactory.decodeByteArray(data, 0, data.length);
            if (bm != null) {
                return bm;
            }
        }
        return null;
    }

    @SuppressLint("WrongConstant")
    private void showResult(String mes) {
        Snackbar.make(binding.lly, mes, Snackbar.LENGTH_LONG)
                .setDuration(Snackbar.LENGTH_LONG).show();
    }


    private void getImg(final String murl) {
            LogUtil.d("下载--->");
            //创建okHttp
            OkHttpClient client = new OkHttpClient.Builder()
                    .connectTimeout(8, TimeUnit.SECONDS)
                    .readTimeout(8, TimeUnit.SECONDS)
                    .build();

            String url = Config.URL+ "/img/" + murl;

            LogUtil.d("url = " + url);
            //创建request
            Request request = new Request.Builder()
                    .url(url).build();
            //创建call
            Call call = client.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    LogUtil.d("下载失败", e);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    //将流转换成字节
                    byte[] bytes = response.body().bytes();
                    final Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                    if (bitmap == null) {
                        LogUtil.d("下载Bitmap--------->为空");
                    } else {
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                binding.showimg.setImageBitmap(bitmap);
                            }
                        });
                        LogUtil.d("保存Bitmap--------->结果为：" +saveBitmapToSDCardPrivateCacheDir(bitmap, murl, activity));
                    }
                }
            });
    }

    // 保存bitmap图片到SDCard的私有Cache目录
    public boolean saveBitmapToSDCardPrivateCacheDir(Bitmap bitmap, String fileName, Context context) {
        if (isSDCardMounted()) {
            BufferedOutputStream bos = null;
            // 获取私有的Cache缓存目录
            File file = context.getExternalCacheDir();

            try {
                bos = new BufferedOutputStream(new FileOutputStream(new File(
                        file, fileName)));
                if (fileName != null
                        && (fileName.contains(".png") || fileName
                        .contains(".PNG"))) {
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);
                } else {
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
                }
                bos.flush();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (bos != null) {
                    try {
                        bos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return true;
        } else {
            return false;
        }
    }


    // 判断SD卡是否被挂载
    public static boolean isSDCardMounted() {
        // return Environment.getExternalStorageState().equals("mounted");
        return Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);
    }


    @Override
    public void notifyUpdate(int code) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_back:
            {
                activity.finish();
            }
            break;
        }
    }
}
