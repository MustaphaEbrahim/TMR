package com.tefa.tamer.draftmvvm.UI.Daarmassr.View;


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
import com.tefa.tamer.databinding.FragmentDarMasrBinding;
import com.tefa.tamer.draftmvvm.Adapters.AdapterModelGawab;
import com.tefa.tamer.draftmvvm.Adapters.OnGawabClickListener;
import com.tefa.tamer.draftmvvm.UI.Base.BaseFragment;
import com.tefa.tamer.draftmvvm.UI.Daarmassr.ViewModel.DarMsrViewModel;
import com.tefa.tamer.draftmvvm.UI.EskanEgtamy.View.modelGawab;
import com.tefa.tamer.draftmvvm.UI.Main.View.User;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import static android.app.Activity.RESULT_OK;

public class DarMasrFragment extends BaseFragment implements OnGawabClickListener {

    private FragmentDarMasrBinding binding;
    private DarMsrViewModel viewModel;
    private AdapterModelGawab adapterModelGawab;

    private AlertDialog dialog;
    private EditText titleGwab;
    private EditText dateGwab;
    private EditText numberGwab;
    private EditText exportGwab;
    private EditText importGwab,pdfGwab;
    private EditText editTittle, editNumber,editexportSide, editImportSide,deleteNumber;
    private Button saveButton,editSaveButton,deleteSaveButton;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        binding = FragmentDarMasrBinding.inflate(inflater , container , false);

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
                    viewModel.getDarMasrList().clear();
                    adapterModelGawab.notifyDataSetChanged();
                    binding.progressBar.setVisibility(View.VISIBLE);
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
                viewModel.getDarMasrList().clear();
                adapterModelGawab.notifyDataSetChanged();
                viewModel.getUserDarMasr();
            }
        });
    }




    private void initRecyclerView() {
        binding.recyclerview.setLayoutManager(new LinearLayoutManager(context));
        adapterModelGawab = new AdapterModelGawab(context, viewModel.getDarMasrList(), this);
        binding.recyclerview.setAdapter(adapterModelGawab);
    }

    @Override
    public void initViewModel() {

        viewModel = new ViewModelProvider(this).get(DarMsrViewModel.class);
        setViewModel(viewModel);

    }

    @Override
    public void initObservers() {

        viewModel.getIsEditSuccessMLD().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                adapterModelGawab.notifyDataSetChanged();
            }
        });
        viewModel.getIsSuccesslMLD().observe(this, new Observer<modelGawab>() {
            @Override
            public void onChanged(modelGawab modelGawab) {
                saveDarMasr();
            }
        });

        viewModel.getGawabResultMLD().observe(this, new Observer<List<modelGawab>>() {
            @Override
            public void onChanged(List<modelGawab> modelGawabslist) {
                viewModel.getDarMasrList().clear();
                viewModel.getDarMasrList().addAll(modelGawabslist);
                adapterModelGawab.notifyDataSetChanged();
                binding.progressBar.setVisibility(View.GONE);
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
                viewModel.getUserDarMasr();
            }
        });

        viewModel.getIsDarMasrReady().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                adapterModelGawab.notifyDataSetChanged();
            }
        });
    }

    private void saveDarMasr() {

        binding.progressBar.setVisibility(View.GONE);
        dialog.dismiss();
        viewModel.getDarMasrList().clear();
        adapterModelGawab.notifyDataSetChanged();
        viewModel.getUserDarMasr();

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

    private void createEditeAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        View view = getLayoutInflater().inflate(R.layout.popupedit, null);

        editNumber = view.findViewById(R.id.answerEditNumber);
        editTittle = view.findViewById(R.id.answerEditTitle);
        editImportSide = view.findViewById(R.id.importEditSide);
        editexportSide = view.findViewById(R.id.exportEditSide);
        editSaveButton = view.findViewById(R.id.saveEditButton);
        editSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editPDFfileFireBase();
            }
        });
        builder.setView(view);
        dialog = builder.create();
        dialog.show();
    }

    private void editPDFfileFireBase() {

        String number = editNumber.getText().toString().trim();
        String tittle = editTittle.getText().toString().trim();
        String exportSide = editexportSide.getText().toString().trim();
        String importSide = editImportSide.getText().toString().trim();

        viewModel.updateGawab(number,tittle,exportSide,importSide);
        saveDarMasr();
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

        builder.setView(view);
        dialog = builder.create();//create dialog
        dialog.show();//show our dialog
    }

    private void selectPdf() {
        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "PDF FILE SELECT"), 130);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 130 && resultCode == RESULT_OK && data != null && data.getData() != null){
            saveButton.setEnabled(true);
            pdfGwab.setText(data.getDataString().substring(data.getDataString().lastIndexOf("/") + 1));

            saveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    binding.progressBar.setVisibility(View.VISIBLE);
                    uploadPDFfileFireBase(data.getData());
                }
            });
        }
    }

    private void uploadPDFfileFireBase(Uri data) {

        String tGwab = titleGwab.getText().toString().trim();
        String dGwab = dateGwab.getText().toString().trim();
        String nGwab = numberGwab.getText().toString().trim();
        String eGwab = exportGwab.getText().toString().trim();
        String iGwab = importGwab.getText().toString().trim();

        if (!TextUtils.isEmpty(tGwab) && !TextUtils.isEmpty(dGwab)
                && !TextUtils.isEmpty(nGwab) && data != null ){
            viewModel.saveDarMasr(tGwab, dGwab, nGwab, data, eGwab, iGwab);
        }else {
            Toast.makeText(getActivity(), "Empty Fields Not Allowed", Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onGawabEditClick(int adapterPosition, modelGawab gawab) {
        // TODO open dialog
        //TODO save new object to firebase
        createEditeAlertDialog();
    }

    @Override
    public void onGawabDeleteClick(int adapterPosition, modelGawab gawab) {
        createDeleteAlertDialog();
    }

    private void createDeleteAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View view = getLayoutInflater().inflate(R.layout.popupdelete, null);

        deleteNumber = view.findViewById(R.id.enter_number);
        deleteSaveButton = view.findViewById(R.id.deleteField);
        deleteSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteDocumentByNumberGawab();
            }
        });
        builder.setView(view);
        dialog = builder.create();
        dialog.show();
    }

    private void deleteDocumentByNumberGawab() {
        String nu = deleteNumber.getText().toString().trim();

        viewModel.deleteGawab(nu);
    }
}