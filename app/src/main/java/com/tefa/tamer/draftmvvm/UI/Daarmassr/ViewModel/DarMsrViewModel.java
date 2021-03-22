package com.tefa.tamer.draftmvvm.UI.Daarmassr.ViewModel;

import android.app.Application;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.tefa.tamer.draftmvvm.Repository.DataProviders.Base.OnDataProviderResponseListener;
import com.tefa.tamer.draftmvvm.Repository.DataProviders.daarMasr.DarMasrDataProvider;
import com.tefa.tamer.draftmvvm.Repository.DataProviders.eskanEgtma3y.EskanEgtma3yDataProvider;
import com.tefa.tamer.draftmvvm.UI.Base.BaseViewModel;
import com.tefa.tamer.draftmvvm.UI.EskanEgtamy.View.modelGawab;
import com.tefa.tamer.draftmvvm.UI.Main.View.User;

import java.util.ArrayList;
import java.util.List;

public class DarMsrViewModel extends BaseViewModel {


    private DarMasrDataProvider darMasrDataProvider;

    private User currentUser;



    private MutableLiveData<Boolean> isloadingMLD = new MutableLiveData<>();
    private MutableLiveData<Boolean> isSuccessMLD = new MutableLiveData<>();
    private MutableLiveData<modelGawab> isSuccesslMLD = new MutableLiveData<>();
    private MutableLiveData<String> isErrorMLD = new MutableLiveData<>();
    private MutableLiveData<User> userAlreadyExistMLD = new MutableLiveData<>();
    private List<modelGawab> darmasrList = new ArrayList<>();
    private MutableLiveData<Boolean> isDarMasrReady = new MutableLiveData<>();
    private MutableLiveData<List<modelGawab>> gawabResultMLD = new MutableLiveData<>();


    public DarMsrViewModel(@NonNull Application application) {
        // da , enta kont 7atet hena parameters
        //aywa dah howa ale a3mlo kda w a3mlo final 3shan a3rf astkhdm el dataprovider, meen ele alak xD

        super(application);
        darMasrDataProvider = new DarMasrDataProvider();
    }


    public void getUser(){
        darMasrDataProvider.getPostUserdarMasr(new OnDataProviderResponseListener<User>() {
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

    public void saveDarMasr(String answerTitle, String answerDate , String answerNumber, Uri pdfUri, String importSide, String exportSide){
        isloadingMLD.setValue(true);

        if (currentUser != null){
            darMasrDataProvider.saveDarMasr(answerTitle, answerDate, answerNumber, pdfUri, importSide, exportSide, currentUser, new OnDataProviderResponseListener<modelGawab>() {
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

    public void getUserDarMasr(){
        if (user != null){
            darMasrDataProvider.getDarMasrList(user, new OnDataProviderResponseListener<List<modelGawab>>() {
                @Override
                public void onSuccess(List<modelGawab> response) {
                    currentUser = user;
                    darmasrList.clear();
                    darmasrList.addAll(response);
                    isDarMasrReady.setValue(true);
                }

                @Override
                public void onError(String errorMsg) {

                }
            });
        }
    }

    public MutableLiveData<Boolean> getIsloadingMLD() { return isloadingMLD; }
    public MutableLiveData<Boolean> getIsSuccessMLD() { return isSuccessMLD; }
    public MutableLiveData<modelGawab> getIsSuccesslMLD() { return isSuccesslMLD; }
    public MutableLiveData<String> getIsErrorMLD() { return isErrorMLD; }
    public MutableLiveData<User> getUserAlreadyExistMLD() { return userAlreadyExistMLD; }
    public List<modelGawab> getDarMasrList() { return darmasrList; }
    public MutableLiveData<Boolean> getIsDarMasrReady() { return isDarMasrReady; }
    public MutableLiveData<List<modelGawab>> getGawabResultMLD() { return gawabResultMLD; }

    public void searchGawab(String searchKey) {


        darMasrDataProvider.searchGawab(searchKey, new OnDataProviderResponseListener<List<modelGawab>>() {
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
