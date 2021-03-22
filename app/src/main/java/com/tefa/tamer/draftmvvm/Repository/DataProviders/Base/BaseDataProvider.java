package com.tefa.tamer.draftmvvm.Repository.DataProviders.Base;

import android.content.Context;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.tefa.tamer.draftmvvm.Application.App;
import com.tefa.tamer.draftmvvm.Repository.Database.Creation.DatabaseCreator;
import com.tefa.tamer.draftmvvm.Repository.Server.Consumer.WebServiceConsumer;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public abstract class BaseDataProvider {


    protected FirebaseUser currentUser;
    protected FirebaseUser user;


    protected FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    protected StorageReference storageReference = FirebaseStorage.getInstance().getReference();
    protected FirebaseFirestore db = FirebaseFirestore.getInstance();
    protected CollectionReference userCollectionReference = db.collection("Users");
    protected CollectionReference eskanegtmayCollectionReference = db.collection("EskanEgtamy");
    protected CollectionReference darMasrCollectionReference = db.collection("DaarMasr");
    protected Context context;
    protected Executor executor = Executors.newSingleThreadExecutor();

    public BaseDataProvider() {
        this.context = App.getAppContext();

    }
}
