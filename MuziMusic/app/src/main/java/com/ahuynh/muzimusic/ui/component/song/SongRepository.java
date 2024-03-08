package com.ahuynh.muzimusic.ui.component.song;

import android.content.Context;

import com.ahuynh.muzimusic.data.model.Song;
import com.ahuynh.muzimusic.player.MediaPlayerManager;
import com.ahuynh.muzimusic.utils.MusicLibraryHelper;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.rxjava3.core.Single;

@Singleton
public class SongRepository {

    private MediaPlayerManager mediaPlayerManager;

    @Inject
    public SongRepository(MediaPlayerManager mediaPlayerManager) {
        this.mediaPlayerManager = mediaPlayerManager;
    }

    public Single<List<Song>> getAllSong(Context context) {
        List<Song> songList = MusicLibraryHelper.fetchMusicLibrary(context);
        return Single.just(songList);
    }

    public void playSong(String filePath) {
        mediaPlayerManager.play(filePath);
    }

    public void pauseSong() {
        mediaPlayerManager.pause();
    }

    public void stopSong() {
        mediaPlayerManager.stop();
    }
}
