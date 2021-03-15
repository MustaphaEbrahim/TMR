package com.tefa.tamer.draftmvvm.Repository.Database.Creation;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.tefa.tamer.draftmvvm.Repository.Database.Models.User.User;
import com.tefa.tamer.draftmvvm.Repository.Database.Models.User.UserDao;


@Database(entities = {User.class}, version = 1, exportSchema = false)


public abstract class DatabaseCreator extends RoomDatabase {


    public static final String DATABASE_NAME = "dist_track.db";
    private static final Object LOCK = new Object();
    private static volatile DatabaseCreator instance;

    public static DatabaseCreator getInstance(Context context) {
        if (instance == null) {
            synchronized (LOCK) {
                if (instance == null) {

                    instance = Room.databaseBuilder(context.getApplicationContext(), DatabaseCreator.class, DATABASE_NAME)
                            .fallbackToDestructiveMigration()
                            .allowMainThreadQueries() // need to be changed
                            .build();

                }
            }
        }


        return instance;
    }

    public abstract UserDao userDao();

}

