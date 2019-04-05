package com.schoolpartime.schoolpartime.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.schoolpartime.schoolpartime.R;
import com.schoolpartime.schoolpartime.entity.ChatRecord;
import com.schoolpartime.schoolpartime.util.LogUtil;

import java.util.List;

public class MessageListAdapter extends BaseAdapter {

    private List<ChatRecord> mess;
    private Context context;

    public MessageListAdapter(Context context,List<ChatRecord> mess) {
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
//        holder.mes_image.setImageDrawable(context.getResources().getDrawable(R.drawable.people));
        holder.mes_content.setText(mess.get(position).getMes());
        holder.mes_name.setText(mess.get(position).getName());
        LogUtil.d("date:" + mess.get(position).getDate());
        holder.mes_date.setText(mess.get(position).getDate());
        return convertView;
    }

    private class ViewHolder {
        ImageView mes_image;
        TextView mes_name;
        TextView mes_content;
        TextView mes_date;
    }
}
