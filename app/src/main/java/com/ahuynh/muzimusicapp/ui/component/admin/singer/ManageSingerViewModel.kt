package com.ahuynh.muzimusicapp.ui.component.admin.singer

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ahuynh.muzimusicapp.data.model.Singer
import com.ahuynh.muzimusicapp.data.model.request.UpdateSingerRequest
import com.ahuynh.muzimusicapp.data.repository.SingerRepository
import com.ahuynh.muzimusicapp.ui.base.bottom_sheet.SortName
import com.ahuynh.muzimusicapp.ui.base.viewmodel.BaseViewModel
import com.ahuynh.muzimusicapp.utils.NetworkResult
import com.ahuynh.muzimusicapp.utils.helper.SharePreferencesHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class ManageSingerViewModel @Inject constructor(
    private val singerRepository: SingerRepository,
    private val sharePreferencesHelper: SharePreferencesHelper
) : BaseViewModel() {
    var deleteSingerStatus = MutableLiveData<Boolean?>()
    var createSingerStatus = MutableLiveData<Boolean?>()
    var updateSingerStatus = MutableLiveData<Boolean?>()
    var singerList = MutableLiveData<List<Singer>>()
    var mess: String? = null
    var avatar = MutableLiveData<String>()
    var singer = MutableLiveData<Singer>()
    var sortSinger = MutableLiveData<SortName>()

    init {
        getSortSinger()
    }

    fun getSortSinger() {
        viewModelScope.launch {
            sortSinger.postValue(sharePreferencesHelper.isSortSinger())
        }
    }

    fun setSortSinger(sortName: SortName) {
        viewModelScope.launch {
            sortSinger.postValue(sortName)
            sharePreferencesHelper.setSortSinger(sortName)
        }
    }


    fun getAllSingers() {
        isLoading.postValue(true)
        parentJob = viewModelScope.launch {
            singerList.postValue(singerRepository.getAllSingers(sharePreferencesHelper.isSortSinger()))

        }
        registerEventParentJobFinish()
    }

    fun createSinger(name: String,description : String, avatar: File) {
        isLoading.postValue(true)
        parentJob = viewModelScope.launch {
            val result = singerRepository.createSinger(name,description, avatar)
            if (result is NetworkResult.Success) {
                mess = result.data.message

            } else if (result is NetworkResult.Failure) {
                mess = result.errorMessage.message
            }
            createSingerStatus.postValue(result is NetworkResult.Success)
        }
        registerEventParentJobFinish()
    }

    fun getSingerById(id: Long) {
        isLoading.postValue(true)
        parentJob = viewModelScope.launch {
            val result = singerRepository.getSingerById(id)
            result?.let {
                singer.postValue(it)
            }

        }
        registerEventParentJobFinish()
    }

    fun updateSinger(id: Long, str: String) {
        isLoading.postValue(true)
        parentJob = viewModelScope.launch {
            val updateSingerRequest = UpdateSingerRequest(id, str)
            val result = singerRepository.updateSinger(updateSingerRequest)
            if (result is NetworkResult.Success) {
                mess = result.data.message

            } else if (result is NetworkResult.Failure) {
                mess = result.errorMessage.message
            }
            updateSingerStatus.postValue(result is NetworkResult.Success)
        }
        registerEventParentJobFinish()
    }

    fun deleteSinger(id: Long) {
        isLoading.postValue(true)
        parentJob = viewModelScope.launch {
            val result = singerRepository.deleteSinger(id)
            if (result is NetworkResult.Success) {
                mess = result.data.message

            } else if (result is NetworkResult.Failure) {
                mess = result.errorMessage.message
            }
            deleteSingerStatus.postValue(result is NetworkResult.Success)
        }

        registerEventParentJobFinish()
    }

    fun changeAvatar(id: Long, file: File) {
        isLoading.postValue(true)
        parentJob = viewModelScope.launch {
            val result = singerRepository.changeAvatar(id, file)
            if (result is NetworkResult.Success) {
                avatar.postValue(result.data.data.avatar)
            } else if (result is NetworkResult.Failure) {
                mess = result.errorMessage.message

            }
            registerEventParentJobFinish()


        }

    }


}
