package com.tefa.tamer.draftmvvm.Repository.DataProviders.Base;


import com.tefa.tamer.draftmvvm.Repository.Server.ResponseBody.Base.Result;

public interface OnDataProviderResponseListener<T> {

    void onSuccess(T response);

    void onError(String errorMsg);
}
