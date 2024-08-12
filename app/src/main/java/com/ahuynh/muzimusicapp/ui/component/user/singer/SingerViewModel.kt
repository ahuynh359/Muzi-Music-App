package com.ahuynh.muzimusicapp.ui.component.user.singer

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ahuynh.muzimusicapp.data.model.Singer
import com.ahuynh.muzimusicapp.data.model.Song
import com.ahuynh.muzimusicapp.data.repository.SingerRepository
import com.ahuynh.muzimusicapp.ui.base.viewmodel.BaseViewModel
import com.ahuynh.muzimusicapp.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SingerViewModel @Inject constructor(
    private val singerRepository: SingerRepository
) : BaseViewModel() {

    var singerList = MutableLiveData<List<Singer>>()
    var singer = MutableLiveData<Singer>()


    var songOfSinger = MutableLiveData<List<Song>>()
    var loveSinger = MutableLiveData<Boolean>()
    var mess: String? = null


    fun isUserLoveSinger(id: Long) {
        isLoading.postValue(true)
        parentJob = viewModelScope.launch {
            val result = singerRepository.isUserLoveSinger(
                id
            )
            if (result is NetworkResult.Success) {
                loveSinger.postValue(
                    result.data.data
                )
            }

        }
        registerEventParentJobFinish()
    }


    fun getSongOfSinger(id: Long) {
        isLoading.postValue(true)
        parentJob = viewModelScope.launch {
            songOfSinger.postValue(singerRepository.getSongsOfSinger(id))
        }
        registerEventParentJobFinish()
    }

    fun loveOrUnloveSinger(id: Long) {
        isLoading.postValue(true)
        parentJob = viewModelScope.launch {
            val result = singerRepository.loveOrUnloveSinger(id)
            if (result is NetworkResult.Success) {
                isUserLoveSinger(id)
            } else if (result is NetworkResult.Failure) {
                mess = result.errorMessage.message
            }


        }
        registerEventParentJobFinish()
    }


    fun getAllSinger() {
        isLoading.postValue(true)
        parentJob = viewModelScope.launch {
            singerList.postValue(singerRepository.getLoveSinger())
        }
        registerEventParentJobFinish()
    }



}

