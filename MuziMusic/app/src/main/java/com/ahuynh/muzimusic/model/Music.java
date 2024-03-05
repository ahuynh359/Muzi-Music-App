package com.ahuynh.muzimusic.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Music implements Parcelable {

    private String artist;
    private String title;
    private String displayName;
    private String album;
    private String relativePath;
    private String absolutePath;
    private String albumArt;
    private int year;
    private int track;
    private int startFrom;
    private long dateAdded;
    private long id;
    private long duration;
    private long albumId;
    protected Music(Parcel in) {
    }

    public static final Creator<Music> CREATOR = new Creator<Music>() {
        @Override
        public Music createFromParcel(Parcel in) {
            return new Music(in);
        }

        @Override
        public Music[] newArray(int size) {
            return new Music[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
    }
}
