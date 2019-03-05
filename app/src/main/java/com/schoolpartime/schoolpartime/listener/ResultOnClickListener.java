package com.schoolpartime.schoolpartime.listener;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.view.View;

import com.schoolpartime.schoolpartime.R;


public class ResultOnClickListener {

    private ResultCallback callback;

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            callback.success("Hello");
        }
    };

    public ResultOnClickListener(ResultCallback callback) {
        this.callback = callback;
    }

    public void onClick(View view) {
        switch (view.getId()){
            case R.id.submit_login:
            {
                callback.start();
                handler.sendEmptyMessageDelayed(1,3000);

            }
            break;
        }
    }

    public interface ResultCallback<T> {

        void start();

        void failed();

        void success(T t);

    }

}
