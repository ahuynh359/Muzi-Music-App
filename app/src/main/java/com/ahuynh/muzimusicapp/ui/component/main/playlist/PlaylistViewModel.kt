package com.ahuynh.muzimusicapp.ui.component.main.playlist

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ahuynh.muzimusicapp.data.model.Playlist
import com.ahuynh.muzimusicapp.data.model.Song
import com.ahuynh.muzimusicapp.data.model.request.PlaylistRequest
import com.ahuynh.muzimusicapp.data.repository.PlaylistRepository
import com.ahuynh.muzimusicapp.data.repository.UserRepository
import com.ahuynh.muzimusicapp.ui.base.viewmodel.BaseViewModel
import com.ahuynh.muzimusicapp.utils.Response
import com.ahuynh.muzimusicapp.utils.helper.SharePreferencesHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlaylistViewModel @Inject
constructor(private val playlistRepository: PlaylistRepository,
) :
    BaseViewModel() {

    var playlists = MutableLiveData<List<Playlist>>()
    var email = MutableLiveData<String>()
    var addPlaylistStatus = MutableLiveData<Boolean?>()
    var updatePlaylistStatus = MutableLiveData<Boolean?>()
    var songOfPlaylist = MutableLiveData<List<Song>>()

    var listSongNotInPlaylist = MutableLiveData<List<Song>>()
    var addSongToPlaylistStatus = MutableLiveData<Boolean?>(null)
    var mess: String? = null


    init {
        getAllPlaylist()
        Log.d("ABC","New")
    }

    fun getAllPlaylist() {
        isLoading.postValue(true)
        parentJob = viewModelScope.launch {
            playlists.postValue(playlistRepository.getAllPlaylist())
        }
        registerEventParentJobFinish()
    }

    fun addNewPlaylist(playlist: String) {
        isLoading.postValue(true)
        parentJob = viewModelScope.launch {

            val playlistRequest = PlaylistRequest(playlist)
            val result = playlistRepository.addPlaylist(playlistRequest)
            if (result is Response.Success) {
                mess = (result.data.message)
                getAllPlaylist()

            } else if (result is Response.Failure) {
                mess= (result.errorMessage)
            }
            addPlaylistStatus.postValue(result is Response.Success)
        }

        registerEventParentJobFinish()
    }

    fun deletePlaylist(id: Long) {
        isLoading.postValue(true)
        parentJob = viewModelScope.launch {
            playlistRepository.deletePlaylist(id)
            getAllPlaylist()


        }
        registerEventParentJobFinish()
    }
    fun updatePlaylist(playlist: String , id : Long) {
        isLoading.postValue(true)
        parentJob = viewModelScope.launch {

            val playlistRequest = PlaylistRequest(playlist)
            val result = playlistRepository.updatePlaylist(playlistRequest , id)
            if (result is Response.Success) {
                mess = (result.data.message)
                getAllPlaylist()

            } else if (result is Response.Failure) {
                mess = (result.errorMessage)
            }
            updatePlaylistStatus.postValue(result is Response.Success)
        }

        registerEventParentJobFinish()
    }
    fun getSongOfPlaylist(id: Long) {
        isLoading.postValue(true)
        parentJob = viewModelScope.launch {
            songOfPlaylist.postValue(playlistRepository.getAllSongFromPlaylist(id))
        }
        registerEventParentJobFinish()

    }

    fun getAllSongsNotInPlaylist(id: Long) {
        isLoading.postValue(true)
        parentJob = viewModelScope.launch {
            listSongNotInPlaylist.postValue(playlistRepository.getAllSongsNotFromPlaylist(id))
        }
        registerEventParentJobFinish()
    }

    fun addSongToPlaylist(playlistId: Long, songId: Long) {
        isLoading.postValue(true)
        viewModelScope.launch {
            val result = playlistRepository.addSongToPlaylist(playlistId, songId)
            if (result is Response.Success) {
                mess = result.data.message
                getAllSongsNotInPlaylist(playlistId)
                getSongOfPlaylist(playlistId)
            } else if (result is Response.Failure) {
                mess = result.errorMessage
            }
            addSongToPlaylistStatus.postValue(result is Response.Success)
        }
        registerEventParentJobFinish()
    }


}