package com.ahuynh.muzimusicapp.ui.component.song

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ahuynh.muzimusicapp.data.model.Song
import com.ahuynh.muzimusicapp.data.repository.SongRepository
import com.ahuynh.muzimusicapp.ui.base.BaseViewModel
import com.ahuynh.muzimusicapp.utils.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SongViewModel @Inject constructor(private val repository: SongRepository) : BaseViewModel() {

    var songList = MutableLiveData<List<Song>>()
    var searchSongList = MutableLiveData<List<Song>>()

    fun getAllSongs() {
        isLoading.postValue(true)
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.getAllSong()
            if (response is Response.Success) {
                songList.postValue(response.data)
            } else if (response is Response.Failure) {
                message.postValue(response.errorMessage)
            }
        }
        registerEventParentJobFinish()
    }

    fun updateSongListen(song : Song){
        isLoading.postValue(true)
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.updateSongListen(song)
            if (response is Response.Success) {
                message.postValue("Ok")
            } else if (response is Response.Failure) {
                message.postValue(response.errorMessage)
            }
        }
        registerEventParentJobFinish()
    }

    fun searchSong(name : String ){
        isLoading.postValue(true)
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.searchSong(name)
            if (response is Response.Success) {
                searchSongList.postValue(response.data)
            } else if (response is Response.Failure) {
                message.postValue(response.errorMessage)
            }
        }
        registerEventParentJobFinish()
    }

    fun getAllSongByListen(){
        isLoading.postValue(true)
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.getAllSong()
            if (response is Response.Success) {
                searchSongList.postValue(response.data)
            } else if (response is Response.Failure) {
                message.postValue(response.errorMessage)
            }
        }
        registerEventParentJobFinish()
    }






}