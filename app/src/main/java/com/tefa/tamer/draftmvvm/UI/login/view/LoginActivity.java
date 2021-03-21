package com.tefa.tamer.draftmvvm.UI.login.view;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;


import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.tefa.tamer.draftmvvm.UI.Base.BaseActivity;
import com.tefa.tamer.draftmvvm.UI.Choose.View.ChooseActivity;
import com.tefa.tamer.draftmvvm.UI.Choose.View.TestActivity;
import com.tefa.tamer.draftmvvm.UI.CreateAccount.View.CreateActivity;
import com.tefa.tamer.draftmvvm.UI.Main.View.User;
import com.tefa.tamer.draftmvvm.UI.login.ViewModel.LoginViewModel;

public class LoginActivity extends BaseActivity {

    private com.tefa.tamer.databinding.ActivityLoginBinding binding;
    private LoginViewModel viewModel;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = com.tefa.tamer.databinding.ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        viewModel.getUser();
        initListener();

    }

    private void initListener() {
        //Login button
        binding.EmailSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(binding.email.getText().toString()) && !TextUtils.isEmpty(binding.password.getText().toString())){


                    String email = binding.email.getText().toString().trim();
                    String password = binding.password.getText().toString().trim();

                    viewModel.loginEmailPasswordUser(email, password);

                    //startJournalActivity();

                } else {
                    Toast.makeText(LoginActivity.this, "Empty Fields Not Allowed", Toast.LENGTH_LONG).show();
                }

            }
        });

        binding.CreateAcctButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this , CreateActivity.class));
            }
        });

    }

    private void startPostActivity(com.tefa.tamer.draftmvvm.UI.Main.View.User user) {

        Intent intent = new Intent(LoginActivity.this , TestActivity.class);
        intent.putExtra("username", user.getUserName());
        intent.putExtra("userId", user.getUserId());
        startActivity(intent);
        finish();

    }

    @Override
    public void initViewModel() {

        viewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        setViewModel(viewModel);

    }

    @Override
    public void initObservers() {
        viewModel.getIsSuccessMLD().observe(this, new Observer<User>() {
            @Override
            public void onChanged(com.tefa.tamer.draftmvvm.UI.Main.View.User user) {

                startPostActivity(user);
            }
        });

        viewModel.getUserAlreadyExistMLD().observe(this, new Observer<User>() {
            @Override
            public void onChanged(User user) {


                startPostActivity(user);

            }
        });
    }

    @Override
    public void initErrorObservers() {
        viewModel.getIsErrorMLD().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Toast.makeText(LoginActivity.this, s, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void initLoadingObservers() {

        viewModel.getIsLoadingMLD().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean){
                    binding.loginProgress.setVisibility(View.VISIBLE);
                }else {
                    binding.loginProgress.setVisibility(View.GONE);
                }
            }
        });
    }
}