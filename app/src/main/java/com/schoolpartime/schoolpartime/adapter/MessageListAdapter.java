package com.schoolpartime.schoolpartime.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.schoolpartime.schoolpartime.R;
import com.schoolpartime.schoolpartime.entity.ChatRecord;
import com.schoolpartime.schoolpartime.util.LogUtil;
import com.schoolpartime.schoolpartime.weiget.image.HeiCircleImageView;

import java.util.List;

public class MessageListAdapter extends BaseAdapter {

    private List<ChatRecord> mess;
    private Activity activity;

    public MessageListAdapter(Activity activity, List<ChatRecord> mess) {
        this.activity = activity;
        this.mess = mess;
    }

    @Override
    public int getCount() {
        return mess.size();
    }

    @Override
    public Object getItem(int position) {
        return mess.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(activity,
                    R.layout.item_chatmessage, null);
            holder = new ViewHolder();
            holder.mes_name = convertView
                    .findViewById(R.id.mes_name);
            holder.mes_content = convertView
                    .findViewById(R.id.mes_content);
            holder.mes_date = convertView
                    .findViewById(R.id.mes_date);
            holder.mes_image = convertView
                    .findViewById(R.id.mes_img);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.mes_image.setNetImg(mess.get(position).getImg(),activity);
        holder.mes_content.setText(mess.get(position).getMes());
        holder.mes_name.setText(mess.get(position).getName());
        LogUtil.d("date:" + mess.get(position).getDate());
        holder.mes_date.setText(mess.get(position).getDate());
        return convertView;
    }

    private class ViewHolder {
        HeiCircleImageView mes_image;
        TextView mes_name;
        TextView mes_content;
        TextView mes_date;
    }
}
