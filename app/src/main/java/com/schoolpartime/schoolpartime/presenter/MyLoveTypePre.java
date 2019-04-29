package com.schoolpartime.schoolpartime.presenter;

import android.content.DialogInterface;
import android.view.View;
import android.widget.AdapterView;

import com.schoolpartime.dao.entity.WorkType;
import com.schoolpartime.schoolpartime.SchoolPartimeApplication;
import com.schoolpartime.schoolpartime.SuperActivity;
import com.schoolpartime.schoolpartime.adapter.MyLoveTypeAdapter;
import com.schoolpartime.schoolpartime.databinding.ActivityMyLoveTypeBinding;
import com.schoolpartime.schoolpartime.dialog.DialogUtil;
import com.schoolpartime.schoolpartime.entity.baseModel.ResultModel;
import com.schoolpartime.schoolpartime.net.interfacz.SetLikeTypeServer;
import com.schoolpartime.schoolpartime.net.request.HttpRequest;
import com.schoolpartime.schoolpartime.net.request.base.RequestResult;
import com.schoolpartime.schoolpartime.util.LogUtil;
import com.schoolpartime.schoolpartime.util.sp.SpCommonUtils;

import java.util.ArrayList;

import androidx.databinding.ViewDataBinding;

public class MyLoveTypePre implements Presenter, View.OnClickListener {

    ActivityMyLoveTypeBinding binding;
    SuperActivity activity;
    private MyLoveTypeAdapter adapter;
    private int[] likeTypes = new int[3];

    ArrayList<WorkType> workTypes = new ArrayList<>();

    @Override
    public void attach(ViewDataBinding binding, SuperActivity activity) {
        this.activity = activity;
        this.binding = (ActivityMyLoveTypeBinding) binding;
        workTypes = (ArrayList<WorkType>) SchoolPartimeApplication.getmDaoSession().getWorkTypeDao().loadAll();
        LogUtil.d("得到兼职数量："  + workTypes.size());
        init();


    }

    private void init() {
        binding.myloveComplete.setOnClickListener(this);
        adapter = new MyLoveTypeAdapter(activity,workTypes,likeTypes);
        binding.listMylove.setAdapter(adapter);
        binding.listMylove.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                MyLoveTypeAdapter.ViewCache viewCache = (MyLoveTypeAdapter.ViewCache) view.getTag();
                if(getLikeTypesSize() <=2){
                    if(viewCache.cb.isChecked()){//被选中状态
                        viewCache.cb.setChecked(false);
                        removeType(workTypes.get(position).getId());
                        activity.showResult(binding.rly,"取消选择：" + workTypes.get(position).getName());
                    }else {   //从选中状态转化为未选中
                        viewCache.cb.setChecked(true);
                        addType(workTypes.get(position).getId());
                        activity.showResult(binding.rly,"当前选择：" + workTypes.get(position).getName());
                    }
                    adapter.notifyDataSetChanged();
                }else if(getLikeTypesSize() ==3 && isContains(workTypes.get(position).getId())){
                    if(viewCache.cb.isChecked()){//被选中状态
                        viewCache.cb.setChecked(false);
                        removeType(workTypes.get(position).getId());
                        activity.showResult(binding.rly,"取消选择：" + workTypes.get(position).getName());
                    }else//从选中状态转化为未选中
                    {
                        viewCache.cb.setChecked(true);
                        addType(workTypes.get(position).getId());
                        activity.showResult(binding.rly,"当前选择：" + workTypes.get(position).getName());
                    }
                    adapter.notifyDataSetChanged();
                }else{
                    activity.showResult(binding.rly,"最多选择三项！");
                }
            }
        });


    }

    private boolean isContains(Integer id) {
        for (int i=0;i<3;i++){
            if (likeTypes[i] == id) {
                return true;
            }
        }
        return false;
    }

    private void addType(Integer id) {
        for (int i=0;i<3;i++){
            if (likeTypes[i] == 0) {
                likeTypes[i] = id;
                break;
            }
        }
    }

    private void removeType(Integer id) {
        for (int i=0;i<3;i++){
            if (likeTypes[i] == id) {
                likeTypes[i] = 0;
                break;
            }
        }
    }

    @Override
    public void notifyUpdate(int code) {

    }

    @Override
    public void onClick(View v) {
        if(getLikeTypesSize() == 0){
            activity.showResult(binding.rly,"请选择至少一项！");
        }else{
            getILikeType();
        }
    }

    private void getILikeType() {
        activity.show("正在修改");
        HttpRequest.request(HttpRequest.builder().create(SetLikeTypeServer.class).
                        setLikeType(SpCommonUtils.getUserId(),likeTypes[0],likeTypes[1],likeTypes[2]),
                new RequestResult() {
                    @Override
                    public void success(ResultModel resultModel) {
                        activity.dismiss();
                        LogUtil.d("修改喜好----------ResultModel："+resultModel.toString());
                        if (resultModel.code == 200) {
                            activity.showResult(binding.rly,"修改成功！");
                            DialogUtil.select2Dialog(activity, "提示：", "修改成功？", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {



                                }
                            });
                        } else {
                            activity.showResult(binding.rly,"修改失败！");
                        }
                    }

                    @Override
                    public void fail(Throwable e) {
                        e.printStackTrace();
                        activity.showResult(binding.rly,"修改异常！");
                        activity.dismiss();
                    }
                },true);
    }

    private int getLikeTypesSize() {
        int index = 0;
        if (likeTypes[0] != 0) index++;
        if (likeTypes[1] != 0) index++;
        if (likeTypes[2] != 0) index++;
        return index;
    }
}
