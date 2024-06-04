package com.ahuynh.muzimusicapp.ui.component.activity.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ahuynh.muzimusicapp.data.model.SongOld
import com.ahuynh.muzimusicapp.data.repository.SongRepository
import com.ahuynh.muzimusicapp.ui.base.BaseViewModel
import com.ahuynh.muzimusicapp.utils.Constants
import com.ahuynh.muzimusicapp.utils.Response
import com.ahuynh.muzimusicapp.utils.helper.SharePreferencesHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val appSharePreferencesHelper: SharePreferencesHelper,
                                        private val songRes: SongRepository,


                                        ) : BaseViewModel() {

    var songOld = MutableLiveData<SongOld>()
    var isPlaying = MutableLiveData(false)
    var songOldList = MutableLiveData<List<SongOld>>()

     fun restoreState(){
        viewModelScope.launch{
            Constants.IS_SHUFFLE = appSharePreferencesHelper.isShuffle()
            Constants.IS_REPEAT = appSharePreferencesHelper.isRepeat()
        }
    }


    fun getAllSongs() {
        isLoading.postValue(true)
        viewModelScope.launch {
            val response = songRes.getAllSong()
            if (response is Response.Success) {
                songOldList.postValue(response.data)

            } else if (response is Response.Failure) {
                message.postValue(response.errorMessage)
            }
        }
        registerEventParentJobFinish()
    }


}