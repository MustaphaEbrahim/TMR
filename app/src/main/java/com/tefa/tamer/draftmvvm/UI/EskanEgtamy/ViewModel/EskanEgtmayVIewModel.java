package com.tefa.tamer.draftmvvm.UI.EskanEgtamy.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.tefa.tamer.draftmvvm.Repository.DataProviders.Base.OnDataProviderResponseListener;
import com.tefa.tamer.draftmvvm.UI.Base.BaseViewModel;
import com.tefa.tamer.draftmvvm.UI.EskanEgtamy.View.EskanEgtamy;
import com.tefa.tamer.draftmvvm.UI.Main.View.User;

import java.util.ArrayList;
import java.util.List;

public class EskanEgtmayVIewModel extends BaseViewModel {

    private MutableLiveData<Boolean> isSuccessMLD = new MutableLiveData<>();
    private MutableLiveData<String> isErrorMLD = new MutableLiveData<>();
    private MutableLiveData<User> userAlreadyExistMLD = new MutableLiveData<>();
    private List<EskanEgtamy> eskanEgtamyList = new ArrayList<>();
    private MutableLiveData<Boolean> isEskanEgtmayReady = new MutableLiveData<>();

    public EskanEgtmayVIewModel(@NonNull Application application) {
        super(application);
    }

    public void signOut(){
        getUserDataProvider().signOut(new OnDataProviderResponseListener<Boolean>() {
            @Override
            public void onSuccess(Boolean response) {
                isSuccessMLD.setValue(response);
            }

            @Override
            public void onError(String errorMsg) {
                isErrorMLD.setValue(errorMsg);
            }
        });
    }

    public void getUser(){
        getUserDataProvider().getUser(new OnDataProviderResponseListener<User>() {
            @Override
            public void onSuccess(User response) {
                userAlreadyExistMLD.setValue(response);
            }

            @Override
            public void onError(String errorMsg) {

            }
        });
    }

    public MutableLiveData<Boolean> getIsSuccessMLD() {return isSuccessMLD;}
    public MutableLiveData<String> getIsErrorMLD() { return isErrorMLD; }
    public MutableLiveData<User> getUserAlreadyExistMLD() {
        return userAlreadyExistMLD;
    }


    public void getUserEskan(User user){


        getUserDataProvider().getEskanList(user, new OnDataProviderResponseListener<List<EskanEgtamy>>() {
            @Override
            public void onSuccess(List<EskanEgtamy> response) {
                eskanEgtamyList.clear();
                eskanEgtamyList.addAll(response);
                isEskanEgtmayReady.setValue(true);

            }

            @Override
            public void onError(String errorMsg) {

            }
        });
    }
    public MutableLiveData<Boolean> getIsEskanEgtmayReady() {return isEskanEgtmayReady;}
    public List<EskanEgtamy> getEskanEgtamyList(){ return eskanEgtamyList;}
    public void setEskanEgtamyList(List<EskanEgtamy> eskanEgtamyList){this.eskanEgtamyList = eskanEgtamyList;}

}
