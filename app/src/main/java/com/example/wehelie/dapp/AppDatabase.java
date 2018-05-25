package com.example.wehelie.dapp;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.arch.persistence.room.Room;


@Database(entities = {UserSetting.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase DB;
    public abstract UserSettingsDao settingsDao();

    public static AppDatabase getDB(Context context) {
        if (DB == null) {
            DB =
                    Room.databaseBuilder(context, AppDatabase.class, "userSetting-DB")
                            .allowMainThreadQueries()
                            .build();
        }
        return DB;
    }

    public static void destroyInstance() {
        DB = null;
    }
}