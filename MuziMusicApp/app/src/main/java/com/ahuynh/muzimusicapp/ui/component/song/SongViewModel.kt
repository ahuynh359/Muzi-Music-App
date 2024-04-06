package com.ahuynh.muzimusicapp.ui.component.song

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ahuynh.muzimusicapp.model.Song
import com.ahuynh.muzimusicapp.ui.base.BaseViewModel
import com.ahuynh.muzimusicapp.utils.Constants
import com.ahuynh.muzimusicapp.utils.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SongViewModel @Inject constructor(private val repository: SongRepository) : BaseViewModel() {

    var songList = MutableLiveData<List<Song>>()
    var message = MutableLiveData<String?>(null)

    fun getAllSongs(order: Constants.SortingOrder) {
        isLoading.postValue(true)
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.getAllSong(order)
            if (response is Response.Success) {
                songList.postValue(response.data)
            } else if (response is Response.Failure) {
                message.postValue(response.errorMessage)
            }
        }
        registerEventParentJobFinish()
    }

}