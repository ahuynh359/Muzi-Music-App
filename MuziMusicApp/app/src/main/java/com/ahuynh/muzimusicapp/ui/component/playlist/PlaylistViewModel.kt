package com.ahuynh.muzimusicapp.ui.component.playlist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ahuynh.muzimusicapp.model.playlist.Playlist
import com.ahuynh.muzimusicapp.model.playlist.PlaylistModel
import com.ahuynh.muzimusicapp.ui.base.BaseViewModel
import com.ahuynh.muzimusicapp.utils.Constants
import com.ahuynh.muzimusicapp.utils.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlaylistViewModel @Inject constructor(private val playlistRepository: PlaylistRepository) :
    BaseViewModel() {
    var message = MutableLiveData<String?>(null)
    var playlists = MutableLiveData<List<Playlist>>()
    var addPlaylistStatus = MutableLiveData<Boolean>()
    var deletePlaylistStatus = MutableLiveData<Boolean>()
    var updatePlaylistStatus = MutableLiveData<Boolean>()



    fun getAllPlaylist(order: Constants.SortingOrder) {
        isLoading.postValue(true)
        parentJob = viewModelScope.launch {
            val response = playlistRepository.getAllPlaylist(order)
            if (response is Response.Success) {
                playlists.postValue(response.data)
            } else if (response is Response.Failure) {
                message.postValue(response.errorMessage)
            }
        }
        registerEventParentJobFinish()
    }

    fun addNewPlaylist(playlist : PlaylistModel){
        parentJob = viewModelScope.launch {
            val response = playlistRepository.addPlaylist(playlist)
            if (response is Response.Success) {
                addPlaylistStatus.postValue(response.data)
            } else if (response is Response.Failure) {
                message.postValue(response.errorMessage)
            }
        }
        registerEventParentJobFinish()
    }

    fun deletePlaylist(playlist: Playlist) {
        parentJob = viewModelScope.launch {
            val response = playlistRepository.deletePlaylist(playlist)
            if (response is Response.Success) {
                deletePlaylistStatus.postValue(response.data)
            } else if (response is Response.Failure) {
                message.postValue(response.errorMessage)
            }
        }
        registerEventParentJobFinish()
    }

    fun updatePlaylist(playlist: Playlist, newName: String) {
        parentJob = viewModelScope.launch {
            val response = playlistRepository.updatePlaylist(playlist, newName)
            if (response is Response.Success) {
                updatePlaylistStatus.postValue(response.data)
            } else if (response is Response.Failure) {
                message.postValue(response.errorMessage)
            }
        }
        registerEventParentJobFinish()
    }
}