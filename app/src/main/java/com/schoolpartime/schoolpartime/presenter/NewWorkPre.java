package com.schoolpartime.schoolpartime.presenter;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.view.View;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectChangeListener;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.google.gson.Gson;
import com.schoolpartime.dao.entity.WorkType;
import com.schoolpartime.schoolpartime.R;
import com.schoolpartime.schoolpartime.SchoolPartimeApplication;
import com.schoolpartime.schoolpartime.SuperActivity;
import com.schoolpartime.schoolpartime.databinding.ActivityNewworkBinding;
import com.schoolpartime.schoolpartime.dialog.DialogUtil;
import com.schoolpartime.schoolpartime.entity.WorkInfo;
import com.schoolpartime.schoolpartime.entity.baseModel.ResultModel;
import com.schoolpartime.schoolpartime.net.interfacz.SendNewWorkInfoServer;
import com.schoolpartime.schoolpartime.net.request.HttpRequest;
import com.schoolpartime.schoolpartime.net.request.base.RequestResult;
import com.schoolpartime.schoolpartime.util.LogUtil;
import com.schoolpartime.schoolpartime.util.sp.SpCommonUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import androidx.databinding.ViewDataBinding;

public class NewWorkPre implements Presenter, View.OnClickListener {

    private OptionsPickerView pvOptions;

    Gson gson = new Gson();

    //  省份
    ArrayList<String> provinceBeanList = new ArrayList<>();
    //  城市
    ArrayList<String> cities;
    ArrayList<List<String>> cityList = new ArrayList<>();
    //  区/县
    ArrayList<String> district;
    ArrayList<List<String>> districts;
    ArrayList<List<List<String>>> districtList = new ArrayList<>();

    WorkInfo workInfo = new WorkInfo();

    SuperActivity activity;
    ActivityNewworkBinding binding;
    private String[] endWayArray;
    private String[] moneyPerArray;
    private ArrayList<WorkType> workTypes;
    private ArrayList<String> endWays;
    private ArrayList<String> moneyPers;

    @Override
    public void attach(ViewDataBinding binding, SuperActivity activity) {
        this.activity = activity;
        this.binding = (ActivityNewworkBinding) binding;
        init();

    }

    private void init() {
        workInfo.setId(0L);
        workInfo.setBossId(SpCommonUtils.getUserId());
        workInfo.setWorkStatu(0);

        ArrayList<String> works = new ArrayList<>();
        workTypes = (ArrayList<WorkType>) SchoolPartimeApplication.getmDaoSession().getWorkTypeDao().loadAll();
        for (WorkType workType: workTypes) {
            works.add(workType.getName());
        }
        binding.workType.attachDataSource(works);

        endWays = new ArrayList<>();
        endWayArray = activity.getResources().getStringArray(R.array.end_way);
        for (String str: endWayArray) {
            endWays.add(str);
        }
        binding.workEndWay.attachDataSource(endWays);

        moneyPerArray = activity.getResources().getStringArray(R.array.money_per);

        moneyPers = new ArrayList<>();
        for (String str: moneyPerArray) {
            moneyPers.add(str);
        }
        binding.workMoneyPer.attachDataSource(moneyPers);

        initJsonData();
        showPicker();

        binding.workAddress.setOnClickListener(this);
        binding.userBack.setOnClickListener(this);
        binding.workSend.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.work_address:{
                pvOptions.show();
            }
            break;
            case R.id.user_back:{
                activity.finish();
            }
            break;
            case R.id.work_send:{
                checkWorkInfo();
            }
            break;
        }
    }

    private void showResult(String mes){
        activity.showResult(binding.lly,mes);
    }

    private void checkWorkInfo() {
        if (binding.workTitle.getText().length() == 0){
            showResult("请输入兼职标题");
            return;
        }
        if (binding.workMoney.getText().length() == 0){
            showResult("请输入工资金额");
            return;
        }
        if (binding.workContants.getText().length() == 0){
            showResult("请输入联系人");
            return;
        }
        if (binding.workPhone.getText().length() == 0){
            showResult("请输入联系人电话");
            return;
        }
        if (binding.workContext.getText().length() == 0){
            showResult("请输入职位描述");
            return;
        }
        if (workInfo.getAddress() == null || workInfo.getCity() == null){
            showResult("请选择工作地点");
            return;
        }

        workInfo.setWorkTitle(binding.workTitle.getText().toString());
        workInfo.setMoney(binding.workMoney.getText().toString() + "元" + moneyPers.get(binding.workMoneyPer.getSelectedIndex()));
        workInfo.setContacts(binding.workContants.getText().toString());
        workInfo.setContactsWay(binding.workPhone.getText().toString());
        workInfo.setEnd_way(endWays.get(binding.workEndWay.getSelectedIndex()));
        workInfo.setWorkTypeId(workTypes.get(binding.workType.getSelectedIndex()).getId());
        workInfo.setWorkContext(binding.workContext.getText().toString());

        SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String format = df.format(new Date());
        workInfo.setCreateTime(format);
        LogUtil.d(workInfo.toString());

        sendWorkInfo(gson.toJson(workInfo));

    }

    private void sendWorkInfo(String data) {
        activity.show("正在发布...");
        HttpRequest.request(HttpRequest.builder().create(SendNewWorkInfoServer.class).addWorkInfo(data),
                new RequestResult() {
                    @Override
                    public void success(ResultModel resultModel) {
                        activity.dismiss();
                        LogUtil.d(" 发布新的兼职信息----------ResultModel：" + resultModel.toString());
                        if (resultModel.code == 200) {
                            showResult("上传成功!");
                            DialogUtil.selectDialog(activity, "提示：", "发布成功，点击确定返回上一页", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent();
                                    intent.putExtra("newworkinfo",workInfo);
                                    activity.setResult(1,intent);
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
                        LogUtil.d("发布新的兼职信息----------失败");
                    }
                }, true);
    }

    @Override
    public void notifyUpdate(int code) {

    }



    private void initJsonData() {
        //解析数据

        /**
         * 注意：assets 目录下的Json文件仅供参考，实际使用可自行替换文件
         * 关键逻辑在于循环体
         *
         * */

        String JsonData = getJson("province.json");//获取assets目录下的json文件数据
        parseJson(JsonData);
    }

    /**
     * 从asset目录下读取fileName文件内容
     *
     * @param fileName 待读取asset下的文件名
     * @return 得到省市县的String
     */
    private String getJson(String fileName) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            AssetManager assetManager = activity.getAssets();
            BufferedReader bf = new BufferedReader(new InputStreamReader(
                    assetManager.open(fileName)));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }




    /**
     * 解析json填充集合
     *
     * @param str 待解析的json，获取省市县
     */
    public void parseJson(String str) {
        try {
            //  获取json中的数组
            JSONArray jsonArray = new JSONArray(str);
            //  遍历数据组
            for (int i = 0; i < jsonArray.length(); i++) {
                //  获取省份的对象
                JSONObject provinceObject = jsonArray.optJSONObject(i);
                //  获取省份名称放入集合
                String provinceName = provinceObject.getString("name");
//                provinceBeanList.add(new ProvinceBean(provinceName));
                provinceBeanList.add(provinceName);
                //  获取城市数组
                JSONArray cityArray = provinceObject.optJSONArray("city");
                cities = new ArrayList<>();
                //   声明存放城市的集合
                districts = new ArrayList<>();
                //声明存放区县集合的集合
                //  遍历城市数组
                for (int j = 0; j < cityArray.length(); j++) {
                    //  获取城市对象
                    JSONObject cityObject = cityArray.optJSONObject(j);
                    //  将城市放入集合
                    String cityName = cityObject.optString("name");
                    cities.add(cityName);
                    district = new ArrayList<>();
                    // 声明存放区县的集合
                    //  获取区县的数组
                    JSONArray areaArray = cityObject.optJSONArray("area");
                    //  遍历区县数组，获取到区县名称并放入集合
                    for (int k = 0; k < areaArray.length(); k++) {
                        String areaName = areaArray.getString(k);
                        district.add(areaName);
                    }
                    //  将区县的集合放入集合
                    districts.add(district);
                }
                //  将存放区县集合的集合放入集合
                districtList.add(districts);
                //  将存放城市的集合放入集合
                cityList.add(cities);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void showPicker() {
        pvOptions = new OptionsPickerBuilder(activity, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                String city = provinceBeanList.get(options1);
                String address; //  如果是直辖市或者特别行政区只设置市和区/县
                if ("北京市".equals(city) || "上海市".equals(city) || "天津市".equals(city) || "重庆市".equals(city) || "澳门".equals(city) || "香港".equals(city)) {
                    address = provinceBeanList.get(options1) + "-" + districtList.get(options1).get(options2).get(options3);
                } else {
                    address = provinceBeanList.get(options1) + " " + cityList.get(options1).get(options2) + " " + districtList.get(options1).get(options2).get(options3);
//                    address = provinceBeanList.get(options1) + "-" + cityList.get(options1).get(options2);
                }
                binding.workAddress.setText(address);
                workInfo.setAddress(address);
                workInfo.setCity(cityList.get(options1).get(options2));
                LogUtil.d("当前选择的地点为："+address);
            }
        }).setOptionsSelectChangeListener(new OnOptionsSelectChangeListener() {
            @Override
            public void onOptionsSelectChanged(int options1, int options2, int options3) {

            }
        }).setSubmitText("确定")//确定按钮文字
                .setCancelText("取消")//取消按钮文字
                .setTitleText("城市选择")//标题
                .setSubCalSize(18)//确定和取消文字大小
                .setTitleSize(20)//标题文字大小
                .setTitleColor(Color.BLACK)//标题文字颜色
                .setSubmitColor(Color.BLACK)//确定按钮文字颜色
                .setCancelColor(Color.BLACK)//取消按钮文字颜色
                .setTitleBgColor(0xFF333333)//标题背景颜色 Night mode
                .setBgColor(R.color.white)//滚轮背景颜色 Night mode
                .setContentTextSize(18)//滚轮文字大小
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .setCyclic(false, false, false)//循环与否
                .setSelectOptions(0, 0, 0)  //设置默认选中项
                .setOutSideCancelable(false)//点击外部dismiss default true
                .isDialog(true)//是否显示为对话框样式
                .isRestoreItem(true)//切换时是否还原，设置默认选中第一项。
                .build();

        pvOptions.setPicker(provinceBeanList, cityList, districtList);//添加数据源

    }


}
