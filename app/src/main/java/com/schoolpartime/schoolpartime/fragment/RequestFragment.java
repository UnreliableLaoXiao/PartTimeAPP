package com.schoolpartime.schoolpartime.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.schoolpartime.schoolpartime.R;
import com.schoolpartime.schoolpartime.SuperActivity;
import com.schoolpartime.schoolpartime.databinding.FragmentRequestBinding;
import com.schoolpartime.schoolpartime.presenter.FrgRequestPre;
import com.schoolpartime.schoolpartime.presenter.Presenter;
import com.schoolpartime.schoolpartime.presenter.SendRecordPre;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

public class RequestFragment extends Fragment implements SendRecordPre.ClearAllListener {

    private Presenter pre = new FrgRequestPre();

    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentRequestBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_request, container, false);
        pre.attach(binding, (SuperActivity) getActivity());
        return binding.getRoot();
    }


    @Override
    public void onResume() {
        super.onResume();
        pre.notifyUpdate(0);
    }

    @Override
    public void clearAll() {
        pre.notifyUpdate(4);
    }
}
