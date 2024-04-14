package com.ahuynh.muzimusicapp.ui.component.player

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ahuynh.muzimusicapp.data.model.SleepTimerState
import com.ahuynh.muzimusicapp.data.model.Song
import com.ahuynh.muzimusicapp.data.repository.PlayerRepository
import com.ahuynh.muzimusicapp.ui.base.BaseViewModel
import com.ahuynh.muzimusicapp.utils.Constants
import com.ahuynh.muzimusicapp.utils.SharePreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlayerViewModel @Inject constructor(
    private val playerRepository: PlayerRepository,
    private val sharePreferences: SharePreferences
) :
    BaseViewModel() {
    var isPlaying=  MutableLiveData(false)
    var song =  MutableLiveData<Song>()
    var songList =  MutableLiveData<ArrayList<Song>>(arrayListOf())
    var isClear: Boolean = false
    var sleepTime = MutableLiveData<String>()
    var sleepTimerState = MutableLiveData<SleepTimerState>()
    var currentSongTime  = MutableLiveData<Int>(0)
    var isShuffle = MutableLiveData(false)
    var isRepeat = MutableLiveData(false)

    fun setShuffle(value: Boolean) {
        viewModelScope.launch {
            sharePreferences.setShuffle(value)
            isShuffle.postValue(value)
            Constants.IS_SHUFFLE = value

        }
    }

    fun getShuffle() {
        viewModelScope.launch {
            isShuffle.postValue(sharePreferences.isShuffle())
        }
    }


    fun setRepeat(value: Boolean) {
        viewModelScope.launch {
            sharePreferences.setRepeat(value)
            isRepeat.postValue(value)
            Constants.IS_REPEAT = value

        }
    }

    fun getRepeat() {
        viewModelScope.launch {
            isRepeat.postValue(sharePreferences.isRepeat())
        }
    }
}