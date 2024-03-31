package com.ahuynh.muzimusicapp.ui.component.song

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ahuynh.muzimusicapp.model.Song
import com.ahuynh.muzimusicapp.ui.component.library.LibraryRepository
import com.ahuynh.muzimusicapp.utils.Constants
import com.ahuynh.muzimusicapp.utils.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SongViewModel @Inject constructor(private val repository: SongRepository) : ViewModel(){

    private val _songs = MutableLiveData<Response<List<Song>>>()
    val songs: LiveData<Response<List<Song>>>
        get() = _songs

    fun getAllSongs(order: Constants.SortingOrder) {
        _songs.postValue(Response.Loading)
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.getAllSong(order)
            _songs.postValue(response)
        }
    }

}