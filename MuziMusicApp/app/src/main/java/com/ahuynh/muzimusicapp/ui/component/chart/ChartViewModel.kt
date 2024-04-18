package com.ahuynh.muzimusicapp.ui.component.chart

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
class ChartViewModel @Inject constructor(private val repository: SongRepository) : BaseViewModel() {

    var songList = MutableLiveData<List<Song>>()
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




}