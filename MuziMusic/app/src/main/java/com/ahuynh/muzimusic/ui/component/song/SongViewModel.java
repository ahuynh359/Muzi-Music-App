package com.ahuynh.muzimusic.ui.component.song;


import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ahuynh.muzimusic.model.Song;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

@HiltViewModel
public class SongViewModel extends ViewModel {

    private final SongRepository repository;
    private Disposable disposable;

    @Inject
    public SongViewModel(SongRepository repository,Disposable disposable) {
        this.repository = repository;
        this.disposable = disposable;
    }

    private MutableLiveData<List<Song>> songList = new MutableLiveData<>();


    public LiveData<List<Song>> getSongList(Context context) {
        disposable = repository.getAllSong(context)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        list -> songList.postValue(list),
                        Throwable::printStackTrace
                );
        return songList;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }
}
