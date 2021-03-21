package com.tefa.tamer.draftmvvm.Repository.DataProviders.Base;

import com.google.firebase.auth.FirebaseAuth;

public class DarMasrDataProvider extends BaseDataProvider {

    public static DarMasrDataProvider sharedInstance = new DarMasrDataProvider();

    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

}
