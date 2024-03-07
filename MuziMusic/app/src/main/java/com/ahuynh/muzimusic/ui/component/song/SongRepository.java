package com.ahuynh.muzimusic.ui.component.song;

import android.content.Context;

import com.ahuynh.muzimusic.model.Song;
import com.ahuynh.muzimusic.utils.MusicLibraryHelper;

import java.util.List;

import javax.inject.Singleton;

import io.reactivex.rxjava3.core.Observable;

@Singleton
public class SongRepository {

    public Observable<List<Song>> getAllSong(Context context) {
        List<Song> songList = MusicLibraryHelper.fetchMusicLibrary(context);
        return Observable.just(songList);
    }
}
