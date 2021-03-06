package com.tefa.tamer.draftmvvm.UI.EskanGana.View;


import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.tefa.tamer.R;
import com.tefa.tamer.databinding.FragmentGanaBinding;
import com.tefa.tamer.draftmvvm.Adapters.AdapterEskanGana;
import com.tefa.tamer.draftmvvm.UI.Base.BaseFragment;
import com.tefa.tamer.draftmvvm.UI.Choose.View.TestActivity;
import com.tefa.tamer.draftmvvm.UI.EskanEgtamy.View.modelGawab;
import com.tefa.tamer.draftmvvm.UI.EskanGana.ViewModel.EskanGanaViewModel;
import com.tefa.tamer.draftmvvm.UI.Main.View.User;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import static android.app.Activity.RESULT_OK;

public class GanaFragment extends BaseFragment {

    private FragmentGanaBinding binding;
    private EskanGanaViewModel viewModel;
    private AdapterEskanGana adapterEskanGana;

    private EditText titleGwab;
    private EditText dateGwab;
    private EditText numberGwab;
    private EditText exportGwab;
    private EditText importGwab;
    private EditText pdfGwab;
    private Button saveButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
         super.onCreateView(inflater, container, savedInstanceState);
         binding = FragmentGanaBinding.inflate(inflater, container, false);
         initRecyclerView();
         initListeners();
         viewModel.getUser();

         return binding.getRoot();
    }

    private void initListeners() {
        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createAlertDialog();
            }
        });

        binding.searchEt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                if (actionId == EditorInfo.IME_ACTION_SEARCH){
                    viewModel.getGanaGawabList().clear();
                    adapterEskanGana.notifyDataSetChanged();
                    //binding.progressBar.setVisibility(View.VISIBLE);
                    viewModel.getIsloadingMLD().setValue(true);
                    viewModel.searchGawab(binding.searchEt.getText().toString());
                }

                return true;
            }
        });

        binding.searchEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (s.length() > 0) {
                    // text inside
                    binding.ivClear.setVisibility(View.VISIBLE);
                } else {
                    // empty layout
                    binding.ivClear.setVisibility(View.GONE);
                }


            }
        });

        binding.ivClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.searchEt.setText("");
                viewModel.getGanaGawabList().clear();
                adapterEskanGana.notifyDataSetChanged();
                viewModel.getUserGana();

                // TODO Refresh list in recyclerview
            }
        });

    }

    private void createAlertDialog() {
        AlertDialog.Builder builder =new AlertDialog.Builder(getContext());

        View view = getLayoutInflater().inflate(R.layout.popup, null);

        titleGwab = view.findViewById(R.id.answerTitle);
        dateGwab = view.findViewById(R.id.answerDate);
        numberGwab = view.findViewById(R.id.answerNumber);
        exportGwab = view.findViewById(R.id.exportSide);
        importGwab = view.findViewById(R.id.importSide);
        pdfGwab = view.findViewById(R.id.uriPdf);

        pdfGwab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectPdf();
            }
        });

        saveButton = view.findViewById(R.id.saveButton);
        saveButton.setEnabled(false);

        builder.setView(view);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void selectPdf() {
        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "PDF SELECTE"), 400);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 400 && resultCode == RESULT_OK && data != null && data.getData() != null){
            saveButton.setEnabled(true);
            pdfGwab.setText(data.getDataString().substring(data.getDataString().lastIndexOf("/") + 1));

            saveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    uploadingPDFFileFireBase(data.getData());
                }
            });
        }
    }

    private void uploadingPDFFileFireBase(Uri pdfUri) {

        String tGwab = titleGwab.getText().toString().trim();
        String dGawb = dateGwab.getText().toString().trim();
        String nGwab = numberGwab.getText().toString().trim();
        String eGwab = exportGwab.getText().toString().trim();
        String iGwab = importGwab.getText().toString().trim();

        if (!TextUtils.isEmpty(tGwab) && !TextUtils.isEmpty(dGawb)
                && !TextUtils.isEmpty(nGwab) && pdfUri != null ){
            viewModel.saveEskanGana(tGwab,dGawb,nGwab,pdfUri,eGwab,iGwab);
        }else {
            Toast.makeText(getActivity(), "Empty Fields Not Allowed", Toast.LENGTH_LONG).show();
        }

    }

    private void initRecyclerView() {

        binding.recyclerview.setLayoutManager(new LinearLayoutManager(context));
        adapterEskanGana = new AdapterEskanGana(context, viewModel.getGanaGawabList());
        binding.recyclerview.setAdapter(adapterEskanGana);

    }

    @Override
    public void initViewModel() {
        viewModel = new ViewModelProvider(this).get(EskanGanaViewModel.class);
        setViewModel(viewModel);
    }

    @Override
    public void initObservers() {

        viewModel.getIsSuccesslMLD().observe(this, new Observer<modelGawab>() {
            @Override
            public void onChanged(modelGawab modelGawab) {
                saveGana();
            }
        });

        viewModel.getGawabResultMLD().observe(this, new Observer<List<modelGawab>>() {
            @Override
            public void onChanged(List<modelGawab> modelGawabList) {
                viewModel.getGanaGawabList().clear();
                viewModel.getGanaGawabList().addAll(modelGawabList);
                adapterEskanGana.notifyDataSetChanged();
                viewModel.getIsloadingMLD().setValue(true);
                //binding.progressBar.setVisibility(View.GONE);
            }
        });

        viewModel.getIsSuccessMLD().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {

            }
        });

        viewModel.getUserAlreadyExistMLD().observe(this, new Observer<User>() {
            @Override
            public void onChanged(User user) {
                viewModel.setUser(user);
                viewModel.getUserGana();
            }
        });

        viewModel.getIsEskanGanaReady().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                adapterEskanGana.notifyDataSetChanged();
            }
        });

    }

    private void saveGana() {
        Intent intent = new Intent(getActivity() , TestActivity.class);
        startActivity(intent);
    }

    @Override
    public void initLoading() {
        viewModel.getIsloadingMLD().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean){
                    binding.progressBar.setVisibility(View.VISIBLE);
                }else {
                    binding.progressBar.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    public void initOnErrorObserver() {
        viewModel.getIsErrorMLD().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
        EventBus.getDefault().unregister(this);
    }
}