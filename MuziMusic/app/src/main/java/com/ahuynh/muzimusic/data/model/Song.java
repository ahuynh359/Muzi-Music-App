package com.ahuynh.muzimusic.data.model;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "song")
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
    @PrimaryKey
    private long songId;
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

    private long duration;
    private long albumId;

    public Song(){

    }

    public long getSongId() {
        return songId;
    }

    public void setSongId(long songId) {
        this.songId = songId;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getRelativePath() {
        return relativePath;
    }

    public void setRelativePath(String relativePath) {
        this.relativePath = relativePath;
    }

    public String getAbsolutePath() {
        return absolutePath;
    }

    public void setAbsolutePath(String absolutePath) {
        this.absolutePath = absolutePath;
    }

    public String getAlbumArt() {
        return albumArt;
    }

    public void setAlbumArt(String albumArt) {
        this.albumArt = albumArt;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getTrack() {
        return track;
    }

    public void setTrack(int track) {
        this.track = track;
    }

    public int getStartFrom() {
        return startFrom;
    }

    public void setStartFrom(int startFrom) {
        this.startFrom = startFrom;
    }

    public long getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(long dateAdded) {
        this.dateAdded = dateAdded;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public long getAlbumId() {
        return albumId;
    }

    public void setAlbumId(long albumId) {
        this.albumId = albumId;
    }

    public Song(String artist, String title, String displayName, String album, String relativePath, String absolutePath,
                int year, int track, int startFrom, long dateAdded,
                long songId, long duration, long albumId,
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
        this.songId = songId;
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
        songId = in.readLong();
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
                ", id=" + songId +
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
        dest.writeLong(songId);
        dest.writeLong(duration);
        dest.writeLong(albumId);
    }
}

