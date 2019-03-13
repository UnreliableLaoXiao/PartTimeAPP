package com.schoolpartime.schoolpartime.adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.schoolpartime.schoolpartime.R;

/**
 * Created by machenike on 2018/10/15.
 * 用于适配recycleview
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private Context mcontext;
    private MyOnItemClickListener mItemClickListener;

    public RecyclerAdapter(Context mcontext, MyOnItemClickListener mItemClickListener) {
        this.mcontext = mcontext;
        this.mItemClickListener = mItemClickListener;
    }

    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerAdapter.ViewHolder holder = new RecyclerAdapter.ViewHolder(LayoutInflater.from(mcontext).inflate(R.layout.item_work, parent, false));
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

    @Override
    public void onBindViewHolder(RecyclerAdapter.ViewHolder holder, int position) {
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View itemView) {
            super(itemView);
        }
    }

    public interface MyOnItemClickListener {
        void onItemClick(View view);
        void onItemLongClick(View view);
    }

}
