package com.example.wehelie.dapp;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import android.provider.ContactsContract;

import java.util.List;

@Dao
public interface UserSettingsDao {
    @Query("SELECT * FROM usersetting")
    List<UserSetting> getAll();

    @Query("SELECT * FROM usersetting WHERE email IN (:userEmails)")
    List<UserSetting> loadAllByIds(String[] userEmails);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(UserSetting... userSettings);
}
