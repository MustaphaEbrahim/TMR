package com.tefa.tamer.draftmvvm.UI.EskanGana.ViewModel;

import android.app.Application;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.tefa.tamer.draftmvvm.Repository.DataProviders.Base.OnDataProviderResponseListener;
import com.tefa.tamer.draftmvvm.Repository.DataProviders.eskanGana.EskanGanaDataProvider;
import com.tefa.tamer.draftmvvm.UI.Base.BaseViewModel;
import com.tefa.tamer.draftmvvm.UI.EskanEgtamy.View.modelGawab;
import com.tefa.tamer.draftmvvm.UI.Main.View.User;

import java.util.ArrayList;
import java.util.List;

public class EskanGanaViewModel extends BaseViewModel {

    private EskanGanaDataProvider eskanGanaDataProvider;

    private User currentUser;

    private MutableLiveData<Boolean> isloadingMLD = new MutableLiveData<>();
    private MutableLiveData<Boolean> isSuccessMLD = new MutableLiveData<>();
    private MutableLiveData<modelGawab> isSuccesslMLD = new MutableLiveData<>();
    private MutableLiveData<String> isErrorMLD = new MutableLiveData<>();
    private MutableLiveData<User> userAlreadyExistMLD = new MutableLiveData<>();
    private List<modelGawab> ganaGawabList = new ArrayList<>();
    private MutableLiveData<Boolean> isEskanGanaReady = new MutableLiveData<>();
    private MutableLiveData<List<modelGawab>> gawabResultMLD = new MutableLiveData<>();


    public EskanGanaViewModel(@NonNull Application application) {
        super(application);
        eskanGanaDataProvider = new EskanGanaDataProvider();
    }


    public MutableLiveData<Boolean> getIsloadingMLD() { return isloadingMLD; }
    public MutableLiveData<Boolean> getIsSuccessMLD() { return isSuccessMLD; }
    public MutableLiveData<modelGawab> getIsSuccesslMLD() { return isSuccesslMLD; }
    public MutableLiveData<String> getIsErrorMLD() { return isErrorMLD; }
    public MutableLiveData<User> getUserAlreadyExistMLD() { return userAlreadyExistMLD; }
    public List<modelGawab> getGanaGawabList() { return ganaGawabList; }
    public MutableLiveData<Boolean> getIsEskanGanaReady() { return isEskanGanaReady; }
    public MutableLiveData<List<modelGawab>> getGawabResultMLD() { return gawabResultMLD; }

    public void getUser(){
        eskanGanaDataProvider.getPostUserGana(new OnDataProviderResponseListener<User>() {
            @Override
            public void onSuccess(User response) {
                currentUser = response;
                userAlreadyExistMLD.setValue(response);
            }

            @Override
            public void onError(String errorMsg) {
                isErrorMLD.setValue(errorMsg);
            }
        });
    }

    public void saveEskanGana(String answerTittle , String answerDate, String answerNumber , Uri pdfUri, String importSide , String exportSide ){
        isloadingMLD.setValue(true);

        if (currentUser != null){

            eskanGanaDataProvider.saveGana(answerTittle, answerDate, answerNumber, pdfUri, importSide, exportSide, currentUser, new OnDataProviderResponseListener<modelGawab>(){
                @Override
                public void onSuccess(modelGawab response) {
                    isloadingMLD.setValue(false);
                    isSuccesslMLD.setValue(response);
                }

                @Override
                public void onError(String errorMsg) {
                    isloadingMLD.setValue(false);
                    isErrorMLD.setValue(errorMsg);
                }
            });
        }
    }

    public void getUserGana(){
        if (user != null){
            eskanGanaDataProvider.getGanaList(user, new OnDataProviderResponseListener<List<modelGawab>>() {
                @Override
                public void onSuccess(List<modelGawab> response) {
                    currentUser = user;
                    ganaGawabList.clear();
                    ganaGawabList.addAll(response);
                    isEskanGanaReady.setValue(true);
                }

                @Override
                public void onError(String errorMsg) {
                    isErrorMLD.setValue(errorMsg);
                }
            });
        }
    }

    public void searchGawab (String searchKey){
        eskanGanaDataProvider.searchGawab(searchKey, new OnDataProviderResponseListener<List<modelGawab>>() {
            @Override
            public void onSuccess(List<modelGawab> response) {
                gawabResultMLD.setValue(response);
            }

            @Override
            public void onError(String errorMsg) {
                isErrorMLD.setValue(errorMsg);
            }
        });
    }


}
