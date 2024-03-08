package com.ahuynh.muzimusic.di;

import android.app.Application;

import androidx.room.Room;

import com.ahuynh.muzimusic.data.dao.PlaylistDao;
import com.ahuynh.muzimusic.data.db.AppDatabase;
import com.ahuynh.muzimusic.player.MediaPlayerManager;
import com.ahuynh.muzimusic.ui.component.song.SongRepository;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class AppModule {
    @Provides
    public SongRepository provideSongRepository(MediaPlayerManager mediaPlayerManager) {
        return new SongRepository(mediaPlayerManager);
    }

    @Provides
    public AppDatabase provideAppDatabase(@ApplicationContext Application context) {
        return
                Room.databaseBuilder(context.getApplicationContext(),
                                AppDatabase.class, "database")
                        .build();

    }

    @Provides
    public PlaylistDao providePlaylistDao(AppDatabase database) {
        return database.playlistDao();
    }

    @Provides
    public MediaPlayerManager provideMediaPlayerManager() {
        return new MediaPlayerManager();
    }

}
