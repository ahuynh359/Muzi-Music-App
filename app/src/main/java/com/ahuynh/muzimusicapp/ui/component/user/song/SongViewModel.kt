package com.ahuynh.muzimusicapp.ui.component.user.song

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ahuynh.muzimusicapp.data.model.Song
import com.ahuynh.muzimusicapp.data.model.response.toListSong
import com.ahuynh.muzimusicapp.data.repository.SongRepository
import com.ahuynh.muzimusicapp.ui.base.viewmodel.BaseViewModel
import com.ahuynh.muzimusicapp.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SongViewModel @Inject
constructor(
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
            if(result is NetworkResult.Success){
                mess = result.data.message
                loveSong.postValue(result.data.data.songs.toListSong())
                des.postValue(result.data.data.total)
            } else if(result is NetworkResult.Failure){
                mess = result.errorMessage.message
            }
            addSongToPlaylistStatus.postValue(result is NetworkResult.Success)
        }
        registerEventParentJobFinish()
    }


}

