package com.tefa.tamer.draftmvvm.UI.Base;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.tefa.tamer.draftmvvm.Repository.DataProviders.UserDataProvider;
import com.tefa.tamer.draftmvvm.UI.Main.View.User;
import com.tefa.tamer.draftmvvm.Utilities.SharedPreferenceHelper;

import java.util.Locale;

public abstract class BaseViewModel extends AndroidViewModel {


    protected User user;
    private UserDataProvider userDataProvider = UserDataProvider.sharedInstance;

    public UserDataProvider getUserDataProvider() {
        return userDataProvider;
    }

    public MutableLiveData<Boolean> isConnectToInternetMutableLiveData = new MutableLiveData<>();
    Context context;

    public BaseViewModel(@NonNull Application application) {
        super(application);
    }


    public void setContext(Context context) {
        this.context = context;
    }



    public void setUser(User user) {
        this.user = user;
    }


    public MutableLiveData<Boolean> getIsConnectToInternetMutableLiveData() {
        return isConnectToInternetMutableLiveData;
    }

    public void setIsConnectToInternet(boolean status) {
        this.isConnectToInternetMutableLiveData.setValue(status);
    }




    public int getLanguageId() {
        String Language = SharedPreferenceHelper.getSharedPreferenceString(getApplication(), SharedPreferenceHelper.LANGUAGE_KEY, Locale.getDefault().getLanguage());
        if (Language.equalsIgnoreCase("ar")) {
            return 1;
        }
        if (Language.equalsIgnoreCase("en")) {
            return 2;
        }
        return 2;
    }


}
