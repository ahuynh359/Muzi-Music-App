package com.ahuynh.muzimusic;


import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ahuynh.muzimusic.model.Song;
import com.ahuynh.muzimusic.utils.MusicLibraryHelper;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class MainViewModel extends ViewModel {
    private ExecutorService executor = Executors.newSingleThreadExecutor();

    private MutableLiveData<List<Song>> songList = new MutableLiveData<>();

    public void loadSongsFromLocal(Context context) {
        executor.execute(() -> {
            List<Song> songs = MusicLibraryHelper.fetchMusicLibrary(context);
            songList.postValue(songs);
        });

    }

    public MutableLiveData<List<Song>> getSongList() {
        return songList;
    }
}
