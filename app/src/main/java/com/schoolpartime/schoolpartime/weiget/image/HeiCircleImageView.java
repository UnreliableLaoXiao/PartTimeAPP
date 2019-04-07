package com.schoolpartime.schoolpartime.weiget.image;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.schoolpartime.schoolpartime.util.LogUtil;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import androidx.annotation.Nullable;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

@SuppressLint("AppCompatCustomView")
public class HeiCircleImageView extends ImageView {

    private Paint mPaint;
    private int mRadius;
    private float mScale;
    private Context context;

    public HeiCircleImageView(Context context) {
        super(context);
        this.context = context;
    }

    public HeiCircleImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    public HeiCircleImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int size = Math.min(getMeasuredHeight(),getMeasuredWidth());
        mRadius = size/2;
        setMeasuredDimension(size,size);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        mPaint = new Paint();

        Drawable drawable = getDrawable();

        if(drawable != null) {

            Bitmap bitmap = ((BitmapDrawable)drawable).getBitmap();

            BitmapShader bitmapShader = new BitmapShader(bitmap,BitmapShader.TileMode.CLAMP,BitmapShader.TileMode.CLAMP);

            mScale = mRadius*2.0f/Math.min(bitmap.getHeight(),bitmap.getWidth());

            Matrix matrix = new Matrix();
            matrix.setScale(mScale,mScale);
            bitmapShader.setLocalMatrix(matrix);
            mPaint.setShader(bitmapShader);
            canvas.drawCircle(mRadius,mRadius,mRadius,mPaint);

        }else {
            super.onDraw(canvas);
        }
    }

    public void setNetImg(String url,Activity activity){
        File file = new File(activity.getExternalCacheDir(), url);
        if (file.exists()) {
            this.setImageBitmap(loadBitmapFromSDCard(file));
        } else {
            getImg(url,activity);
        }
    }


    private void getImg(final String murl, final Activity activity) {
        LogUtil.d("下载--->");
        //创建okHttp
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(8, TimeUnit.SECONDS)
                .readTimeout(8, TimeUnit.SECONDS)
                .build();

        String url = "http://192.168.124.11:8080/img/" + murl;

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
                            HeiCircleImageView.this.setImageBitmap(bitmap);
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
}
