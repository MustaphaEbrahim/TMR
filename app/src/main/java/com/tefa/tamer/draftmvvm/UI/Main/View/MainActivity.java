package com.tefa.tamer.draftmvvm.UI.Main.View;



import androidx.lifecycle.ViewModelProvider;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.tefa.tamer.databinding.ActivityMainBinding;
import com.tefa.tamer.draftmvvm.UI.Base.BaseActivity;
import com.tefa.tamer.draftmvvm.UI.Main.ViewModel.MainActivityViewModel;
import com.tefa.tamer.draftmvvm.UI.login.view.LoginActivity;

public class MainActivity extends BaseActivity {

    private com.tefa.tamer.databinding.ActivityMainBinding binding;
    private MainActivityViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initListener();
    }

    private void initListener() {
        binding.getStartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this , LoginActivity.class));
            }
        });
    }

    @Override
    public void initViewModel() {

        viewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);
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