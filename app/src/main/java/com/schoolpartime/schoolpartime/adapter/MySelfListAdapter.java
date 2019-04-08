package com.schoolpartime.schoolpartime.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.schoolpartime.schoolpartime.R;
import com.schoolpartime.schoolpartime.entity.DataModel;
import com.schoolpartime.schoolpartime.util.LogUtil;

import java.util.List;

/**
 * Created by Auser on 2018/3/13.
 * 这个适配器用于个人中心（用户信息界面）的功能列表展示
 */

public class MySelfListAdapter extends BaseAdapter {

    private List<DataModel> list_user;
    private Context context;

    public MySelfListAdapter(List<DataModel> list_user , Context context) {
        this.list_user = list_user;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list_user.size();
    }

    @Override
    public Object getItem(int position) {
        return list_user.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(context,
                    R.layout.item_data, null);
            holder = new ViewHolder();
            holder.user_name = convertView
                    .findViewById(R.id.user_name);
            holder.user_image = convertView
                    .findViewById(R.id.user_image);
            holder.badge = convertView
                    .findViewById(R.id.badge);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.user_name.setText(list_user.get(position).user_name);
        holder.user_image.setImageResource(list_user.get(position).user_image);
        if (list_user.get(position).number > 0){
            LogUtil.d("显示数量------------->");
            holder.badge.setVisibility(View.VISIBLE);
            holder.badge.setText(list_user.get(position).number+"");
        }else {
            holder.badge.setVisibility(View.GONE);
        }
        return convertView;
    }

    private class ViewHolder {
        ImageView user_image;
        TextView user_name;
        TextView badge;
    }
}
