package com.schoolpartime.schoolpartime.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.schoolpartime.schoolpartime.R;
import com.schoolpartime.schoolpartime.activity.DetailsInfoActivity;
import com.schoolpartime.schoolpartime.adapter.RecyclerAdapter;
import com.schoolpartime.schoolpartime.entity.WorkInfo;

import java.util.ArrayList;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ListFragment extends Fragment {

    private RecyclerView recyclerView;
    ArrayList<WorkInfo> workInfos = new ArrayList<>();
    private RecyclerAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        recyclerView = (RecyclerView) inflater.inflate(R.layout.list_fragment,container,false);
        return recyclerView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new RecyclerAdapter(workInfos,getContext(), new RecyclerAdapter.MyOnItemClickListener() {
            @Override
            public void onItemClick(View view) {
                (new DetailsInfoActivity()).inToActivity(getActivity());
            }

            @Override
            public void onItemLongClick(View view) {

            }
        });
        recyclerView.setAdapter(adapter);//设置adapter
    }


}
