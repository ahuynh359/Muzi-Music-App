package com.ahuynh.muzimusic.data.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.ahuynh.muzimusic.data.dao.PlaylistDao;
import com.ahuynh.muzimusic.data.model.Playlist;

@Database(entities = {Playlist.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public abstract PlaylistDao playlistDao();


}