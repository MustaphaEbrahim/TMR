package com.tefa.tamer.draftmvvm.Repository.DataProviders.daarMasr;

import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
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

public class DarMasrDataProvider extends BaseDataProvider {

    public void getPostUserdarMasr(OnDataProviderResponseListener<User> userOnDataProviderResponseListener) {
        firebaseAuth.addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                user = firebaseAuth.getCurrentUser();
                if (user != null) {


                    userCollectionReference.document(user.getUid()).addSnapshotListener(new EventListener<DocumentSnapshot>() {
                        @Override
                        public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                            if (value != null) {
                                User user = value.toObject(User.class);

                                if (user != null) {
                                    userOnDataProviderResponseListener.onSuccess(user);
                                }
                            }
                        }
                    });
                }
            }
        });
    }

    //darmasr get list of darmasr
    public void getDarMasrList (User user, OnDataProviderResponseListener<List<modelGawab>> userOnDataProviderResponseListener){
        List<modelGawab> darMasrList = new ArrayList<>();

        darMasrCollectionReference.whereEqualTo("userId", user.getUserId()).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if (!queryDocumentSnapshots.isEmpty()){
                    for (QueryDocumentSnapshot darMasrii : queryDocumentSnapshots){
                        modelGawab modelGawab = darMasrii.toObject(modelGawab.class);
                        darMasrList.add(modelGawab);
                    }
                    userOnDataProviderResponseListener.onSuccess(darMasrList);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }

    //eskan egtmay get list of daarmasr
    public void saveDarMasr (String answerTitle, String answerDate, String answerNumber, Uri pdfUri, String importSide, String exportSide, User user, OnDataProviderResponseListener<modelGawab> booleanOnDataProviderResponseListener){

        final StorageReference filepath = storageReference
                .child("darmasr_pdf")
                .child("my_pdf_" + answerNumber + Timestamp.now().getNanoseconds());

        filepath.putFile(pdfUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                filepath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        String currentName = user.getUserName();
                        String currentUserId = user.getUserId();
                        String pdfUri = uri.toString();

                        //create a darmasr object - model
                        modelGawab darmasr = new modelGawab();
                        darmasr.setTitle(answerTitle);
                        darmasr.setDate(answerDate);
                        darmasr.setNumber(answerNumber);
                        darmasr.setImportSide(importSide);
                        darmasr.setExportSide(exportSide);
                        darmasr.setPdfUri(pdfUri);
                        darmasr.setTimeAdded(new Timestamp(new Date()));
                        darmasr.setUserName(currentName);
                        darmasr.setUserId(currentUserId);

                        darMasrCollectionReference.document(answerNumber).set(darmasr).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                booleanOnDataProviderResponseListener.onSuccess(darmasr);
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

            }
        });
    }

    public void searchGawab (String searchKey, OnDataProviderResponseListener<List<modelGawab>> listOnDataProviderResponseListener){
        darMasrCollectionReference.whereEqualTo("number", searchKey).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    listOnDataProviderResponseListener.onSuccess(task.getResult().toObjects(modelGawab.class));
                }else {
                    // TODO handle if no result
                }
            }
        });
    }

    public void updateGawab(String number, String tittle,String exportSide, String importSide, OnDataProviderResponseListener<Boolean> booleanOnDataProviderResponseListener) {
        darMasrCollectionReference.whereEqualTo("number", number).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                darMasrCollectionReference.document(number).update(
                        "importSide",importSide,
                        "exportSide", exportSide,
                        "title" ,tittle ).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        booleanOnDataProviderResponseListener.onSuccess(true);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        booleanOnDataProviderResponseListener.onError(e.getLocalizedMessage());
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

    public void  deleteGawab(String number, OnDataProviderResponseListener<Boolean> booleanOnDataProviderResponseListener){
        darMasrCollectionReference.whereEqualTo("number", number).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                darMasrCollectionReference.document(number).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        booleanOnDataProviderResponseListener.onSuccess(true);
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

}
