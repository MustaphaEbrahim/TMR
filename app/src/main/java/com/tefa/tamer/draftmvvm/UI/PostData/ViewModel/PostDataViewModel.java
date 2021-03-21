package com.tefa.tamer.draftmvvm.UI.PostData.ViewModel;

import android.app.Application;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.tefa.tamer.draftmvvm.Repository.DataProviders.Base.OnDataProviderResponseListener;
import com.tefa.tamer.draftmvvm.UI.Base.BaseViewModel;
import com.tefa.tamer.draftmvvm.UI.EskanEgtamy.View.modelGawab;
import com.tefa.tamer.draftmvvm.UI.Main.View.User;

public class PostDataViewModel extends BaseViewModel {

    private MutableLiveData<Boolean> isloadingMLD = new MutableLiveData<>();
    private MutableLiveData<modelGawab> isSuccessMLD = new MutableLiveData<>();
    private MutableLiveData<String> isErrorMLD = new MutableLiveData<>();
    private MutableLiveData<User> userAlreadyExistMLD = new MutableLiveData<>();
    private User currentUser;


    public PostDataViewModel(@NonNull Application application) {
        super(application);
    }

    public void saveEskanEgtmay(String answerTittle , String answerDate, String answerNumber , Uri pdfUri, String importSide , String exportSide){
        isloadingMLD.setValue(true);

        if (currentUser != null){
            getUserDataProvider().saveEskanEgtmay(answerTittle ,answerDate ,answerNumber ,pdfUri, importSide ,exportSide  ,currentUser ,new OnDataProviderResponseListener<modelGawab>(){

                @Override
                public void onSuccess(modelGawab response) {
                    isloadingMLD.setValue(false);
                    isSuccessMLD.setValue(response);
                }

                @Override
                public void onError(String errorMsg) {
                    isloadingMLD.setValue(false);
                    isErrorMLD.setValue(errorMsg);
                }
            });
        }
    }



    public MutableLiveData<Boolean> getIsloadingMLD(){ return isloadingMLD; }

    public MutableLiveData<modelGawab> getIsSuccessMLD() { return isSuccessMLD; }

    public MutableLiveData<String> getIsErrorMLD() {return isErrorMLD; }

    public MutableLiveData<User> getUserAlreadyExistMLD() {
        return userAlreadyExistMLD;
    }

    public void getUserEskan() {
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
}
