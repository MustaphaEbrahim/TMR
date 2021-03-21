package com.tefa.tamer.draftmvvm.UI.Daarmassr.View;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.tefa.tamer.databinding.FragmentDarMasrBinding;
import com.tefa.tamer.draftmvvm.UI.Base.BaseFragment;
import com.tefa.tamer.draftmvvm.UI.Daarmassr.ViewModel.DarMsrViewModel;

public class DarMasrFragment extends BaseFragment {

    private FragmentDarMasrBinding binding;
    private DarMsrViewModel viewModel;

    public DarMasrFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        binding = FragmentDarMasrBinding.inflate(inflater , container , false);

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