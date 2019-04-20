package com.schoolpartime.schoolpartime.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.schoolpartime.schoolpartime.R;
import com.schoolpartime.schoolpartime.entity.DataModel;
import com.schoolpartime.schoolpartime.entity.WorkInfo;
import com.schoolpartime.schoolpartime.util.LogUtil;

import java.util.List;

/**
 * Created by Auser on 2018/3/13.
 * 这个适配器用于个人中心（用户信息界面）的功能列表展示
 */

public class WorkListAdapter extends BaseAdapter {

    private List<WorkInfo> workInfos;
    private Context context;

    public WorkListAdapter(List<WorkInfo> workInfos, Context context) {
        this.workInfos = workInfos;
        this.context = context;
    }

    @Override
    public int getCount() {
        return workInfos.size();
    }

    @Override
    public Object getItem(int position) {
        return workInfos.get(position);
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
                    R.layout.item_work, null);
            holder = new ViewHolder();
            holder.workTitle = convertView.findViewById(R.id.work_name);
            holder.workCity = convertView.findViewById(R.id.work_city);
            holder.workSalary = convertView.findViewById(R.id.work_salary);
            holder.workEndType = convertView.findViewById(R.id.work_end_type);
            holder.workAddress = convertView.findViewById(R.id.work_address);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.workTitle.setText(workInfos.get(position).getWorkTitle());
        holder.workCity.setText(workInfos.get(position).getCity());
        holder.workSalary.setText(workInfos.get(position).getMoney());
        holder.workEndType.setText(workInfos.get(position).getEnd_way());
        holder.workAddress.setText(workInfos.get(position).getAddress());
        return convertView;
    }

    private class ViewHolder {
        TextView workTitle;
        TextView workCity;
        TextView workSalary;
        TextView workEndType;
        TextView workAddress;
    }
}
