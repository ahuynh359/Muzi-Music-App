package com.ahuynh.muzimusicapp.ui.component.user.singer.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ahuynh.muzimusicapp.data.model.Song
import com.ahuynh.muzimusicapp.data.repository.SingerRepository
import com.ahuynh.muzimusicapp.ui.base.viewmodel.BaseViewModel
import com.ahuynh.muzimusicapp.utils.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailSingerViewModel @Inject constructor(
    private val singerRepository: SingerRepository
) : BaseViewModel() {


    var songOfSinger = MutableLiveData<List<Song>>()
    var loveSinger = MutableLiveData<Boolean>()
    var mess : String ?= null




    fun isUserLoveSinger(id: Long) {
        isLoading.postValue(true)
        parentJob = viewModelScope.launch {
            val result = singerRepository.isUserLoveSinger(
                id
            )
            if (result is Response.Success) {
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
            val result = singerRepository.loveOrUnloveSinger(
                id
            )
            if(result is Response.Success){
                isUserLoveSinger(id)
            } else if(result is Response.Failure){
                mess = result.errorMessage
            }


        }
        registerEventParentJobFinish()
    }

}

