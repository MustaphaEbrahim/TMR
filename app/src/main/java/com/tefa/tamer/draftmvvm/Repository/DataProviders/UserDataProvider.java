package com.tefa.tamer.draftmvvm.Repository.DataProviders;


import android.app.DownloadManager;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.tefa.tamer.draftmvvm.Repository.DataProviders.Base.BaseDataProvider;
import com.tefa.tamer.draftmvvm.Repository.DataProviders.Base.OnDataProviderResponseListener;
import com.tefa.tamer.draftmvvm.UI.EskanEgtamy.View.EskanEgtamy;
import com.tefa.tamer.draftmvvm.UI.Main.View.User;

import java.net.URI;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserDataProvider extends BaseDataProvider {
    public static UserDataProvider sharedInstance = new UserDataProvider();

    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseUser currentUser;
    private FirebaseUser user;

    //fireStore connection
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private StorageReference storageReference = FirebaseStorage.getInstance().getReference();
    private CollectionReference userCollectionReference = db.collection("Users");
    private CollectionReference eskanegtmayCollectionReference = db.collection("EskanEgtamy");
    private StorageReference pdfStorage = FirebaseStorage.getInstance().getReference("egtmay_pdf");




    public void loginEmailPasswordUser(String email, String password, OnDataProviderResponseListener<com.tefa.tamer.draftmvvm.UI.Main.View.User> booleanOnDataProviderResponseListener){
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                FirebaseUser currentUser = authResult.getUser();
                assert currentUser != null;
                String currentUserId = currentUser.getUid();

                userCollectionReference.document(currentUserId).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        User user =  documentSnapshot.toObject(User.class);
                        if (user != null){
                            booleanOnDataProviderResponseListener.onSuccess(user);
                        }
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

    public void createUserEmailAccount (String email, String password, String UserName, OnDataProviderResponseListener<User> booleanOnDataProviderResponseListener ){

        firebaseAuth.createUserWithEmailAndPassword(email , password ).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {

                currentUser = authResult.getUser();
                assert currentUser != null;
                String currentUserId = currentUser.getUid();

                User user = new User();
                user.setUserName(UserName);
                user.setUserId(currentUserId);

                userCollectionReference.document(user.getUserId()).set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        booleanOnDataProviderResponseListener.onSuccess(user);

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

    public void signOut(OnDataProviderResponseListener<Boolean> booleanOnDataProviderResponseListener){
        firebaseAuth.addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    firebaseAuth.signOut();
                    booleanOnDataProviderResponseListener.onSuccess(true);
                }
            }
        });
    }

    public void  retrievePDF (User user , Uri pdfUri, OnDataProviderResponseListener<List<EskanEgtamy>> pDFOnDataProviderResponseListener){
    }

    public void getEskanList(User user, OnDataProviderResponseListener<List<EskanEgtamy>> userOnDataProviderResponseListener){
        List<EskanEgtamy> eskanEgtamies = new ArrayList<>();

        eskanegtmayCollectionReference.whereEqualTo("userId", user.getUserId()).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if (!queryDocumentSnapshots.isEmpty()){
                    for (QueryDocumentSnapshot eskanEgtamii : queryDocumentSnapshots){
                        EskanEgtamy eskanEgtamy = eskanEgtamii.toObject(EskanEgtamy.class);
                        eskanEgtamies.add(eskanEgtamy);
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


    public void saveEskanEgtmay (String answerTitle , String answerDate , String answerNumber, Uri pdfUri, String importSide , String exportSide , User user , OnDataProviderResponseListener<EskanEgtamy> booleanOnDataProviderResponseListener){

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
                        EskanEgtamy eskanEgtamy = new EskanEgtamy();
                        eskanEgtamy.setTitle(answerTitle);
                        eskanEgtamy.setDate(answerDate);
                        eskanEgtamy.setNumber(answerNumber);
                        eskanEgtamy.setImportSide(importSide);
                        eskanEgtamy.setExportSide(exportSide);
                        eskanEgtamy.setPdfUri(pdfuri);
                        eskanEgtamy.setTimeAdded(new Timestamp(new Date()));
                        eskanEgtamy.setUserName(currentName);
                        eskanEgtamy.setUserId(currentUserId);

                        //invoke our collectionReference
                        eskanegtmayCollectionReference.add(eskanEgtamy)
                                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {

                                booleanOnDataProviderResponseListener.onSuccess(eskanEgtamy);

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

    public void getPostUserEskanegtamy (OnDataProviderResponseListener<User> userOnDataProviderResponseListener){
        firebaseAuth.addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                user = firebaseAuth.getCurrentUser();
                if (user != null) {



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

    public void getUser(OnDataProviderResponseListener<User> userOnDataProviderResponseListener) {

        firebaseAuth = FirebaseAuth.getInstance();

        currentUser = firebaseAuth.getCurrentUser();

        if (currentUser != null) {//user is already loggin...

            User user = new User();
            user.setUserId(currentUser.getUid());
            user.setUserName(currentUser.getDisplayName());

            userOnDataProviderResponseListener.onSuccess(user);


        }


    }
}

