package com.tefa.tamer.draftmvvm.UI.EskanEgtamy.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.tefa.tamer.R;
import com.tefa.tamer.databinding.ActivityEskanEgtmayBinding;
import com.tefa.tamer.draftmvvm.Adapters.AdapterEskanEgtmay;
import com.tefa.tamer.draftmvvm.UI.Base.BaseActivity;
import com.tefa.tamer.draftmvvm.UI.Choose.View.ChooseActivity;
import com.tefa.tamer.draftmvvm.UI.EskanEgtamy.ViewModel.EskanEgtmayVIewModel;
import com.tefa.tamer.draftmvvm.UI.Main.View.User;

public class EskanEgtmayActivity extends BaseActivity {

    private ActivityEskanEgtmayBinding binding;
    private EskanEgtmayVIewModel vIewModel;
    private AdapterEskanEgtmay adapterEskanEgtmay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEskanEgtmayBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initRecyclerView();
        defineToolbar();
        vIewModel.getUser();
        initListener();
    }

    private void initListener() {
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
                //take users to add Journal
                Intent intent = new Intent(EskanEgtmayActivity.this , ChooseActivity.class);
                startActivity(intent);
                break;
            case R.id.action_signout:
                //sign user out
                vIewModel.signOut();

                break;

        }
        return super.onOptionsItemSelected(item);
    }

    public long downloadFile(Context context , String fileName , String fileExtension , String destinationDirection , String url){

        DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        Uri uri = Uri.parse(url);
        DownloadManager.Request request = new DownloadManager.Request(uri);

        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalFilesDir(context, destinationDirection, fileName + fileExtension);

        return downloadManager.enqueue(request);

    }

    private void initRecyclerView() {
        binding.recyclerview.setLayoutManager(new LinearLayoutManager(context));
        adapterEskanEgtmay = new AdapterEskanEgtmay(vIewModel.getEskanEgtamyList());
        binding.recyclerview.setAdapter(adapterEskanEgtmay);
    }

    @Override
    public void initViewModel() {
        vIewModel = new ViewModelProvider(this).get(EskanEgtmayVIewModel.class);
        setViewModel(vIewModel);
    }

    @Override
    public void initObservers() {
        vIewModel.getIsSuccessMLD().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
            }
        });

        vIewModel.getUserAlreadyExistMLD().observe(this, new Observer<User>() {
            @Override
            public void onChanged(User user) {
                vIewModel.getUserEskan(user);
            }
        });

        vIewModel.getIsEskanEgtmayReady().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                adapterEskanEgtmay.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void initErrorObservers() {

    }

    @Override
    public void initLoadingObservers() {

    }
    @Override
    protected void onStart() {
        super.onStart();

    }
}