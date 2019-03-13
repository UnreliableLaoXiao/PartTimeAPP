package com.schoolpartime.schoolpartime.fragment;

import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.schoolpartime.schoolpartime.R;
import com.schoolpartime.schoolpartime.SuperActivity;
import com.schoolpartime.schoolpartime.databinding.FragmentMainBinding;
import com.schoolpartime.schoolpartime.listener.IntentOnClickListener;
import com.schoolpartime.schoolpartime.presenter.FrgMainPre;
import com.schoolpartime.schoolpartime.presenter.Presenter;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment{

    private Presenter pre = new FrgMainPre();

    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentMainBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false);
        pre.attach(binding, (SuperActivity) getActivity());
        binding.setHandler(new IntentOnClickListener());
        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        pre.notifyUpdate(0);
    }

    public void scroll_Start() {
        pre.notifyUpdate(1);
    }
}
