package com.schoolpartime.schoolpartime.presenter;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;
import com.schoolpartime.dao.entity.UserInfo;
import com.schoolpartime.schoolpartime.R;
import com.schoolpartime.schoolpartime.SchoolPartimeApplication;
import com.schoolpartime.schoolpartime.SuperActivity;
import com.schoolpartime.schoolpartime.config.Config;
import com.schoolpartime.schoolpartime.databinding.ActivityBeginbooBinding;
import com.schoolpartime.schoolpartime.dialog.ChoosePictrueDialog;
import com.schoolpartime.schoolpartime.dialog.DialogUtil;
import com.schoolpartime.schoolpartime.entity.baseModel.ResultModel;
import com.schoolpartime.schoolpartime.net.interfacz.UploadFileForPartServer;
import com.schoolpartime.schoolpartime.net.request.HttpRequest;
import com.schoolpartime.schoolpartime.net.request.base.RequestResult;
import com.schoolpartime.schoolpartime.service.ServiceController;
import com.schoolpartime.schoolpartime.util.LogUtil;
import com.schoolpartime.schoolpartime.util.sp.SpCommonUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import androidx.databinding.ViewDataBinding;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class BeginBossPre implements Presenter, View.OnClickListener {

    ActivityBeginbooBinding binding;
    SuperActivity activity;
    private ChoosePictrueDialog choosePictrueDialog;
    private long id;
    private Bitmap bitmap;
    private boolean isSelect = false;
    private static ByteArrayOutputStream baos;


    @Override
    public void attach(ViewDataBinding binding, SuperActivity activity) {
        this.binding = (ActivityBeginbooBinding) binding;
        this.activity = activity;
        choosePictrueDialog = new ChoosePictrueDialog(activity);
        init();
    }

    private void init() {
        UserInfo info = SchoolPartimeApplication.getmDaoSession().getUserInfoDao().loadAll().get(0);
        binding.userName.setText(info.getUsername());
        binding.userAge.setText(info.getUserage()+"");
        binding.userSex.setText(info.getUsersex());
        binding.userAddress.setText(info.getAddress());
        binding.userPhone.setText(info.getPhonenumber());

        id = SpCommonUtils.getUserId();
        binding.submit.setOnClickListener(this);
        binding.userBack.setOnClickListener(this);
        binding.userRequest.setOnClickListener(this);
    }

    @Override
    public void notifyUpdate(int code) {
        switch (code)
        {
            case 3:
            {
                choosePictrueDialog.startPhoto();
            }
            break;
            case 4:
            {
                closeChoosePictrueDialog();
            }
            break;
        }

    }

    // 关闭选择图片Dialog
    private void closeChoosePictrueDialog() {
        if (choosePictrueDialog != null)
            choosePictrueDialog.closeChoosePictrueDialog();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.submit:
            {
                choosePictrueDialog.showChoosePictrueDialog();
            }
            break;
            case R.id.user_back:
            {
                activity.finish();
            }
            break;
            case R.id.user_request:
            {
                SendRequestInfo();
            }
            break;
        }
    }


    @SuppressLint("WrongConstant")
    private void showResult(String mes) {
        Snackbar.make(binding.lly, mes, Snackbar.LENGTH_LONG)
                .setDuration(Snackbar.LENGTH_LONG).show();
    }

    private void SendRequestInfo() {

        if (isSelect){
            activity.show("正在发送...");
            File img_file = convertBitmapToFile();
            if (img_file != null){
                RequestBody photoBody = RequestBody.create(MediaType.parse("image/png"),img_file);
                MultipartBody.Part photo = MultipartBody.Part.createFormData("file","base.png",photoBody);
                HttpRequest.request(HttpRequest.builder().create(UploadFileForPartServer.class).uploadFile(photo,id,Config.IMG_ID_CARD),
                        new RequestResult() {
                            @Override
                            public void success(ResultModel resultModel) {
                                activity.dismiss();
                                LogUtil.d("上传证件----------ResultModel："+resultModel.toString());
                                if (resultModel.code == 200) {
                                    showResult("上传成功!");
                                    DialogUtil.selectDialog(activity, "提示：", "上传成功，等待相关人员审核通过即可使用商家功能。", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            SpCommonUtils.setUserType(4);
                                            ServiceController.startBossCheckService();
                                            activity.finish();
                                        }
                                    });
                                } else {
                                    showResult(resultModel.message);
                                }
                            }

                            @Override
                            public void fail(Throwable e) {
                                activity.dismiss();
                                showResult("请求失败");
                                LogUtil.d("上传证件失败-----》",e);
                            }
                        },true);

            }else {
                showResult("请求失败，照片信息错误");
            }
        }else {
            showResult("请选择照片后再申请");
        }
    }

    public void updateImg(Uri photoUri) {
        isSelect= true;
        if (photoUri != null) {
            try {
                bitmap = getBitmapFormUri(photoUri);
                binding.showimg.setImageBitmap(bitmap);
            } catch (IOException e) {
                LogUtil.d("得到图片，显示错误----------------》",e);
            }
        }else {
            LogUtil.d("得到图片URI为空----------------》");
        }


    }

    // convert bitmap to file

    private File convertBitmapToFile() {
        File f = null;
        try {
            // create a file to write bitmap data
            f = new File(activity.getCacheDir(), "base.png");
            f.createNewFile();
            byte[] bitmapdata = baos.toByteArray();
            // write the bytes in file
            FileOutputStream fos = new FileOutputStream(f);
            fos.write(bitmapdata);
            fos.flush();
            fos.close();
        } catch (Exception e) {

            LogUtil.d("图片文件转化错误------》",e);
        }
        return f;
    }

    public Bitmap getBitmapFormUri(Uri uri) throws  IOException {
        InputStream input = activity.getContentResolver().openInputStream(uri);
        BitmapFactory.Options onlyBoundsOptions = new BitmapFactory.Options();
        onlyBoundsOptions.inJustDecodeBounds = true;
        onlyBoundsOptions.inDither = true;//optional
        onlyBoundsOptions.inPreferredConfig = Bitmap.Config.ARGB_8888;//optional
        BitmapFactory.decodeStream(input, null, onlyBoundsOptions);
        input.close();
        int originalWidth = onlyBoundsOptions.outWidth;
        int originalHeight = onlyBoundsOptions.outHeight;
        if ((originalWidth == -1) || (originalHeight == -1))
            return null;
        //图片分辨率以480x800为标准
        float hh = 800f;//这里设置高度为800f
        float ww = 480f;//这里设置宽度为480f
        //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;//be=1表示不缩放
        if (originalWidth > originalHeight && originalWidth > ww) {//如果宽度大的话根据宽度固定大小缩放
            be = (int) (originalWidth / ww);
        } else if (originalWidth < originalHeight && originalHeight > hh) {//如果高度高的话根据宽度固定大小缩放
            be = (int) (originalHeight / hh);
        }
        if (be <= 0)
            be = 1;
        //比例压缩
        BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
        bitmapOptions.inSampleSize = be;//设置缩放比例
        bitmapOptions.inDither = true;//optional
        bitmapOptions.inPreferredConfig = Bitmap.Config.ARGB_8888;//optional
        input = activity.getContentResolver().openInputStream(uri);
        Bitmap bitmap = BitmapFactory.decodeStream(input, null, bitmapOptions);
        input.close();

        return compressImage(bitmap);//再进行质量压缩
    }

    /**
     * 质量压缩方法
     *
     * @param image
     * @return
     */
    public static Bitmap compressImage(Bitmap image) {

        baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 100;
        while (baos.toByteArray().length / 1024 > 100) {  //循环判断如果压缩后图片是否大于100kb,大于继续压缩
            baos.reset();//重置baos即清空baos
            //第一个参数 ：图片格式 ，第二个参数： 图片质量，100为最高，0为最差  ，第三个参数：保存压缩后的数据的流
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中
            options -= 10;//每次都减少10
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);//把ByteArrayInputStream数据生成图片
        return bitmap;
    }
}
