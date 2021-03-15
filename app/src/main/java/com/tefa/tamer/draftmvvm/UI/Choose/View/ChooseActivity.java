package com.tefa.tamer.draftmvvm.UI.Choose.View;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.tefa.tamer.R;
import com.tefa.tamer.databinding.ActivityChooseBinding;
import com.tefa.tamer.draftmvvm.UI.Daarmassr.View.DaarMasrActivity;
import com.tefa.tamer.draftmvvm.UI.EskanGana.View.EskanGanaActivity;
import com.tefa.tamer.draftmvvm.UI.Base.BaseActivity;
import com.tefa.tamer.draftmvvm.UI.Choose.ViewModel.ChooseViewModel;
import com.tefa.tamer.draftmvvm.UI.EskanEgtamy.View.EskanEgtmayActivity;
import com.tefa.tamer.draftmvvm.UI.Main.View.MainActivity;
import com.tefa.tamer.draftmvvm.UI.Main.View.User;
import com.tefa.tamer.draftmvvm.UI.PostData.View.PostEskanEgtmay;

public class ChooseActivity extends BaseActivity {

    private ActivityChooseBinding binding;
    private ChooseViewModel viewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChooseBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        defineToolbar();
        viewModel.getUser();
        // kosomak ya tefa xD <3
    }

    private void defineToolbar() {


        //    final Drawable upArrow = getResources().getDrawable(R.drawable.ic_toolbar_back);
        // upArrow.setColorFilter(getResources().getColor(R.color.grey), PorterDuff.Mode.SRC_ATOP);

        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayUseLogoEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        // getSupportActionBar().setHomeAsUpIndicator(upArrow);
        getSupportActionBar().setTitle(context.getString(R.string.app_name));

        // el mafrod el dnya tmam
        // msh 3arf eh el moshkela estana nshof

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_add:
                Intent intent = new Intent(ChooseActivity.this,EskanEgtmayActivity.class);
                startActivity(intent);
                break;
            case  R.id.action_signout:
                viewModel.signOut();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void initViewModel() {

        viewModel = new ViewModelProvider(this).get(ChooseViewModel.class);
        setViewModel(viewModel);

    }

    @Override
    public void initObservers() {
        
        viewModel.getIsSuccessMLD().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                startSplashActivity();
            }
        });

        viewModel.getUserAlreadyExistMLD().observe(this, new Observer<User>() {
            @Override
            public void onChanged(User user) {

            }
        });



    }

    private void startSplashActivity() {

        Intent intent = new Intent(ChooseActivity.this, MainActivity.class);
        startActivity(intent);
        finish();

    }

    @Override
    public void initErrorObservers() {

    }

    @Override
    public void initLoadingObservers() {

    }

    public void eskanEgtmai(View view) {
        Intent intent = new Intent(ChooseActivity.this, PostEskanEgtmay.class);
        startActivity(intent);
    }

    public void daarmasr(View view) {
        Intent intent = new Intent(ChooseActivity.this , DaarMasrActivity.class);
        startActivity(intent);
    }

    public void eskangana(View view) {
        Intent intent = new Intent(ChooseActivity.this, EskanGanaActivity.class);
        startActivity(intent);
    }
}