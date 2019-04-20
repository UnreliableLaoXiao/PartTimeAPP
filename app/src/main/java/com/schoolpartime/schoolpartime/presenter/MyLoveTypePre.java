package com.schoolpartime.schoolpartime.presenter;

import android.view.View;
import android.widget.AdapterView;

import com.schoolpartime.dao.entity.WorkType;
import com.schoolpartime.schoolpartime.SchoolPartimeApplication;
import com.schoolpartime.schoolpartime.SuperActivity;
import com.schoolpartime.schoolpartime.adapter.MyLoveTypeAdapter;
import com.schoolpartime.schoolpartime.databinding.ActivityMyLoveTypeBinding;
import com.schoolpartime.schoolpartime.util.LogUtil;

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
    }

    private int getLikeTypesSize() {
        int index = 0;
        if (likeTypes[0] != 0) index++;
        if (likeTypes[1] != 0) index++;
        if (likeTypes[2] != 0) index++;
        return index;
    }
}
