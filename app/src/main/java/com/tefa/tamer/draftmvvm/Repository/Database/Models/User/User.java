package com.tefa.tamer.draftmvvm.Repository.Database.Models.User;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "User")
public class User {

    @PrimaryKey
    @NonNull
    String userName;


    @NonNull
    public String getUserName() {
        return userName;
    }

    public void setUserName(@NonNull String userName) {
        this.userName = userName;
    }
}
