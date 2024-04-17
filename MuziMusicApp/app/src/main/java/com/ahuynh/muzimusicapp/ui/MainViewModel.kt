package com.ahuynh.muzimusicapp.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ahuynh.muzimusicapp.data.model.Song
import com.ahuynh.muzimusicapp.data.repository.SongRepository
import com.ahuynh.muzimusicapp.ui.base.BaseViewModel
import com.ahuynh.muzimusicapp.utils.Constants
import com.ahuynh.muzimusicapp.utils.Response
import com.ahuynh.muzimusicapp.utils.SharePreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val appSharePreferences: SharePreferences,
    private val songRes : SongRepository

) : BaseViewModel() {

    var song = MutableLiveData<Song>()
    var isPlaying = MutableLiveData(false)

     fun restoreState(){
        viewModelScope.launch{
            Constants.IS_SHUFFLE = appSharePreferences.isShuffle()
            Constants.IS_REPEAT = appSharePreferences.isRepeat()
        }
    }

     var songList = MutableLiveData<List<Song>>()

    fun getAllSongs() {
        isLoading.postValue(true)
        viewModelScope.launch(Dispatchers.IO) {
            val response = songRes.getAllSong()
            if (response is Response.Success) {
                songList.postValue(response.data)

            } else if (response is Response.Failure) {
                message.postValue(response.errorMessage)
            }
        }
        registerEventParentJobFinish()
    }


}