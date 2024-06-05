package com.ahuynh.muzimusicapp.ui.component.player

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ahuynh.muzimusicapp.data.model.Song
import com.ahuynh.muzimusicapp.data.repository.UserRepository
import com.ahuynh.muzimusicapp.ui.base.BaseViewModel
import com.ahuynh.muzimusicapp.utils.Constants
import com.ahuynh.muzimusicapp.utils.helper.SharePreferencesHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlayerViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val sharePreferencesHelper: SharePreferencesHelper
) :
    BaseViewModel() {
    var currentRotate = 0f
    var isPlaying=  MutableLiveData(false)
    var song =  MutableLiveData<Song>()
    var loveSong =  MutableLiveData<Boolean>()
    var sleepTime =  MutableLiveData<String>()
    var songList =  MutableLiveData<ArrayList<Song>>(arrayListOf())
    var isClear: Boolean = false
    var currentSongTime  = MutableLiveData<Int>(0)
    var isShuffle:MutableLiveData<Boolean> =  MutableLiveData(false)
    var isRepeat :MutableLiveData<Boolean> =  MutableLiveData(false)
    var isUserTouchSlider = false
    var audioSessionId = MutableLiveData(0)
    var id = MutableLiveData<String>()
    var username = MutableLiveData<String>()

    init{

    }

    fun setShuffle(value: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            sharePreferencesHelper.setShuffle(value)
            isShuffle.postValue(value)
            Constants.IS_SHUFFLE = value


        }
    }



    fun getShuffle() {
        viewModelScope.launch(Dispatchers.IO) {
            isShuffle.postValue(sharePreferencesHelper.isShuffle())
        }

    }

    fun getUserName() {
        viewModelScope.launch() {
            isShuffle.postValue(sharePreferencesHelper.isShuffle())
        }

    }


    fun setRepeat(value: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            sharePreferencesHelper.setRepeat(value)
            isRepeat.postValue(value)
            Constants.IS_REPEAT = value

        }
    }

    fun getRepeat() {
        viewModelScope.launch(Dispatchers.IO){
            isRepeat.postValue(sharePreferencesHelper.isRepeat())
        }

    }


    fun getUserLogin() {
        viewModelScope.launch(Dispatchers.IO){
            isRepeat.postValue(sharePreferencesHelper.isRepeat())
        }

    }


}