package com.ahuynh.muzimusicapp.ui.component.library

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.ahuynh.muzimusicapp.utils.Response
import com.ahuynh.muzimusicapp.model.Song
import com.ahuynh.muzimusicapp.utils.NetworkConnectivityHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LibraryViewModel @Inject constructor(
    private val repository: LibraryRepository,

) : ViewModel() {




    fun getAllSongs() = liveData(Dispatchers.IO) {
        repository.getAllSongs().collect { response ->
            emit(response)
        }
    }

    private val _songs = MutableLiveData<Response<List<Song>>>()
    val songs: LiveData<Response<List<Song>>> = _songs

    private fun loadSongs() {
        viewModelScope.launch {
            _songs.value = Response.Loading
            _songs.value = repository.getSongs()
            Log.d("ABC",_songs.value.toString())
        }
    }
}