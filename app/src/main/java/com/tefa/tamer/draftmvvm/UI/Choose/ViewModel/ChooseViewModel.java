package com.tefa.tamer.draftmvvm.UI.Choose.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.tefa.tamer.draftmvvm.Repository.DataProviders.Base.OnDataProviderResponseListener;
import com.tefa.tamer.draftmvvm.UI.Base.BaseViewModel;
import com.tefa.tamer.draftmvvm.UI.Main.View.User;

import java.util.ArrayList;
import java.util.List;

public class ChooseViewModel extends BaseViewModel {

    private MutableLiveData<Boolean> isSuccessMLD = new MutableLiveData<>();
    private MutableLiveData<String> isErrorMLD = new MutableLiveData<>();
    private MutableLiveData<User> userAlreadyExistMLD = new MutableLiveData<>();
    private MutableLiveData<Boolean> isJournalsReady = new MutableLiveData<>();

    public ChooseViewModel(@NonNull Application application) {
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

    public void getUser() {

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
    public MutableLiveData<Boolean> getIsSuccessMLD() {
        return isSuccessMLD;
    }
    public MutableLiveData<String> getIsErrorMLD() { return isErrorMLD; }
    public MutableLiveData<User> getUserAlreadyExistMLD() {
        return userAlreadyExistMLD;
    }


}
