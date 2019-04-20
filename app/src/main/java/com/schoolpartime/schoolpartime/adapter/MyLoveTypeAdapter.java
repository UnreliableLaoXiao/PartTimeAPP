package com.schoolpartime.schoolpartime.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.schoolpartime.dao.entity.WorkType;
import com.schoolpartime.schoolpartime.R;

import java.util.ArrayList;

/**
 * Created by Auser on 2018/3/20.
 */

public class MyLoveTypeAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<WorkType> workTypes;
    private int[] likeTypes;

    private LayoutInflater layoutInflater;
    private TextView tv;
    private CheckBox cb;
    public MyLoveTypeAdapter(Context context, ArrayList<WorkType> workTypes,int[] likeTypes) {
        this.context = context;
        this.workTypes = workTypes;   //全部兼职
        this.likeTypes = likeTypes;   //已经选择的类型
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return workTypes.size();
    }

    @Override
    public Object getItem(int position) {
        return workTypes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = layoutInflater.inflate(R.layout.item_worktype_check, null);
            ViewCache viewCache = new ViewCache();
            tv = (TextView) convertView.findViewById(R.id.item_tv);
            cb = (CheckBox) convertView.findViewById(R.id.cb);

            viewCache.tv = tv;
            viewCache.cb = cb;
            convertView.setTag(viewCache);
        }else
        {
            ViewCache viewCache = (ViewCache) convertView.getTag();
            tv = viewCache.tv;
            cb = viewCache.cb;
        }
        tv.setText(workTypes.get(position).getName());
        if ( likeTypes[0] == workTypes.get(position).getId() || likeTypes[1] == workTypes.get(position).getId() || likeTypes[2] == workTypes.get(position).getId()) {
            cb.setChecked(true);
        }else {
            cb.setChecked(false);
        }
        return convertView;
    }

    public class ViewCache{
        public TextView tv;
        public CheckBox cb;
    }
}
