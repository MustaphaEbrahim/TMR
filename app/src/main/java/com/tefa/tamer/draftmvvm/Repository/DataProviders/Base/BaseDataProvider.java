package com.tefa.tamer.draftmvvm.Repository.DataProviders.Base;

import android.content.Context;

import com.tefa.tamer.draftmvvm.Application.App;
import com.tefa.tamer.draftmvvm.Repository.Database.Creation.DatabaseCreator;
import com.tefa.tamer.draftmvvm.Repository.Server.Consumer.WebServiceConsumer;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public abstract class BaseDataProvider {

    protected Context context;
    protected Executor executor = Executors.newSingleThreadExecutor();

    public BaseDataProvider() {
        this.context = App.getAppContext();

    }
}
