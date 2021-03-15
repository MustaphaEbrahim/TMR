package com.tefa.tamer.draftmvvm.UI.EskanGana.View;

import android.os.Bundle;

import androidx.lifecycle.ViewModelProvider;

import com.tefa.tamer.R;
import com.tefa.tamer.databinding.ActivityEskanEgtmayBinding;
import com.tefa.tamer.databinding.ActivityEskanGanaBinding;
import com.tefa.tamer.draftmvvm.UI.Base.BaseActivity;
import com.tefa.tamer.draftmvvm.UI.EskanEgtamy.ViewModel.EskanEgtmayVIewModel;
import com.tefa.tamer.draftmvvm.UI.EskanGana.ViewModel.EskanGanaViewModel;

public class EskanGanaActivity extends BaseActivity {

    private ActivityEskanGanaBinding binding;
    private EskanGanaViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEskanGanaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

    @Override
    public void initViewModel() {
        viewModel = new ViewModelProvider(this).get(EskanGanaViewModel.class);
        setViewModel(viewModel);
    }

    @Override
    public void initObservers() {

    }

    @Override
    public void initErrorObservers() {

    }

    @Override
    public void initLoadingObservers() {

    }
}