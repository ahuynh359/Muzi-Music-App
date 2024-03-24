package com.ahuynh.muzimusicapp.ui.component.library

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ahuynh.muzimusicapp.model.Playlist
import com.ahuynh.muzimusicapp.model.Song
import com.ahuynh.muzimusicapp.utils.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LibraryViewModel @Inject constructor(
    private val repository: LibraryRepository,

) : ViewModel() {


    private val _songs = MutableLiveData<Response<List<Song>>>()
    val song: LiveData<Response<List<Song>>>
        get() = _songs
    private val _playlists = MutableLiveData<Response<List<Playlist>>>()
    val playlists: LiveData<Response<List<Playlist>>>
        get() = _playlists


    fun getSong() {
        _songs.value = Response.Loading
        repository.getSongs { _songs.value = it }
    }

    fun getPlaylist() {
        _playlists.value = Response.Loading
        repository.getPlaylist { _playlists.value = it }
    }




}