package com.tefa.tamer.draftmvvm.Repository.Database.Models.BaseDao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Update;

import java.util.List;

/**
 * Created by Youssif Hamdy on 3/4/2020.
 */
@Dao
public interface BaseDao<T> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(T object);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<T> object);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void update(T object);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void update(List<T> object);

    @Delete
    void delete(T object);

    @Delete
    void delete(List<T> object);

}
