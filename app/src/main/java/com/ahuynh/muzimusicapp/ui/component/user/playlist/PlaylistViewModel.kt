package com.ahuynh.muzimusicapp.ui.component.user.playlist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ahuynh.muzimusicapp.data.model.Playlist
import com.ahuynh.muzimusicapp.data.model.Song
import com.ahuynh.muzimusicapp.data.model.request.PlaylistRequest
import com.ahuynh.muzimusicapp.data.repository.PlaylistRepository
import com.ahuynh.muzimusicapp.ui.base.viewmodel.BaseViewModel
import com.ahuynh.muzimusicapp.utils.NetworkResult
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
            if (result is NetworkResult.Success) {
                mess = result.data.message
                getAllPlaylist()

            } else if (result is NetworkResult.Failure) {
                mess= result.errorMessage.message
            }
            addPlaylistStatus.postValue(result is NetworkResult.Success)
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
            if (result is NetworkResult.Success) {
                mess = (result.data.message)
                getAllPlaylist()

            } else if (result is NetworkResult.Failure) {
                mess = result.errorMessage.message
            }
            updatePlaylistStatus.postValue(result is NetworkResult.Success)
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
            if (result is NetworkResult.Success) {
                mess = result.data.message
                getAllSongsNotInPlaylist(playlistId)
                getSongOfPlaylist(playlistId)
            } else if (result is NetworkResult.Failure) {
                mess = result.errorMessage.message
            }
            addSongToPlaylistStatus.postValue(result is NetworkResult.Success)
        }
        registerEventParentJobFinish()
    }


}