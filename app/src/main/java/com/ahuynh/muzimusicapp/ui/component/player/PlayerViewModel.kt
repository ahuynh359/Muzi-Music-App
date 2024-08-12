package com.ahuynh.muzimusicapp.ui.component.player

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ahuynh.muzimusicapp.data.model.Song
import com.ahuynh.muzimusicapp.data.repository.SongRepository
import com.ahuynh.muzimusicapp.ui.base.viewmodel.BaseViewModel
import com.ahuynh.muzimusicapp.utils.Constants
import com.ahuynh.muzimusicapp.utils.NetworkResult
import com.ahuynh.muzimusicapp.utils.helper.SharePreferencesHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlayerViewModel @Inject constructor(
    private val songRepository: SongRepository,
    private val sharePreferencesHelper: SharePreferencesHelper
) :
    BaseViewModel() {
    var currentRotate = 0f
    var isPlaying = MutableLiveData(false)
    var song = MutableLiveData<Song>()

    var loveSong = MutableLiveData<Boolean>()
    var songList = MutableLiveData<ArrayList<Song>>(arrayListOf())
    var isClear: Boolean = false
    var currentSongTime = MutableLiveData<Int>(0)
    var isShuffle: MutableLiveData<Boolean> = MutableLiveData(false)
    var isRepeat: MutableLiveData<Boolean> = MutableLiveData(false)
    var isUserTouchSlider = false
    var audioSessionId = MutableLiveData(0)
    var duration: MutableLiveData<Long> = MutableLiveData(0)
    var timeMillis: MutableLiveData<Long> = MutableLiveData(0)


    var username = MutableLiveData<String>()


    fun setShuffle(value: Boolean) {
        viewModelScope.launch {
            sharePreferencesHelper.setShuffle(value)
            isShuffle.postValue(value)
            Constants.IS_SHUFFLE = value


        }
    }




    fun loveOrUnlove(songId: Long) {
        isLoading.postValue(true)
        parentJob = viewModelScope.launch {
            songRepository.loveSong(
                songId
            )
            isUserLoveSong(songId)

        }
        registerEventParentJobFinish()
    }

    fun isUserLoveSong(songId: Long) {
        isLoading.postValue(true)
        parentJob = viewModelScope.launch {
            val result = songRepository.isUserLoveSong(
                songId
            )

            if (result is NetworkResult.Success) {
                loveSong.postValue(
                    result.data.data
                )
            }

        }
        registerEventParentJobFinish()
    }


    fun getShuffle() {
        viewModelScope.launch {
            isShuffle.postValue(sharePreferencesHelper.isShuffle())
        }

    }


    fun setRepeat(value: Boolean) {
        viewModelScope.launch {
            sharePreferencesHelper.setRepeat(value)
            isRepeat.postValue(value)
            Constants.IS_REPEAT = value

        }
    }

    fun getRepeat() {
        viewModelScope.launch {
            isRepeat.postValue(sharePreferencesHelper.isRepeat())
        }

    }



}