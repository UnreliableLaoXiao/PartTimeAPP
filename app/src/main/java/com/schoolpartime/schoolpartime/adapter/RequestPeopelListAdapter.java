package com.schoolpartime.schoolpartime.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.schoolpartime.dao.entity.UserInfo;
import com.schoolpartime.schoolpartime.R;
import com.schoolpartime.schoolpartime.weiget.niceSpinner.NiceSpinner;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Auser on 2018/3/13.
 * 这个适配器用于个人中心（用户信息界面）的功能列表展示
 */

public class RequestPeopelListAdapter extends BaseAdapter {

    private List<UserInfo> userInfos;
    private Context context;
    private ArrayList<String> selectType = new ArrayList<>();
    private RequestSelectListener requestSelectListener;

    public interface RequestSelectListener {
        void selectYes(UserInfo userInfo);
        void selectNo(UserInfo userInfo);
        void chat(UserInfo userInfo);
    }

    public RequestPeopelListAdapter(List<UserInfo> userInfos, Context context,RequestSelectListener requestSelectListener) {
        this.userInfos = userInfos;
        this.context = context;
        this.requestSelectListener = requestSelectListener;
        selectType.add("同意");
        selectType.add("拒绝");
    }

    @Override
    public int getCount() {
        return userInfos.size();
    }

    @Override
    public Object getItem(int position) {
        return userInfos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(context,
                    R.layout.item_people, null);
            holder = new ViewHolder();
            holder.username = convertView.findViewById(R.id.user_name);
            holder.usersex = convertView.findViewById(R.id.user_sex);
            holder.userage = convertView.findViewById(R.id.user_age);
            holder.useraddress = convertView.findViewById(R.id.user_address);
            holder.bt_yes = convertView.findViewById(R.id.bt_yes);
            holder.bt_no = convertView.findViewById(R.id.bt_no);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.username.setText(userInfos.get(position).getUsername());
        holder.usersex.setText(userInfos.get(position).getUsersex());
        holder.userage.setText(userInfos.get(position).getUserage()+"");
        holder.useraddress.setText(userInfos.get(position).getAddress());
        holder.bt_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestSelectListener.selectNo(userInfos.get(position));
            }
        });
        holder.bt_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestSelectListener.selectYes(userInfos.get(position));
            }
        });
        holder.username.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestSelectListener.chat(userInfos.get(position));
            }
        });
        return convertView;
    }

    private class ViewHolder {
        TextView username;
        TextView userage;
        TextView usersex;
        TextView useraddress;
        Button bt_yes;
        Button bt_no;
    }
}
