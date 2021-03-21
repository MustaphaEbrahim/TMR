package com.tefa.tamer.draftmvvm.UI.Daarmassr.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.tefa.tamer.draftmvvm.Repository.DataProviders.Base.OnDataProviderResponseListener;
import com.tefa.tamer.draftmvvm.UI.Base.BaseViewModel;
import com.tefa.tamer.draftmvvm.UI.EskanEgtamy.View.modelGawab;
import com.tefa.tamer.draftmvvm.UI.Main.View.User;

import java.util.ArrayList;
import java.util.List;

public class DarMsrViewModel extends BaseViewModel {


    private User currentUser;



    private MutableLiveData<Boolean> isloadingMLD = new MutableLiveData<>();
    private MutableLiveData<Boolean> isSuccessMLD = new MutableLiveData<>();
    private MutableLiveData<modelGawab> isSuccesslMLD = new MutableLiveData<>();
    private MutableLiveData<String> isErrorMLD = new MutableLiveData<>();
    private MutableLiveData<User> userAlreadyExistMLD = new MutableLiveData<>();
    private List<modelGawab> modelGawabList = new ArrayList<>();
    private MutableLiveData<Boolean> isEskanEgtmayReady = new MutableLiveData<>();
    private MutableLiveData<List<modelGawab>> gawabResultMLD = new MutableLiveData<>();


    public DarMsrViewModel(@NonNull Application application) {
        super(application);
    }


    public void getUser(){
        getUserDataProvider().getPostUserEskanegtamy(new OnDataProviderResponseListener<User>() {
            @Override
            public void onSuccess(User user) {

                currentUser = user;
                userAlreadyExistMLD.setValue(user);
            }

            @Override
            public void onError(String errorMsg) {

            }
        });
    }

    

    public MutableLiveData<Boolean> getIsloadingMLD() { return isloadingMLD; }

    public MutableLiveData<Boolean> getIsSuccessMLD() { return isSuccessMLD; }

    public MutableLiveData<modelGawab> getIsSuccesslMLD() { return isSuccesslMLD; }

    public MutableLiveData<String> getIsErrorMLD() { return isErrorMLD; }

    public MutableLiveData<User> getUserAlreadyExistMLD() { return userAlreadyExistMLD; }

    public List<modelGawab> getModelGawabList() { return modelGawabList; }

    public MutableLiveData<Boolean> getIsEskanEgtmayReady() { return isEskanEgtmayReady; }

    public MutableLiveData<List<modelGawab>> getGawabResultMLD() { return gawabResultMLD; }
}
