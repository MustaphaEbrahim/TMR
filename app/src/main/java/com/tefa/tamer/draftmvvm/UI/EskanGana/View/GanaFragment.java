package com.tefa.tamer.draftmvvm.UI.EskanGana.View;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.tefa.tamer.databinding.FragmentGanaBinding;
import com.tefa.tamer.draftmvvm.UI.Base.BaseFragment;
import com.tefa.tamer.draftmvvm.UI.EskanGana.ViewModel.EskanGanaViewModel;

public class GanaFragment extends BaseFragment {

    private FragmentGanaBinding binding;
    private EskanGanaViewModel viewModel;


    public GanaFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         super.onCreateView(inflater, container, savedInstanceState);
         binding = FragmentGanaBinding.inflate(inflater, container, false);

         return binding.getRoot();
    }

    @Override
    public void initViewModel() {

    }

    @Override
    public void initObservers() {

    }

    @Override
    public void initLoading() {

    }

    @Override
    public void initOnErrorObserver() {

    }
}