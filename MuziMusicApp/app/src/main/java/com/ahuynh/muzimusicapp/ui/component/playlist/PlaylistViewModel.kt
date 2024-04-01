package com.ahuynh.muzimusicapp.ui.component.playlist

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ahuynh.muzimusicapp.model.playlist.Playlist
import com.ahuynh.muzimusicapp.model.playlist.PlaylistModel
import com.ahuynh.muzimusicapp.utils.Constants
import com.ahuynh.muzimusicapp.utils.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlaylistViewModel @Inject constructor(private val playlistRepository: PlaylistRepository) : ViewModel() {
     var playlists = MutableLiveData<Response<List<Playlist>>>()
     var addPlaylistStatus = MutableLiveData<Response<Boolean>>()


    fun getAllPlaylist(order: Constants.SortingOrder) {
        Log.d("PlaylistFragment","Call API")
        playlists.postValue(Response.Loading)
        viewModelScope.launch {
            val response = playlistRepository.getAllPlaylist(order)
            playlists.postValue(response)
        }
    }

    fun addNewPlaylist(playlist : PlaylistModel){
        addPlaylistStatus.postValue(Response.Loading)
        viewModelScope.launch {
            val response = playlistRepository.addPlaylist(playlist)
            addPlaylistStatus.postValue(response)
        }
    }
}