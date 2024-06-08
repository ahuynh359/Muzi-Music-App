package com.ahuynh.muzimusicapp.ui.component.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ahuynh.muzimusicapp.data.model.Album
import com.ahuynh.muzimusicapp.data.model.Song
import com.ahuynh.muzimusicapp.data.model.Type
import com.ahuynh.muzimusicapp.data.repository.AlbumRepository
import com.ahuynh.muzimusicapp.data.repository.SongRepository
import com.ahuynh.muzimusicapp.data.repository.TypeRepository
import com.ahuynh.muzimusicapp.data.repository.UserRepository
import com.ahuynh.muzimusicapp.ui.base.BaseViewModel
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
    private val userRepository: UserRepository
) : BaseViewModel() {

    var albumList = MutableLiveData<List<Album>>()
    var typeList = MutableLiveData<List<Type>>()
    var songList = MutableLiveData<List<Song>>()
    var loveSongList = MutableLiveData<List<Song>>()
    var deleteSongFromPlaylist = MutableLiveData<Boolean>()
    var accessToken = MutableLiveData<String>()

    var songOfAlbum = MutableLiveData<List<Song>>()

    var deleteSong = MutableLiveData<Boolean>()

    var sortIndex = MutableLiveData<Int>(-1)
    var getNotification = MutableLiveData<Int>()


    init {
        getAllSongs()
        getAllAlbum()
        getAllType()
        getAccessToken()
        getAllLoveSong()

    }

     fun getAllLoveSong() {
        isLoading.postValue(true)
        parentJob = viewModelScope.launch {
            loveSongList.postValue(userRepository.getLoveSong(sharePreferencesHelper.getId()))
        }
        registerEventParentJobFinish()
    }

    fun getAccessToken() {

        viewModelScope.launch {
            accessToken.postValue(sharePreferencesHelper.getToken())

        }

    }


    fun getUnreadNoti() {

        viewModelScope.launch {
            getNotification.postValue(sharePreferencesHelper.getUnreadNoti())

        }

    }




    fun getAllSongs() {
        isLoading.postValue(true)
        parentJob = viewModelScope.launch {
            songList.postValue(songRepository.getAllSong())
        }
        registerEventParentJobFinish()
    }

    fun getAllType() {
        isLoading.postValue(true)
        parentJob = viewModelScope.launch {
            typeList.postValue(typeRepository.getAllType())
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


    fun getAllAlbum() {
        isLoading.postValue(true)
        parentJob = viewModelScope.launch {
            albumList.postValue(albumRepository.getAllAlbum())
        }
        registerEventParentJobFinish()
    }
}












