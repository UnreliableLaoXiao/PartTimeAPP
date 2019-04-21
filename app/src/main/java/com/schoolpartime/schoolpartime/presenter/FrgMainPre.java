package com.schoolpartime.schoolpartime.presenter;

import android.animation.ValueAnimator;

import androidx.databinding.ViewDataBinding;

import android.graphics.Color;

import androidx.core.widget.NestedScrollView;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.jude.rollviewpager.hintview.ColorPointHintView;
import com.schoolpartime.schoolpartime.SuperActivity;
import com.schoolpartime.schoolpartime.activity.DetailsInfoActivity;
import com.schoolpartime.schoolpartime.adapter.LoopAdapter;
import com.schoolpartime.schoolpartime.adapter.WorkListAdapter;
import com.schoolpartime.schoolpartime.databinding.FragmentMainBinding;
import com.schoolpartime.schoolpartime.entity.WorkInfo;
import com.schoolpartime.schoolpartime.entity.baseModel.ResultModel;
import com.schoolpartime.schoolpartime.net.interfacz.LikeWorkInfoServer;
import com.schoolpartime.schoolpartime.net.request.HttpRequest;
import com.schoolpartime.schoolpartime.net.request.base.RequestResult;
import com.schoolpartime.schoolpartime.util.LogUtil;
import com.schoolpartime.schoolpartime.util.sp.SpCommonUtils;
import com.schoolpartime.schoolpartime.weiget.myListView.XrefershListviewListener;
import com.zaaach.citypicker.CityPicker;
import com.zaaach.citypicker.adapter.OnPickListener;
import com.zaaach.citypicker.model.City;
import com.zaaach.citypicker.model.LocateState;
import com.zaaach.citypicker.model.LocatedCity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class FrgMainPre implements Presenter, NestedScrollView.OnScrollChangeListener,  View.OnClickListener, AMapLocationListener ,
        XrefershListviewListener, AdapterView.OnItemClickListener {

    private FragmentMainBinding binding;
    private SuperActivity activity;
    private int mscrollY;
    private boolean isScroll = false;

    ArrayList<WorkInfo> workInfos = new ArrayList<>();

    //声明mlocationClient对象
    public AMapLocationClient mlocationClient;
    public AMapLocationClientOption mLocationOption = null;
    private WorkListAdapter adapter;

    @Override
    public void attach(ViewDataBinding binding, SuperActivity activity) {
        this.binding = (FragmentMainBinding) binding;
        this.activity = activity;
        init();
        getLocaltion();

    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (aMapLocation != null) {
            if (aMapLocation.getErrorCode() == 0) {
                //定位成功回调信息，设置相关消息
                aMapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见定位类型表
                aMapLocation.getLatitude();//获取纬度
                aMapLocation.getLongitude();//获取经度
                aMapLocation.getAccuracy();//获取精度信息
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = new Date(aMapLocation.getTime());
                df.format(date);//定位时间

                LogUtil.d("City:" + aMapLocation.getCity());

                binding.citypicker.setText("当前城市:" + aMapLocation.getCity());


            } else {
                //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                LogUtil.d("location Error, ErrCode:"
                        + aMapLocation.getErrorCode() + ", errInfo:"
                        + aMapLocation.getErrorInfo());
            }
        }

    }

    private void getLocaltion() {
        mlocationClient = new AMapLocationClient(activity);
        mLocationOption = new AMapLocationClientOption();
        mlocationClient.setLocationListener(this);
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        mLocationOption.setInterval(2000);
        mLocationOption.setOnceLocation(true);
        mLocationOption.setOnceLocationLatest(true);
        mlocationClient.setLocationOption(mLocationOption);
        mlocationClient.startLocation();
    }

    private void init() {
        openRollPageView();
        binding.fgMainScroll.setOnScrollChangeListener(this);
        binding.citypicker.setOnClickListener(this);
        initData(0);

        //设置adapter
        adapter = new WorkListAdapter(workInfos,activity);

        binding.rcyShow.setOnItemClickListener(this);

        binding.rcyShow.setAdapter(adapter);
        binding.rcyShow.setXrefershListviewListener(this);
    }

    private void initData(long id) {
        HttpRequest.request(HttpRequest.builder().create(LikeWorkInfoServer.class).getLikeWorkInfoNormal(id),
                new RequestResult() {
                    @Override
                    public void success(ResultModel resultModel) {
                        activity.dismiss();
                        LogUtil.d("得到兼职信息----------ResultModel：" + resultModel.toString());
                        if (resultModel.code == 200) {
                            workInfos.clear();
                            workInfos.addAll((ArrayList<WorkInfo>) resultModel.data);
                            adapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void fail(Throwable e) {
                        LogUtil.d("得到兼职信息----------失败");
                    }
                }, true);
    }

    @Override
    public void notifyUpdate(int code) {
        switch (code) {
            case 0: {
                if (SpCommonUtils.getIsLogin()) {
                    binding.goLogin.setText("我订阅的职位");
                    initData(SpCommonUtils.getUserId());
                } else {
                    binding.goLogin.setText("立即登录");
                }
            }
            break;
            case 1: {
                if (isScroll)
                    scroll_Start();
            }
            default:
                break;
        }
    }

    private void scroll_Start() {
        ValueAnimator valueAnimator = ValueAnimator.ofInt(mscrollY, 0);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (int) animation.getAnimatedValue();
                binding.fgMainScroll.scrollTo(0, value);
            }
        });
        valueAnimator.setDuration(1000);
        valueAnimator.start();
    }


    private void openRollPageView() {
        binding.rollviewpager.setAdapter(new LoopAdapter(binding.rollviewpager));
        binding.rollviewpager.setPlayDelay(3000);
        binding.rollviewpager.setHintView(new ColorPointHintView(activity, Color.RED, Color.GRAY));
    }

    @Override
    public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
        mscrollY = scrollY;
        if (scrollY > 800) {
            isScroll = true;
        }
        if (scrollY < 20) {
            isScroll = false;
        }
    }

    @Override
    public void onClick(View v) {

        CityPicker.getInstance()
                .setFragmentManager(activity.getSupportFragmentManager())  //此方法必须调用
                .enableAnimation(true)  //启用动画效果
                .setLocatedCity(new LocatedCity("杭州", "浙江", "101210101"))  //APP自身已定位的城市，默认为null（定位失败）
                .setOnPickListener(new OnPickListener() {
                    @Override
                    public void onPick(int position, City data) {
                        if (data != null)
                            binding.citypicker.setText("当前城市:" + data.getName() + "市");
                    }

                    @Override
                    public void onLocate() {
                        //开始定位，这里模拟一下定位
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                //定位完成之后更新数据
                                CityPicker.getInstance()
                                        .locateComplete(new LocatedCity("深圳", "广东", "101280601"), LocateState.SUCCESS);
                            }
                        }, 2000);
                    }
                })
                .show();
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                binding.rcyShow.computeScroll();
            }
        }, 3000);
    }

    @Override
    public void onLoadMore() {
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                binding.rcyShow.computeScroll();
            }
        }, 3000);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("workinfo",workInfos.get(position));
        (new DetailsInfoActivity()).inToActivity(activity,bundle);

    }
}
