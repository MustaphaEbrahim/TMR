package com.tefa.tamer.draftmvvm.UI.PostData.View;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.tefa.tamer.databinding.ActivityPostEskanEgtmayBinding;
import com.tefa.tamer.draftmvvm.UI.Base.BaseActivity;
import com.tefa.tamer.draftmvvm.UI.Choose.View.TestActivity;
import com.tefa.tamer.draftmvvm.UI.EskanEgtamy.View.modelGawab;
import com.tefa.tamer.draftmvvm.UI.PostData.ViewModel.PostDataViewModel;

public class PostEskanEgtmay extends BaseActivity {



    private ActivityPostEskanEgtmayBinding binding;
    private PostDataViewModel viewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPostEskanEgtmayBinding.inflate(getLayoutInflater());
        viewModel.getUserEskan();
        setContentView(binding.getRoot());
        initListener();
    }

    private void initListener() {
        binding.uriPdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectPdf();
            }
        });

        
    }

    private void selectPdf() {
        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "PDF FILE SELECT"), 12);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 12 && resultCode == RESULT_OK && data != null && data.getData() != null){
            binding.saveButton.setEnabled(true);

            binding.uriPdf.setText(data.getDataString().substring(data.getDataString().lastIndexOf("/") + 1));

            binding.saveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    uploadPDFFileFirebase(data.getData());
                }
            });
        }
    }

    private void uploadPDFFileFirebase(Uri uriPdf) {

        String title = binding.answerTitle.getText().toString().trim();
        String date = binding.answerDate.getText().toString().trim();
        String number = binding.answerNumber.getText().toString().trim();
        String importSide = binding.importSide.getText().toString().trim();
        String exportSide = binding.exportSide.getText().toString().trim();

        if (!TextUtils.isEmpty(title) && !TextUtils.isEmpty(date) && !TextUtils.isEmpty(number) && uriPdf != null ){
            viewModel.saveEskanEgtmay(title,date,number,uriPdf,exportSide,importSide);
        } else {
            Toast.makeText(PostEskanEgtmay.this, "Empty Fields Not Allowed", Toast.LENGTH_LONG).show();
        }


    }

    @Override
    public void initViewModel() {
        viewModel = new ViewModelProvider(this).get(PostDataViewModel.class);
        setViewModel(viewModel);
    }

    @Override
    public void initObservers() {
        viewModel.getIsSuccessMLD().observe(this, new Observer<modelGawab>() {
            @Override
            public void onChanged(modelGawab modelGawab) {
              saveEgtmay();
            }
        });
    }

    private void saveEgtmay() {
        Intent intent = new Intent(PostEskanEgtmay.this, TestActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void initErrorObservers() {

        viewModel.getIsErrorMLD().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Toast.makeText(PostEskanEgtmay.this, s, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void initLoadingObservers() {

        viewModel.getIsloadingMLD().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isLoading) {

                if (isLoading){
                    binding.posrProgressBar.setVisibility(View.VISIBLE);
                }else {
                    binding.posrProgressBar.setVisibility(View.GONE);
                }

            }
        });

    }
}