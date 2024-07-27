package com.ahuynh.muzimusicapp.ui.component.user.song.add_song_to_playlist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ahuynh.muzimusicapp.data.model.Playlist
import com.ahuynh.muzimusicapp.data.repository.PlaylistRepository
import com.ahuynh.muzimusicapp.ui.base.viewmodel.BaseViewModel
import com.ahuynh.muzimusicapp.utils.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddSongToPlaylistViewModel @Inject
constructor(
    private val playlistRepository: PlaylistRepository
) :
    BaseViewModel() {
    var playlists = MutableLiveData<List<Playlist>>()
    var addSongToPlaylistStatus = MutableLiveData<Boolean?>(null)
    var mess: String? = null


    fun getAllPlaylists() {
        isLoading.postValue(true)
        parentJob = viewModelScope.launch {
            playlists.postValue(playlistRepository.getAllPlaylist())
        }
        registerEventParentJobFinish()
    }

    fun addSongToPlaylist(playlistId : Long, songId: Long){
        isLoading.postValue(true)
        viewModelScope.launch {
            val result = playlistRepository.addSongToPlaylist(playlistId,songId)
            if(result is Response.Success){
                mess = result.data.message
            } else if(result is Response.Failure){
                mess = result.errorMessage
            }
            addSongToPlaylistStatus.postValue(result is Response.Success)
        }
        registerEventParentJobFinish()
    }
}

