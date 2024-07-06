package com.ahuynh.muzimusicapp.ui.component.user.singer

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ahuynh.muzimusicapp.data.model.Singer
import com.ahuynh.muzimusicapp.data.repository.SingerRepository
import com.ahuynh.muzimusicapp.ui.base.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SingerViewModel @Inject constructor(
    private val singerRepository: SingerRepository
) : BaseViewModel() {


    var singerList = MutableLiveData<List<Singer>>()






    fun getAllSinger() {
        isLoading.postValue(true)
        parentJob = viewModelScope.launch {
            singerList.postValue(singerRepository.getLoveSinger())
        }
        registerEventParentJobFinish()
    }


}

