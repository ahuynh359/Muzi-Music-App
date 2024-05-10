package com.ahuynh.muzimusicapp.ui.component.song

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ahuynh.muzimusicapp.data.model.Song
import com.ahuynh.muzimusicapp.data.model.playlist.Playlist
import com.ahuynh.muzimusicapp.data.repository.NotificationRepository
import com.ahuynh.muzimusicapp.data.repository.PlaylistRepository
import com.ahuynh.muzimusicapp.data.repository.SongRepository
import com.ahuynh.muzimusicapp.ui.base.BaseViewModel
import com.ahuynh.muzimusicapp.utils.Response
import com.ahuynh.muzimusicapp.utils.helper.SharePreferencesHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SongViewModel @Inject constructor(
    private val repository: SongRepository,
    private val sharePreferencesHelper: SharePreferencesHelper,
    private val playlistRepository: PlaylistRepository,
    private val notificationRepository: NotificationRepository
) : BaseViewModel() {

    var songList = MutableLiveData<List<Song>>()
    var deleteSongFromPlaylist = MutableLiveData<Boolean>()
    var listenSongList = MutableLiveData<List<Song>>()
    var searchSongList = MutableLiveData<List<Song>>()
    var deleteSong = MutableLiveData<Boolean>()
    var loveSong = MutableLiveData<Boolean>()

    var sortIndex = MutableLiveData<Int>(-1)
    var getNotification = MutableLiveData<Int>()




    init {
        getAllSongs()
        getAllSongByListen()

    }

    fun getUnreadNoti() {
//        isLoading.postValue(true)
//        viewModelScope.launch {
//            val response = notificationRepository.getNotification()
//            if (response is Response.Success) {
//                getNotification.postValue(response.data)
//                CURRENT_NOTI = response.data
//            } else if (response is Response.Failure) {
//                message.postValue(response.errorMessage)
//            }
//        }
//        registerEventParentJobFinish()
        viewModelScope.launch {
            getNotification.postValue(sharePreferencesHelper.getUnreadNoti())

        }

    }


    fun deleteSongFromPlaylist(playlist: Playlist, song: Song) {
        isLoading.postValue(true)
        viewModelScope.launch {
            val response = playlistRepository.deleteSongFromPlaylist(playlist, song)
            if (response is Response.Success) {
                deleteSongFromPlaylist.postValue(response.data)
            } else if (response is Response.Failure) {
                message.postValue(response.errorMessage)
            }
        }
        registerEventParentJobFinish()
    }



    fun getAllSongs() {
        isLoading.postValue(true)
        viewModelScope.launch {
            val response = repository.getAllSong()
            if (response is Response.Success) {
                songList.postValue(response.data)
            } else if (response is Response.Failure) {
                message.postValue(response.errorMessage)
            }
        }
        registerEventParentJobFinish()
    }

    fun updateSongListen(song: Song) {
        isLoading.postValue(true)
        viewModelScope.launch() {
            val response = repository.updateSongListen(song)
            if (response is Response.Success) {
                message.postValue("Ok")
            } else if (response is Response.Failure) {
                message.postValue(response.errorMessage)
            }


        }
        registerEventParentJobFinish()
    }

    fun updateSongWithCurrentDate(song: Song, currentDate: String) {
        isLoading.postValue(true)

        viewModelScope.launch()
        {
            val response1 = repository.updateSongWithCurrentDate(song, currentDate)
            if (response1 is Response.Success) {
                message.postValue("Ok")
            } else if (response1 is Response.Failure) {
                message.postValue(response1.errorMessage)
            }
        }
        registerEventParentJobFinish()
    }

    fun searchSong(name : String ){
        isLoading.postValue(true)
        viewModelScope.launch() {
            val response = repository.searchSong(name)
            if (response is Response.Success) {
                searchSongList.postValue(response.data)
            } else if (response is Response.Failure) {
                message.postValue(response.errorMessage)
            }
        }
        registerEventParentJobFinish()
    }

    fun deleteSong(song: Song) {
        isLoading.postValue(true)
        viewModelScope.launch() {
            val response = repository.deleteSong(song.id!!)
            if (response is Response.Success) {
                deleteSong.postValue(response.data)
            } else if (response is Response.Failure) {
                message.postValue(response.errorMessage)
            }
        }
        registerEventParentJobFinish()
    }


    fun getAllSongByListen(){
        isLoading.postValue(true)
        viewModelScope.launch() {
            val response = repository.getAllSongByListen()
            if (response is Response.Success) {
                listenSongList.postValue(response.data)
            } else if (response is Response.Failure) {
                message.postValue(response.errorMessage)
            }
        }
        registerEventParentJobFinish()
    }

    fun updateSongLoveStatus(id: String, newLoveStatus: Boolean) {
        isLoading.postValue(true)
        viewModelScope.launch() {
            val response = repository.updateSongLoveStatus(id, newLoveStatus)
            if (response is Response.Success) {
                loveSong.postValue(response.data)
            } else if (response is Response.Failure) {
                message.postValue(response.errorMessage)
            }
        }
        registerEventParentJobFinish()
    }






}