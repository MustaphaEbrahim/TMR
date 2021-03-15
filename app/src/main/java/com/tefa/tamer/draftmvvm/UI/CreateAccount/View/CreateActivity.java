package com.tefa.tamer.draftmvvm.UI.CreateAccount.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.tefa.tamer.R;
import com.tefa.tamer.databinding.ActivityCreateBinding;
import com.tefa.tamer.draftmvvm.UI.Base.BaseActivity;
import com.tefa.tamer.draftmvvm.UI.Choose.View.ChooseActivity;
import com.tefa.tamer.draftmvvm.UI.CreateAccount.ViewModel.CreateViewModel;
import com.tefa.tamer.draftmvvm.UI.Main.View.User;

public class CreateActivity extends BaseActivity {

    private ActivityCreateBinding binding;
    private CreateViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreateBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        viewModel.getUser();
        // el moder kabas xD
        // swany w harg3lak
        initListener();
    }

    private void initListener() {

        binding.CreateAcctButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(binding.userNameAcct.getText().toString())
                        && !TextUtils.isEmpty(binding.emailAcct.getText().toString())
                        && !TextUtils.isEmpty(binding.passwordAcct.getText().toString())) {


                    String email = binding.emailAcct.getText().toString().trim();
                    String password = binding.passwordAcct.getText().toString().trim();
                    String username = binding.userNameAcct.getText().toString().trim();

                    viewModel.createUserEmailAccount(email, password, username);

                }else{
                    Toast.makeText(CreateActivity.this, "Empty Fields Not Allowed", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public void initViewModel() {
        viewModel = new ViewModelProvider(this).get(CreateViewModel.class);
        setViewModel(viewModel);
    }

    @Override
    public void initObservers() {

        viewModel.getIsSuccessMLD().observe(this, new Observer<User>() {
            @Override
            public void onChanged(User user) {
                startPostActivity(user);
            }
        });

    }

    private void startPostActivity(User user) {

        Intent intent = new Intent(CreateActivity.this , ChooseActivity.class);
        intent.putExtra("username", user.getUserName());
        intent.putExtra("userId" , user.getUserId());
        startActivity(intent);

    }

    @Override
    public void initErrorObservers() {

        viewModel.getIsErrorMLD().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Toast.makeText(CreateActivity.this, s, Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void initLoadingObservers() {

        viewModel.getIsLoadingMLD().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isLoading) {
                if (isLoading) {
                    binding.Createacctprogress.setVisibility(View.VISIBLE);
                } else {
                    binding.Createacctprogress.setVisibility(View.GONE);
                }
            }
        });

    }
}