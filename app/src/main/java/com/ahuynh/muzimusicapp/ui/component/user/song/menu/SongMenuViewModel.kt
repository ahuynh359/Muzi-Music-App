package com.ahuynh.muzimusicapp.ui.component.user.song.menu


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ahuynh.muzimusicapp.data.model.Song
import com.ahuynh.muzimusicapp.data.model.response.toListSong
import com.ahuynh.muzimusicapp.data.repository.SongRepository
import com.ahuynh.muzimusicapp.data.repository.UserRepository
import com.ahuynh.muzimusicapp.ui.base.viewmodel.BaseViewModel
import com.ahuynh.muzimusicapp.utils.Response
import com.ahuynh.muzimusicapp.utils.helper.SharePreferencesHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SongMenuViewModel @Inject
constructor(
    private val songRepository: SongRepository
) :
    BaseViewModel() {
    var mess: String? = null

    var loveSong = MutableLiveData<Boolean>()


    fun isUserLoveSong(songId: Long) {
        isLoading.postValue(true)
        parentJob = viewModelScope.launch {
            val result = songRepository.isUserLoveSong(
                songId
            )

            if (result is Response.Success) {
                loveSong.postValue(
                    result.data.data
                )
            }

        }
        registerEventParentJobFinish()
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
}

