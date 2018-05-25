package com.example.wehelie.dapp;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity
public class UserSetting {
    @PrimaryKey
    @NonNull
    private String email = "";

    @ColumnInfo(name = "reminder")
    private String reminder;

    @ColumnInfo(name = "account_status")
    private String privacy;

    @ColumnInfo(name = "gender")
    private String gender;

    @ColumnInfo(name = "minage")
    private String minAge;

    @ColumnInfo(name = "maxage")
    private String maxAge;

    @ColumnInfo(name = "min_distance")
    private String minDistance;

    @ColumnInfo(name = "max_distance")
    private String maxDistance;

    @NonNull
    public String getEmail() {
        return email;
    }

    public void setEmail(@NonNull String email) {
        this.email = email;
    }

    public String getReminder() {
        return reminder;
    }

    public void setReminder(String reminder) {
        this.reminder = reminder;
    }

    public String getPrivacy() {
        return privacy;
    }

    public void setPrivacy(String privacy) {
        this.privacy = privacy;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getMinAge() {
        return minAge;
    }

    public void setMinAge(String minAge) {
        this.minAge = minAge;
    }

    public String getMaxAge() {
        return maxAge;
    }

    public void setMaxAge(String maxAge) {
        this.maxAge = maxAge;
    }

    public String getMinDistance() {
        return minDistance;
    }

    public void setMinDistance(String minDistance) {
        this.minDistance = minDistance;
    }

    public String getMaxDistance() {
        return maxDistance;
    }

    public void setMaxDistance(String maxDistance) {
        this.maxDistance = maxDistance;
    }
}