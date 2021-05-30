package com.tefa.tamer.draftmvvm.Repository.DataProviders.eskanGana;

import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.tefa.tamer.draftmvvm.Repository.DataProviders.Base.BaseDataProvider;
import com.tefa.tamer.draftmvvm.Repository.DataProviders.Base.OnDataProviderResponseListener;
import com.tefa.tamer.draftmvvm.UI.EskanEgtamy.View.modelGawab;
import com.tefa.tamer.draftmvvm.UI.Main.View.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EskanGanaDataProvider extends BaseDataProvider {

    public void getPostUserGana(OnDataProviderResponseListener<User> userOnDataProviderResponseListener){
        firebaseAuth.addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                user = firebaseAuth.getCurrentUser();

                if (user != null){
                    userCollectionReference.document(user.getUid()).addSnapshotListener(new EventListener<DocumentSnapshot>() {
                        @Override
                        public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                            if (value != null){
                                User user = value.toObject(User.class);

                                if (user != null){
                                    userOnDataProviderResponseListener.onSuccess(user);
                                }
                            }
                        }
                    });
                }
            }
        });
    }

    public void getGanaList(User user, OnDataProviderResponseListener<List<modelGawab>> userOnDataProviderResponseListener){

        List<modelGawab> eskanGanaList = new ArrayList<>();

        eskanGanaCollectionReference.whereEqualTo("userId", user.getUserId()).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if (!queryDocumentSnapshots.isEmpty()){
                    for (QueryDocumentSnapshot ganaLst : queryDocumentSnapshots){
                        modelGawab eskanGana = ganaLst.toObject(modelGawab.class);
                        eskanGanaList.add(eskanGana);
                    }
                    userOnDataProviderResponseListener.onSuccess(eskanGanaList);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });

    }

    public void saveGana(String answerTitle, String answerDate, String answerNumber, Uri pdfUri,
                         String importSide, String exportSide, User user,
                         OnDataProviderResponseListener<modelGawab> booleanOnDataProviderResponseListener){

        final StorageReference filepath = storageReference
                .child("gana_pdf")
                .child("my_pdf" + answerNumber + Timestamp.now().getNanoseconds());

        filepath.putFile(pdfUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        filepath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {

                                String currentName = user.getUserName();
                                String currentUserId = user.getUserId();
                                String pdfuri = uri.toString();
                                //create a gana Object - model
                                modelGawab modelGana = new modelGawab();
                                modelGana.setTitle(answerTitle);
                                modelGana.setDate(answerDate);
                                modelGana.setNumber(answerNumber);
                                modelGana.setImportSide(importSide);
                                modelGana.setExportSide(exportSide);
                                modelGana.setPdfUri(pdfuri);
                                modelGana.setTimeAdded(new Timestamp(new Date()));
                                modelGana.setUserName(currentName);
                                modelGana.setUserId(currentUserId);

                                eskanGanaCollectionReference.add(modelGana).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                            @Override
                                            public void onSuccess(DocumentReference documentReference) {
                                                booleanOnDataProviderResponseListener.onSuccess(modelGana);
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        booleanOnDataProviderResponseListener.onError(e.getLocalizedMessage());
                                    }
                                });
                            }
                        });
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                booleanOnDataProviderResponseListener.onError(e.getLocalizedMessage());
            }
        });
    }

    public void searchGawab(String searchKey, OnDataProviderResponseListener<List<modelGawab>> listOnDataProviderResponseListener){
        eskanGanaCollectionReference.whereEqualTo("number", searchKey).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    listOnDataProviderResponseListener.onSuccess(task.getResult().toObjects(modelGawab.class));
                }else {
                    //todo handle if no result
                }
            }
        });
    }
}
