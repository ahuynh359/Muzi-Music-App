package com.ahuynh.muzimusic.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "playlist")
public class Playlist implements Parcelable {
    @PrimaryKey
    private long playlistId;
    private String name;
    private int numOfSongs;



    protected Playlist(Parcel in) {
        playlistId = in.readLong();
        name = in.readString();
        numOfSongs = in.readInt();
    }


    public static final Creator<Playlist> CREATOR = new Creator<Playlist>() {
        @Override
        public Playlist createFromParcel(Parcel in) {
            return new Playlist(in);
        }

        @Override
        public Playlist[] newArray(int size) {
            return new Playlist[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeLong(playlistId);
        dest.writeString(name);
        dest.writeInt(numOfSongs);
        dest.writeInt(playingIndex);
    }



}
