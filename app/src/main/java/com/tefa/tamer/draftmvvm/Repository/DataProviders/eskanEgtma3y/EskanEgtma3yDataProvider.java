package com.tefa.tamer.draftmvvm.Repository.DataProviders.eskanEgtma3y;

import android.net.Uri;
import android.widget.Toast;

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

public class EskanEgtma3yDataProvider extends BaseDataProvider {



    public void getPostUserEskanegtamy(OnDataProviderResponseListener<User> userOnDataProviderResponseListener) {
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

    public void getEskanList(User user, OnDataProviderResponseListener<List<modelGawab>> userOnDataProviderResponseListener) {
        List<modelGawab> eskanEgtamies = new ArrayList<>();

        eskanegtmayCollectionReference.whereEqualTo("userId", user.getUserId()).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if (!queryDocumentSnapshots.isEmpty()) {
                    for (QueryDocumentSnapshot eskanEgtamii : queryDocumentSnapshots) {
                        modelGawab modelGawab = eskanEgtamii.toObject(modelGawab.class);
                        eskanEgtamies.add(modelGawab);
                    }
                    userOnDataProviderResponseListener.onSuccess(eskanEgtamies);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }

   /* public void saveEskanEgtmay(String answerTitle, String answerDate, String answerNumber, Uri pdfUri, String importSide, String exportSide, User user, OnDataProviderResponseListener<modelGawab> booleanOnDataProviderResponseListener) {

        final StorageReference filepath = storageReference
                .child("egtmay_pdf")
                .child("my_pdf_" + answerNumber + Timestamp.now().getSeconds());


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
                                //create a eskanegtmay Object - model
                                modelGawab modelGawab = new modelGawab();
                                modelGawab.setTitle(answerTitle);
                                modelGawab.setDate(answerDate);
                                modelGawab.setNumber(answerNumber);
                                modelGawab.setImportSide(importSide);
                                modelGawab.setExportSide(exportSide);
                                modelGawab.setPdfUri(pdfuri);
                                modelGawab.setTimeAdded(new Timestamp(new Date()));
                                modelGawab.setUserName(currentName);
                                modelGawab.setUserId(currentUserId);


                                //invoke our collectionReference
                                eskanegtmayCollectionReference.add(modelGawab)
                                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                            @Override
                                            public void onSuccess(DocumentReference documentReference) {

                                                booleanOnDataProviderResponseListener.onSuccess(modelGawab);

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

    }*/
    public void saveEskanEgtmay(String answerTitle, String answerDate, String answerNumber, Uri pdfUri, String importSide, String exportSide, User user, OnDataProviderResponseListener<modelGawab> booleanOnDataProviderResponseListener) {

        final StorageReference filepath = storageReference
                .child("egtmay_pdf")
                .child("my_pdf_" + answerNumber + Timestamp.now().getSeconds());


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
                                //create a eskanegtmay Object - model
                                modelGawab modelGawab = new modelGawab();
                                modelGawab.setTitle(answerTitle);
                                modelGawab.setDate(answerDate);
                                modelGawab.setNumber(answerNumber);
                                modelGawab.setImportSide(importSide);
                                modelGawab.setExportSide(exportSide);
                                modelGawab.setPdfUri(pdfuri);
                                modelGawab.setTimeAdded(new Timestamp(new Date()));
                                modelGawab.setUserName(currentName);
                                modelGawab.setUserId(currentUserId);


                                //invoke our collectionReference
                                eskanegtmayCollectionReference.document(answerNumber).set(modelGawab)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                booleanOnDataProviderResponseListener.onSuccess(modelGawab);
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

    public void searchGawab(String searchKey, OnDataProviderResponseListener<List<modelGawab>> listOnDataProviderResponseListener) {

        eskanegtmayCollectionReference.whereEqualTo("number", searchKey).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if (task.isSuccessful()) {
                    listOnDataProviderResponseListener.onSuccess(task.getResult().toObjects(modelGawab.class));
                } else {
                    Toast.makeText(context, "this number is not found ", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }

    public void updateGawabModel(String exportSidde, String number,String export,String title, OnDataProviderResponseListener<Boolean> booleanOnDataProviderResponseListener) {

        eskanegtmayCollectionReference.whereEqualTo("number" , number).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    eskanegtmayCollectionReference.document(number).update(
                            "importSide",export
                            , "exportSide", exportSidde,
                            "title" ,title).addOnSuccessListener(new OnSuccessListener<Void>() {
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
            }
        });
       /* eskanegtmayCollectionReference.whereEqualTo("number", gawab.getNumber()).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                eskanegtmayCollectionReference.document(queryDocumentSnapshots.getDocuments().get(0).getId()).update(
                        "importSide",export
                        , "exportSide", exportSidde,
                        "title" ,title).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        // TODO Refresh el list ele fl UI
                        booleanOnDataProviderResponseListener.onSuccess(true);

                    }
                });

            }
        });*/

    }


    /*public void updateGawabModel(modelGawab gawab , OnDataProviderResponseListener<Boolean> booleanOnDataProviderResponseListener){

        if (gawab.getNumber() != null){
        eskanegtmayCollectionReference.whereEqualTo("number", gawab.getNumber()).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                eskanegtmayCollectionReference.document(gawab.getNumber()).set(gawab).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        booleanOnDataProviderResponseListener.onSuccess(true);
                    }
                });
            }
        });
        }
    }*/


    public void delete (String number, OnDataProviderResponseListener<Boolean> booleanOnDataProviderResponseListener) {

        eskanegtmayCollectionReference.whereEqualTo("number" ,number).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                eskanegtmayCollectionReference.document(number).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        booleanOnDataProviderResponseListener.onSuccess(true);
                    }
                });
            }
        });

    }


}
