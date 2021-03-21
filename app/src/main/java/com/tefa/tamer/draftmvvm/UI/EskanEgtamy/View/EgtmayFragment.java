package com.tefa.tamer.draftmvvm.UI.EskanEgtamy.View;


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
import com.tefa.tamer.databinding.FragmentEgtmayBinding;
import com.tefa.tamer.draftmvvm.Adapters.AdapterModelGawab;
import com.tefa.tamer.draftmvvm.UI.Base.BaseFragment;
import com.tefa.tamer.draftmvvm.UI.Choose.View.TestActivity;
import com.tefa.tamer.draftmvvm.UI.EskanEgtamy.ViewModel.EskanEgtmayVIewModel;
import com.tefa.tamer.draftmvvm.UI.Main.View.User;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import static android.app.Activity.RESULT_OK;


public class EgtmayFragment extends BaseFragment {


    private FragmentEgtmayBinding binding;
    private EskanEgtmayVIewModel vIewModel;
    private AdapterModelGawab adapterModelGwab;

    private AlertDialog dialog;
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
        binding = FragmentEgtmayBinding.inflate(inflater, container, false);
        initRecyclerView();
        initListeners();
        vIewModel.getUser();
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
                // TODO Handle progress
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    vIewModel.getModelGawabList().clear();
                    adapterModelGwab.notifyDataSetChanged();
                    binding.progressBar.setVisibility(View.VISIBLE);
                    vIewModel.searchGawab(binding.searchEt.getText().toString());
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
                vIewModel.getModelGawabList().clear();
                adapterModelGwab.notifyDataSetChanged();
                vIewModel.getUserEskan();

                // TODO Refresh list in recyclerview
            }
        });


    }

    private void createAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

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
        /*saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!titleGwab.getText().toString().isEmpty() && !dateGwab.getText().toString().isEmpty()
                        && !numberGwab.getText().toString().isEmpty() && !pdfGwab.getText().toString().isEmpty() ){

                    String tGwab = titleGwab.getText().toString().trim();
                    String dGawb = dateGwab.getText().toString().trim();
                    String nGwab = numberGwab.getText().toString().trim();
                    String eGwab = exportGwab.getText().toString().trim();
                    String iGwab = importGwab.getText().toString().trim();
                    //URI

                    //vIewModel.saveEskanEgtmay(tGwab,dGawb,nGwab,,iGwab,eGwab);
                }else {
                    Snackbar.make(v, "Empty Fields not Allowed", Snackbar.LENGTH_SHORT)
                            .show();
                }
            }
        });*/

        builder.setView(view);
        dialog = builder.create();// create dialog
        dialog.show(); //show our dialog!!



    }

    private void selectPdf() {
        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "PDF FILE SELECT"), 12);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 12 && resultCode == RESULT_OK && data != null && data.getData() != null){
            saveButton.setEnabled(true);

            pdfGwab.setText(data.getDataString().substring(data.getDataString().lastIndexOf("/") + 1));

            saveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    uploadPDFFileFireBaser(data.getData());
                }
            });
        }
    }

    private void uploadPDFFileFireBaser(Uri uriPdf) {

        String tGwab = titleGwab.getText().toString().trim();
        String dGawb = dateGwab.getText().toString().trim();
        String nGwab = numberGwab.getText().toString().trim();
        String eGwab = exportGwab.getText().toString().trim();
        String iGwab = importGwab.getText().toString().trim();


        if (!TextUtils.isEmpty(tGwab) && !TextUtils.isEmpty(dGawb)
                && !TextUtils.isEmpty(nGwab) && uriPdf != null ){
            vIewModel.saveEskanEgtmay(tGwab,dGawb,nGwab,uriPdf,eGwab,iGwab);
        }else {
            Toast.makeText(getActivity(), "Empty Fields Not Allowed", Toast.LENGTH_LONG).show();
        }

    }

    private void initRecyclerView() {
        binding.recyclerview.setLayoutManager(new LinearLayoutManager(context));
        adapterModelGwab = new AdapterModelGawab(context, vIewModel.getModelGawabList());
        binding.recyclerview.setAdapter(adapterModelGwab);
    }

    @Override
    public void initViewModel() {
        vIewModel = new ViewModelProvider(this).get(EskanEgtmayVIewModel.class);
        setViewModel(vIewModel);
    }

    @Override
    public void initObservers() {

        vIewModel.getIsSuccesslMLD().observe(this, new Observer<modelGawab>() {
            @Override
            public void onChanged(modelGawab modelGawab) {
                saveEgtmay();
            }
        });

        vIewModel.getGawabResultMLD().observe(this, new Observer<List<modelGawab>>() {
            @Override
            public void onChanged(List<modelGawab> modelGawabList) {
                vIewModel.getModelGawabList().clear();
                vIewModel.getModelGawabList().addAll(modelGawabList);
                adapterModelGwab.notifyDataSetChanged();
                binding.progressBar.setVisibility(View.GONE);
            }
        });

        vIewModel.getIsSuccessMLD().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {

            }
        });

        vIewModel.getUserAlreadyExistMLD().observe(this, new Observer<User>() {
            @Override
            public void onChanged(User user) {
                vIewModel.setUser(user);
                vIewModel.getUserEskan();
            }
        });

        vIewModel.getIsEskanEgtmayReady().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                adapterModelGwab.notifyDataSetChanged();
            }
        });

    }

    private void saveEgtmay() {
        Intent intent = new Intent(getActivity() , TestActivity.class);
        startActivity(intent);


    }

    /*private void hideNoSearchResult() {
        binding.searchEt.setVisibility(View.GONE);
    }

    private void showNoSearchResult() {
        binding.searchEt.setVisibility(View.VISIBLE);
    }*/

    @Override
    public void initLoading() {
        vIewModel.getIsloadingMLD().observe(this, new Observer<Boolean>() {
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
        vIewModel.getIsErrorMLD().observe(this, new Observer<String>() {
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