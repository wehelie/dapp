package com.example.wehelie.dapp;

import android.os.Parcelable;
import android.os.Parcel;

public class MatchesObject implements Parcelable  {
    public boolean liked;
    public String name;
    public String uid;
    public String imageUrl;
    public String lat;
    public String longitude;

    public MatchesObject(Parcel in) {

        liked = in.readByte() != 0;
        name = in.readString();
        imageUrl = in.readString();
        lat = in.readString();
        longitude = in.readString();
    }

    public MatchesObject() {

    }



    public static final Creator<MatchesObject> CREATOR = new Creator<MatchesObject>() {
        @Override
        public MatchesObject createFromParcel(Parcel source) {
            return new MatchesObject(source);
        }

        @Override
        public MatchesObject[] newArray(int size) {
            return new MatchesObject[size];
        }
    };



    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(imageUrl);
        dest.writeByte((byte) (liked ? 1 : 0));
        dest.writeString(name);
        dest.writeString(lat);
        dest.writeString(longitude);
    }
}