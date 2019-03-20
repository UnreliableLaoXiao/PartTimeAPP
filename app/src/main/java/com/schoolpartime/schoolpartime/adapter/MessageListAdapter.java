package com.schoolpartime.schoolpartime.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.schoolpartime.schoolpartime.R;
import com.schoolpartime.schoolpartime.entity.ChatMessage;

import java.util.List;

/**
 * Created by Auser on 2018/3/13.
 * 这个适配器用于个人中心（用户信息界面）的功能列表展示
 */

public class MessageListAdapter extends BaseAdapter {

    private List<ChatMessage> mess;
    private Context context;

    public MessageListAdapter(Context context,List<ChatMessage> mess) {
        this.context = context;
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
            convertView = View.inflate(context,
                    R.layout.item_chatmessage, null);
            holder = new ViewHolder();
            holder.mes_name = convertView
                    .findViewById(R.id.mes_name);
            holder.mes_content = convertView
                    .findViewById(R.id.mes_content);
            holder.mes_date = convertView
                    .findViewById(R.id.mes_date);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
//        holder.mes_image.setImageDrawable(context.getDrawable(mess.get(position).getImg()));
        holder.mes_content.setText(mess.get(position).getMes());
        holder.mes_name.setText(mess.get(position).getName());
        holder.mes_date.setText(mess.get(position).getDete());
        return convertView;
    }

    private class ViewHolder {
        ImageView mes_image;
        TextView mes_name;
        TextView mes_content;
        TextView mes_date;
    }
}
