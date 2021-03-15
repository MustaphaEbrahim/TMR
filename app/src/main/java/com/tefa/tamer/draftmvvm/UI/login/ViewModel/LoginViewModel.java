package com.tefa.tamer.draftmvvm.UI.login.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.tefa.tamer.draftmvvm.Repository.DataProviders.Base.OnDataProviderResponseListener;
import com.tefa.tamer.draftmvvm.UI.Base.BaseViewModel;
import com.tefa.tamer.draftmvvm.UI.Main.View.User;

public class LoginViewModel extends BaseViewModel {

    private MutableLiveData<Boolean> isLoadingMLD = new MutableLiveData<>();
    private MutableLiveData<com.tefa.tamer.draftmvvm.UI.Main.View.User> isSuccessMLD = new MutableLiveData<>();
    private MutableLiveData<String> isErrorMLD = new MutableLiveData<>();
    private MutableLiveData<com.tefa.tamer.draftmvvm.UI.Main.View.User> userAlreadyExistMLD = new MutableLiveData<>();

    public LoginViewModel(@NonNull Application application) {
        super(application);
    }

    public void loginEmailPasswordUser(String email , String password){
        isLoadingMLD.setValue(true);

        getUserDataProvider().loginEmailPasswordUser(email, password, new OnDataProviderResponseListener<com.tefa.tamer.draftmvvm.UI.Main.View.User>() {

            @Override
            public void onSuccess(com.tefa.tamer.draftmvvm.UI.Main.View.User response) {
                isLoadingMLD.setValue(false);
                isSuccessMLD.setValue(response);
            }

            @Override
            public void onError(String errorMsg) {
                isLoadingMLD.setValue(false);
                isErrorMLD.setValue(errorMsg);
            }
        });
    }
    public MutableLiveData<Boolean> getIsLoadingMLD() {
        return isLoadingMLD;
    }

    public MutableLiveData<com.tefa.tamer.draftmvvm.UI.Main.View.User> getIsSuccessMLD() { return isSuccessMLD;
    }

    public MutableLiveData<String> getIsErrorMLD() {
        return isErrorMLD;
    }

    public MutableLiveData<com.tefa.tamer.draftmvvm.UI.Main.View.User> getUserAlreadyExistMLD() { return userAlreadyExistMLD; }

    public void getUser() {

        getUserDataProvider().getUser(new OnDataProviderResponseListener<User>() {
            @Override
            public void onSuccess(com.tefa.tamer.draftmvvm.UI.Main.View.User response) {
                userAlreadyExistMLD.setValue(response);
            }

            @Override
            public void onError(String errorMsg) {

            }
        });


    }
}
