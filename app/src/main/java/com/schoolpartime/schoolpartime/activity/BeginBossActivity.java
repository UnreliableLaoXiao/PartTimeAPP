package com.schoolpartime.schoolpartime.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import com.schoolpartime.schoolpartime.R;
import com.schoolpartime.schoolpartime.SuperActivity;
import com.schoolpartime.schoolpartime.databinding.ActivityBeginbooBinding;
import com.schoolpartime.schoolpartime.dialog.ChoosePictrueUtil;
import com.schoolpartime.schoolpartime.dialog.ZDialogConstantUtil;
import com.schoolpartime.schoolpartime.presenter.BeginBossPre;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;

@SuppressLint("Registered")
public class BeginBossActivity extends SuperActivity {

    BeginBossPre pre = new BeginBossPre();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityBeginbooBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_beginboo);
        pre.attach(binding,this);
    }

    // 权限设置结果
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case ZDialogConstantUtil.PERMISSION_CAMERA_REQUEST_CODE:
                if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    pre.notifyUpdate(3);
                } else {
                    Toast.makeText(this, "获取拍照权限失败", Toast.LENGTH_SHORT).show();
                }
                break;
            case ZDialogConstantUtil.PERMISSION_READ_EXTERNAL_STORAGE_REQUEST_CODE:
                if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    pre.notifyUpdate(3);
                } else {
                    Toast.makeText(this, "sdcard中读取数据的权限失败", Toast.LENGTH_SHORT).show();
                }
                break;
            case ZDialogConstantUtil.PERMISSION_WRITE_EXTERNAL_STORAGE_REQUEST_CODE:
                if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    pre.notifyUpdate(3);
                } else {
                    Toast.makeText(this, "写入数据到扩展存储卡(SD)权限失败", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    /**
     * 通过回调方法处理图片
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case ZDialogConstantUtil.RESULT_PHOTO_CODE:
                /**
                 * 拍照，获取返回结果
                 */
                pre.notifyUpdate(4);
                Uri photoUri = ChoosePictrueUtil.photoUri;
                if (photoUri != null) {
                    /**
                     * 拍照回调，进行相应的逻辑处理即可
                     */
                    pre.updateImg(photoUri);
                }
                break;
            case ZDialogConstantUtil.RESULT_LOAD_CODE:
                /**
                 * 从相册中选择图片，获取返回结果
                 */
                pre.notifyUpdate(4);
                if (data == null) {
                    return;
                } else {
                    Uri uri = data.getData();// 获取图片是以content开头
                    if (uri != null) {
                        // 进行相应的逻辑操作
                        pre.updateImg(uri);
                    }
                }
                break;
        }
    }

}
