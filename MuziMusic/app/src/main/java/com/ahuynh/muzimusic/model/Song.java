package com.ahuynh.muzimusic.model;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Song implements Parcelable {

    public static final Creator<Song> CREATOR = new Creator<Song>() {
        @Override
        public Song createFromParcel(Parcel in) {
            return new Song(in);
        }

        @Override
        public Song[] newArray(int size) {
            return new Song[size];
        }
    };
    public String artist;
    public String title;
    public String displayName;
    public String album;
    public String relativePath;
    public String absolutePath;
    public String albumArt;
    public int year;
    public int track;
    public int startFrom;
    public long dateAdded;
    public long id;
    public long duration;
    public long albumId;

    public Song(String artist, String title, String displayName, String album, String relativePath, String absolutePath,
                 int year, int track, int startFrom, long dateAdded,
                 long id, long duration, long albumId,
                 Uri albumArt) {
        this.artist = artist;
        this.title =title;
        this.displayName = displayName;
        this.album = album;
        this.relativePath = relativePath;
        this.absolutePath = absolutePath;
        this.year = year;
        this.track = track;
        this.startFrom = startFrom;
        this.dateAdded = dateAdded;
        this.id = id;
        this.duration = duration;
        this.albumId = albumId;
        this.albumArt = albumArt.toString();
    }

    protected Song(Parcel in) {
        artist = in.readString();
        title = in.readString();
        displayName = in.readString();
        album = in.readString();
        relativePath = in.readString();
        absolutePath = in.readString();
        albumArt = in.readString();
        year = in.readInt();
        track = in.readInt();
        startFrom = in.readInt();
        dateAdded = in.readLong();
        id = in.readLong();
        duration = in.readLong();
        albumId = in.readLong();
    }

    @NonNull
    @Override
    public String toString() {
        return "Song{" +
                "artist='" + artist + '\'' +
                ", title='" + title + '\'' +
                ", displayName='" + displayName + '\'' +
                ", album='" + album + '\'' +
                ", relativePath='" + relativePath + '\'' +
                ", absolutePath='" + absolutePath + '\'' +
                ", year=" + year +
                ", track=" + track +
                ", startFrom=" + startFrom +
                ", dateAdded=" + dateAdded +
                ", id=" + id +
                ", duration=" + duration +
                ", albumId=" + albumId +
                ", albumArt=" + albumArt +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(artist);
        dest.writeString(title);
        dest.writeString(displayName);
        dest.writeString(album);
        dest.writeString(relativePath);
        dest.writeString(absolutePath);
        dest.writeString(albumArt);
        dest.writeInt(year);
        dest.writeInt(track);
        dest.writeInt(startFrom);
        dest.writeLong(dateAdded);
        dest.writeLong(id);
        dest.writeLong(duration);
        dest.writeLong(albumId);
    }
}

