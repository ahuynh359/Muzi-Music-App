package com.ahuynh.muzimusicapp.ui

import androidx.lifecycle.viewModelScope
import com.ahuynh.muzimusicapp.ui.base.BaseViewModel
import com.ahuynh.muzimusicapp.utils.Constants
import com.ahuynh.muzimusicapp.utils.SharePreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class MainViewModel @Inject constructor(private val appSharePreferences: SharePreferences) : BaseViewModel() {

     fun restoreState(){
        viewModelScope.launch(Dispatchers.IO){
            Constants.IS_SHUFFLE = appSharePreferences.isShuffle()
            Constants.IS_REPEAT = appSharePreferences.isRepeat()
        }
    }
}