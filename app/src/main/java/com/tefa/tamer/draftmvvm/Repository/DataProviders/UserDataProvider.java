package com.tefa.tamer.draftmvvm.Repository.DataProviders;


import android.net.Uri;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
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
import com.tefa.tamer.draftmvvm.UI.EskanEgtamy.View.modelGawab;
import com.tefa.tamer.draftmvvm.UI.Main.View.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserDataProvider extends BaseDataProvider {
    public static UserDataProvider sharedInstance = new UserDataProvider();


    private FirebaseAuth.AuthStateListener authStateListener;


    //fireStore connection

    private CollectionReference darMasrCollectionReference = db.collection("DaarMasr");



    public void loginEmailPasswordUser(String email, String password, OnDataProviderResponseListener<com.tefa.tamer.draftmvvm.UI.Main.View.User> booleanOnDataProviderResponseListener) {
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                FirebaseUser currentUser = authResult.getUser();
                assert currentUser != null;
                String currentUserId = currentUser.getUid();

                userCollectionReference.document(currentUserId).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        User user = documentSnapshot.toObject(User.class);
                        if (user != null) {
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

    public void createUserEmailAccount(String email, String password, String UserName, OnDataProviderResponseListener<User> booleanOnDataProviderResponseListener) {

        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
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

    public void signOut(OnDataProviderResponseListener<Boolean> booleanOnDataProviderResponseListener) {
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
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }

    //eskan egtmay get list of eskan egtmay

    public void saveDarMasr (String answerTitle, String answerDate, String answerNumber, Uri pdfUri, String importSide, String exportSide, User user, OnDataProviderResponseListener<modelGawab> booleanOnDataProviderResponseListener){
        final StorageReference filepath = storageReference
                .child("darmasr_pdf")
                .child("my_pdf_" + answerNumber + Timestamp.now().getNanoseconds());

        filepath.putFile(pdfUri);
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

