package com.schoolpartime.schoolpartime.net.request;

import android.util.Log;

import com.schoolpartime.schoolpartime.entity.baseModel.ResultModel;
import com.schoolpartime.schoolpartime.filter.ResponseBodyFilter;
import com.schoolpartime.schoolpartime.net.request.base.RequestFactory;
import com.schoolpartime.schoolpartime.net.request.base.RequestResult;

import okhttp3.Response;
import retrofit2.Retrofit;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class HttpRequest {

    private static String TAG = "HttpRequest";

    public static Retrofit builder(){
        return RequestFactory.getInstance().Build(RequestFactory.TYPE.ALL);
    }

    public static Retrofit builder(RequestFactory.TYPE type){
        return RequestFactory.getInstance().Build(type);
    }

    public static void request(Observable observable, final RequestResult result,boolean isSecurity){
        if (isSecurity) {
            generalRequest(observable, result);
        } else {
            securityRequest(observable, result);
        }
    }


    private static void generalRequest(Observable observable, final RequestResult result){
        observable
                .subscribeOn(Schedulers.io())//IO线程加载数据
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResultModel>() {
                    @Override
                    public void onCompleted() {}

                    @Override
                    public void onError(Throwable e) {
                        result.fail(e);
                    }

                    @Override
                    public void onNext(ResultModel resultModel) {
                        result.success(resultModel);
                    }
                });
    }



    private static void securityRequest(Observable observable, final RequestResult result){
        observable
                .subscribeOn(Schedulers.io())//IO线程加载数据
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Response>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        result.fail(e);
                    }

                    @Override
                    public void onNext(Response response) {
                            try {
                                result.success(ResponseBodyFilter.filter(response));
                            } catch (Exception e) {
                                result.fail(e);
                                Log.e(TAG, "onNext: 数据转化错误",e );
                                e.printStackTrace();
                            }
                    }


                });
    }



}
