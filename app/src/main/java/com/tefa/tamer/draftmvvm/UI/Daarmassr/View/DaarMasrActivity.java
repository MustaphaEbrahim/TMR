package com.tefa.tamer.draftmvvm.UI.Daarmassr.View;

import android.os.Bundle;

import androidx.lifecycle.ViewModelProvider;

import com.tefa.tamer.R;
import com.tefa.tamer.databinding.ActivityDaarMasrBinding;
import com.tefa.tamer.draftmvvm.UI.Base.BaseActivity;
import com.tefa.tamer.draftmvvm.UI.Daarmassr.ViewModel.DarMsrViewModel;


public class DaarMasrActivity extends BaseActivity {

    private ActivityDaarMasrBinding binding;
    private DarMsrViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDaarMasrBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

    @Override
    public void initViewModel() {
        viewModel = new ViewModelProvider(this).get(DarMsrViewModel.class);
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