package com.schoolpartime.schoolpartime.dialog;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.schoolpartime.schoolpartime.R;

/**
 * 常用Dialog集合
 */

public class DialogUtil {

    public static Dialog loadingDialog(Context content, String mes) {
        Dialog mLoadingDialog;
        @SuppressLint("InflateParams") View view = LayoutInflater.from(content).inflate(R.layout.dialog_loading, null);
        TextView loadingText = view.findViewById(R.id.text);
        loadingText.setText(mes);
        mLoadingDialog = new Dialog(content, R.style.loading_dialog_style);
        mLoadingDialog.setCancelable(false);
        mLoadingDialog.setContentView(view, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        return mLoadingDialog;
    }

    public static void selectDialog(Context context, String title, String message, DialogInterface.OnClickListener listener){
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton("确定",listener);
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) { }
        });
        builder.create().show();
    }

}
