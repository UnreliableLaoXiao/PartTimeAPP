package com.schoolpartime.schoolpartime.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.schoolpartime.schoolpartime.R;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Auser on 2018/3/20.
 */

public class MyLoveTypeAdapter extends BaseAdapter {
    private Context context;
    private List<HashMap<String,Object>> list;
    private LayoutInflater layoutInflater;
    private TextView tv;
    private CheckBox cb;
    public MyLoveTypeAdapter(Context context, List<HashMap<String, Object>> list) {
        this.context = context;
        this.list = list;//list中checkbox状态为false
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
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
        tv.setText(list.get(position).get("name")+"");
        cb.setChecked((Boolean) list.get(position).get("boolean"));
        return convertView;
    }

    public class ViewCache{
        public TextView tv;
        public CheckBox cb;
    }
}
