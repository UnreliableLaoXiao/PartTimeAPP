package com.schoolpartime.schoolpartime.listener;

import android.view.View;

import com.schoolpartime.schoolpartime.R;
import com.schoolpartime.schoolpartime.entity.User;
import com.schoolpartime.schoolpartime.entity.baseModel.ResultModel;

import rx.Observable;
import rx.Subscriber;


public class ResultOnClickListener {

    private ResultCallback callback;

    public ResultOnClickListener(ResultCallback callback) {
        this.callback = callback;
    }

    public void onClick(View view) {
        switch (view.getId()){
            case R.id.submit_login:
            {
                callback.start().subscribe(new Subscriber<ResultModel<User>>() {
                            @Override
                            public void onCompleted() { }

                            @Override
                            public void onError(Throwable e) {
                                e.printStackTrace();
                                callback.failed();
                            }

                            @Override
                            public void onNext(ResultModel<User> resultModel) {
                                User user = resultModel.data;
                                callback.success(user);
                            }
                        });
            }
            break;
        }
    }

    public interface ResultCallback<T> {

        Observable<ResultModel<T>> start();

        void failed();

        void success(T t);

    }

}
