package com.tefa.tamer.draftmvvm.Repository.Database.Models.User;

import androidx.room.Dao;
import androidx.room.Query;

import com.tefa.tamer.draftmvvm.Repository.Database.Models.BaseDao.BaseDao;


@Dao
public interface UserDao extends BaseDao<User> {

    @Query("SELECT * FROM User")
    com.tefa.tamer.draftmvvm.UI.Main.View.User getUser();

    @Query("DELETE FROM User")
    void deleteAll();


}
