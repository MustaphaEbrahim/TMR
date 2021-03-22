package com.tefa.tamer.draftmvvm.UI.EskanEgtamy.ViewModel;

import android.app.Application;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.tefa.tamer.draftmvvm.Repository.DataProviders.Base.OnDataProviderResponseListener;
import com.tefa.tamer.draftmvvm.Repository.DataProviders.eskanEgtma3y.EskanEgtma3yDataProvider;
import com.tefa.tamer.draftmvvm.UI.Base.BaseViewModel;
import com.tefa.tamer.draftmvvm.UI.EskanEgtamy.View.modelGawab;
import com.tefa.tamer.draftmvvm.UI.Main.View.User;

import java.util.ArrayList;
import java.util.List;

public class EskanEgtmayVIewModel extends BaseViewModel {


    private  EskanEgtma3yDataProvider eskanEgtma3yDataProvider;


    private User currentUser;


    private MutableLiveData<Boolean> isloadingMLD = new MutableLiveData<>();
    private MutableLiveData<Boolean> isSuccessMLD = new MutableLiveData<>();
    private MutableLiveData<modelGawab> isSuccesslMLD = new MutableLiveData<>();
    private MutableLiveData<String> isErrorMLD = new MutableLiveData<>();
    private MutableLiveData<User> userAlreadyExistMLD = new MutableLiveData<>();
    private List<modelGawab> modelGawabList = new ArrayList<>();
    private MutableLiveData<Boolean> isEskanEgtmayReady = new MutableLiveData<>();
    private MutableLiveData<List<modelGawab>> gawabResultMLD = new MutableLiveData<>();



    public EskanEgtmayVIewModel(@NonNull Application application) {
        super(application);
        eskanEgtma3yDataProvider = new EskanEgtma3yDataProvider();
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
        eskanEgtma3yDataProvider.getPostUserEskanegtamy(new OnDataProviderResponseListener<User>() {
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

    public MutableLiveData<Boolean> getIsSuccessMLD() {return isSuccessMLD;}
    public MutableLiveData<String> getIsErrorMLD() { return isErrorMLD; }
    public MutableLiveData<User> getUserAlreadyExistMLD() {
        return userAlreadyExistMLD;
    }


    ///////////////////////////////////////////////////
    public void saveEskanEgtmay(String answerTittle , String answerDate, String answerNumber , Uri pdfUri, String importSide , String exportSide){
        isloadingMLD.setValue(true);

        if (currentUser != null){

            eskanEgtma3yDataProvider.saveEskanEgtmay(answerTittle ,answerDate ,answerNumber ,pdfUri, importSide ,exportSide  ,currentUser ,new OnDataProviderResponseListener<modelGawab>(){

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
    ///////////////////////////////////////////////////


    public void getUserEskan(){

        if (user != null) {


            eskanEgtma3yDataProvider.getEskanList(user, new OnDataProviderResponseListener<List<modelGawab>>() {
                @Override
                public void onSuccess(List<modelGawab> response) {
                   currentUser = user;
                    modelGawabList.clear();
                    modelGawabList.addAll(response);
                    isEskanEgtmayReady.setValue(true);

                }

                @Override
                public void onError(String errorMsg) {

                }
            });
        }
    }

    public MutableLiveData<modelGawab> getIsSuccesslMLD() {
        return isSuccesslMLD;
    }

    public MutableLiveData<Boolean> getIsloadingMLD() { return isloadingMLD; }
    public MutableLiveData<List<modelGawab>> getGawabResultMLD() {
        return gawabResultMLD;
    }
    public MutableLiveData<Boolean> getIsEskanEgtmayReady() {return isEskanEgtmayReady;}
    public List<modelGawab> getModelGawabList(){ return modelGawabList;}
    public void setModelGawabList(List<modelGawab> modelGawabList){this.modelGawabList = modelGawabList;}

    public void searchGawab(String searchKey) {


        eskanEgtma3yDataProvider.searchGawab(searchKey, new OnDataProviderResponseListener<List<modelGawab>>() {
            @Override
            public void onSuccess(List<modelGawab> response) {
                gawabResultMLD.setValue(response);
            }

            @Override
            public void onError(String errorMsg) {

            }
        });



    }




}
