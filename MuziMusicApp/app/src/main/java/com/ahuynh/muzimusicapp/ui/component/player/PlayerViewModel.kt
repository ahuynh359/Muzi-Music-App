package com.ahuynh.muzimusicapp.ui.component.player

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ahuynh.muzimusicapp.data.model.SleepTimerState
import com.ahuynh.muzimusicapp.data.model.Song
import com.ahuynh.muzimusicapp.data.repository.PlayerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlayerViewModel @Inject constructor(
    private val playerRepository: PlayerRepository,
    private val sharePreferences: AppSharePreferences
) :
    ViewModel() {
    var isPlaying=  MutableLiveData(false)
    var song =  MutableLiveData<Song>()
    var songList =  MutableLiveData<ArrayList<Song>>(arrayListOf())
    var isClear: Boolean = false
    var sleepTime = MutableLiveData<String>()
    var sleepTimerState = MutableLiveData<SleepTimerState>()
    var currentSongTime  = MutableLiveData<Int>(0)
    var isShuffle = MutableLiveData(false)

    fun toggleShuffle() {
        viewModelScope.launch {

        }
    }
}