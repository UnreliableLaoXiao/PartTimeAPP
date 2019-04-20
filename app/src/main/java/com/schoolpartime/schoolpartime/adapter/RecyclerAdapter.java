package com.schoolpartime.schoolpartime.adapter;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.schoolpartime.schoolpartime.R;
import com.schoolpartime.schoolpartime.entity.WorkInfo;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by machenike on 2018/10/15.
 * 用于适配recycleview
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private Context mcontext;
    private MyOnItemClickListener mItemClickListener;
    ArrayList<WorkInfo> workInfos = null;

    public RecyclerAdapter(ArrayList<WorkInfo> workInfos, Context mcontext, MyOnItemClickListener mItemClickListener) {
        this.mcontext = mcontext;
        this.mItemClickListener = mItemClickListener;
        this.workInfos = workInfos;
    }

    @Override
    public void onBindViewHolder(RecyclerAdapter.ViewHolder holder, int position) {
        holder.workTitle.setText(workInfos.get(position).getWorkTitle());
        holder.workCity.setText(workInfos.get(position).getCity());
        holder.workSalary.setText(workInfos.get(position).getMoney());
        holder.workEndType.setText(workInfos.get(position).getEnd_way());
        holder.workAddress.setText(workInfos.get(position).getAddress());
    }

    @Override
    public int getItemCount() {
        return workInfos.size();
    }

    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerAdapter.ViewHolder holder = null;
        holder = new RecyclerAdapter.ViewHolder(LayoutInflater.from(mcontext).inflate(R.layout.item_work, parent, false));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mItemClickListener.onItemClick(v);
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                mItemClickListener.onItemLongClick(v);
                return true;
            }
        });

        return holder;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView workTitle;
        TextView workCity;
        TextView workSalary;
        TextView workEndType;
        TextView workAddress;

        public ViewHolder(View itemView) {
            super(itemView);
            workTitle = itemView.findViewById(R.id.work_name);
            workCity = itemView.findViewById(R.id.work_city);
            workSalary = itemView.findViewById(R.id.work_salary);
            workEndType = itemView.findViewById(R.id.work_end_type);
            workAddress = itemView.findViewById(R.id.work_address);
        }
    }


    public interface MyOnItemClickListener {
        void onItemClick(View view);

        void onItemLongClick(View view);
    }

}
