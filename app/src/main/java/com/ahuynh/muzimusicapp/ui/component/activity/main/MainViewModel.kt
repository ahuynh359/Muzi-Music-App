package com.ahuynh.muzimusicapp.ui.component.activity.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ahuynh.muzimusicapp.data.model.Song
import com.ahuynh.muzimusicapp.ui.base.BaseViewModel
import com.ahuynh.muzimusicapp.utils.Constants
import com.ahuynh.muzimusicapp.utils.helper.SharePreferencesHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val appSharePreferencesHelper: SharePreferencesHelper)
    : BaseViewModel() {

    var song = MutableLiveData<Song>()
    var isPlaying = MutableLiveData(false)

     fun restoreState(){
        viewModelScope.launch{
            Constants.IS_SHUFFLE = appSharePreferencesHelper.isShuffle()
            Constants.IS_REPEAT = appSharePreferencesHelper.isRepeat()
        }
    }




}