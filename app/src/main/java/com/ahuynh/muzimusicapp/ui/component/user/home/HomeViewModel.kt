package com.ahuynh.muzimusicapp.ui.component.user.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ahuynh.muzimusicapp.data.model.Album
import com.ahuynh.muzimusicapp.data.model.Singer
import com.ahuynh.muzimusicapp.data.model.Song
import com.ahuynh.muzimusicapp.data.repository.AlbumRepository
import com.ahuynh.muzimusicapp.data.repository.SingerRepository
import com.ahuynh.muzimusicapp.data.repository.SongRepository
import com.ahuynh.muzimusicapp.data.repository.TypeRepository
import com.ahuynh.muzimusicapp.ui.base.viewmodel.BaseViewModel
import com.ahuynh.muzimusicapp.utils.helper.SharePreferencesHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val sharePreferencesHelper: SharePreferencesHelper,
    private val albumRepository: AlbumRepository,
    private val songRepository: SongRepository,
    private val typeRepository: TypeRepository,
    private val singerRepository: SingerRepository
) : BaseViewModel() {

    var newAlbumList = MutableLiveData<List<Album>>()
    var newSingerList = MutableLiveData<List<Singer>>()
    var newSongList = MutableLiveData<List<Song>>()
    var deleteSongFromPlaylist = MutableLiveData<Boolean>()

    var songOfAlbum = MutableLiveData<List<Song>>()

    var deleteSong = MutableLiveData<Boolean>()


    init {
        getNewSongs()
        getNewAlbums()
        getNewSingers()



    }




    fun getNewSongs() {
        isLoading.postValue(true)
        parentJob = viewModelScope.launch {
            newSongList.postValue(songRepository.getNewSongs())
        }
        registerEventParentJobFinish()
    }

    fun getNewSingers() {
        isLoading.postValue(true)
        parentJob = viewModelScope.launch {
            newSingerList.postValue(singerRepository.getNewSingers())
        }
        registerEventParentJobFinish()
    }

    fun getSongOfAlbum(id: Long) {
        isLoading.postValue(true)
        parentJob = viewModelScope.launch {
            songOfAlbum.postValue(albumRepository.getSongsFromAlbum(id))
        }
        registerEventParentJobFinish()
    }


    fun getNewAlbums() {
        isLoading.postValue(true)
        parentJob = viewModelScope.launch {
            newAlbumList.postValue(albumRepository.getNewAlbums())
        }
        registerEventParentJobFinish()
    }



}












