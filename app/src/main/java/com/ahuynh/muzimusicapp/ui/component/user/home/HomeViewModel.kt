package com.ahuynh.muzimusicapp.ui.component.user.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ahuynh.muzimusicapp.data.database.entity.SongEntity
import com.ahuynh.muzimusicapp.data.model.Album
import com.ahuynh.muzimusicapp.data.model.Singer
import com.ahuynh.muzimusicapp.data.model.Song
import com.ahuynh.muzimusicapp.data.model.Type
import com.ahuynh.muzimusicapp.data.repository.AlbumRepository
import com.ahuynh.muzimusicapp.data.repository.SingerRepository
import com.ahuynh.muzimusicapp.data.repository.SongRepository
import com.ahuynh.muzimusicapp.data.repository.TypeRepository
import com.ahuynh.muzimusicapp.ui.base.bottom_sheet.SortName
import com.ahuynh.muzimusicapp.ui.base.viewmodel.BaseViewModel
import com.ahuynh.muzimusicapp.utils.helper.SharePreferencesHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val albumRepository: AlbumRepository,
    private val songRepository: SongRepository,
    private val singerRepository: SingerRepository,
    private val typeRepository: TypeRepository
) : BaseViewModel() {
    var recentSong = MutableLiveData<List<SongEntity>>()
    var newAlbumList = MutableLiveData<List<Album>>()
    var newSingerList = MutableLiveData<List<Singer>>()
    var newSongList = MutableLiveData<List<Song>>()
    var newTypeList = MutableLiveData<List<Type>>()


    fun getRecentSongs() {
        viewModelScope.launch {
            recentSong.postValue(songRepository.getRecentSongs())
        }
    }


    fun getNewSongs() {
        isLoading.postValue(true)
        parentJob = viewModelScope.launch {
            newSongList.postValue(songRepository.getAllSongs(SortName.NEW))
        }
        registerEventParentJobFinish()
    }


    fun getNewSingers() {
        isLoading.postValue(true)
        parentJob = viewModelScope.launch {
            newSingerList.postValue(singerRepository.getAllSingers(SortName.NEW))
        }
        registerEventParentJobFinish()
    }


    fun getNewAlbums() {
        isLoading.postValue(true)
        parentJob = viewModelScope.launch {
            newAlbumList.postValue(albumRepository.getAllAlbums(SortName.NEW))
        }
        registerEventParentJobFinish()
    }

    fun getNewTypes() {
        isLoading.postValue(true)
        parentJob = viewModelScope.launch {
            newTypeList.postValue(typeRepository.getAllTypes(SortName.NEW))
        }
        registerEventParentJobFinish()
    }


}












