package com.ahuynh.muzimusicapp.ui.component.user.song

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
class SongViewModel @Inject
constructor(
    private val sharePreferencesHelper: SharePreferencesHelper,
    private val userRepository: UserRepository,
    private val songRepository: SongRepository
) :
    BaseViewModel() {
    var loveSong = MutableLiveData<List<Song>>()
    var des = MutableLiveData<String>()
    var addSongToPlaylistStatus = MutableLiveData<Boolean?>(null)
    var mess: String? = null



    fun getLoveSong() {
        isLoading.postValue(true)
        viewModelScope.launch {
            val result = songRepository.getLoveSong()
            if(result is Response.Success){
                mess = result.data.message
                loveSong.postValue(result.data.data.songs.toListSong())
                des.postValue(result.data.data.total)
            } else if(result is Response.Failure){
                mess = result.errorMessage
            }
            addSongToPlaylistStatus.postValue(result is Response.Success)
        }
        registerEventParentJobFinish()
    }
}

