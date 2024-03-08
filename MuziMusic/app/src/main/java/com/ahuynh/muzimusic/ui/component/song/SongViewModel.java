package com.ahuynh.muzimusic.ui.component.song;


import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ahuynh.muzimusic.data.model.Song;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class SongViewModel extends ViewModel {

    private final SongRepository repository;


    @Inject
    public SongViewModel(SongRepository repository) {
        this.repository = repository;

    }

    private MutableLiveData<List<Song>> songList = new MutableLiveData<>();


    public void setSongList(List<Song> list) {
        songList.postValue(list);
    }

    public MutableLiveData<List<Song>> getSongList() {
        return songList;
    }


}
