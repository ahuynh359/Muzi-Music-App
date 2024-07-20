package com.ahuynh.muzimusicapp.ui.component.admin.singer

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ahuynh.muzimusicapp.data.model.Singer
import com.ahuynh.muzimusicapp.data.model.Type
import com.ahuynh.muzimusicapp.data.model.User
import com.ahuynh.muzimusicapp.data.model.request.UpdateSingerRequest
import com.ahuynh.muzimusicapp.data.model.request.UpdateTypeRequest
import com.ahuynh.muzimusicapp.data.repository.SingerRepository
import com.ahuynh.muzimusicapp.data.repository.TypeRepository
import com.ahuynh.muzimusicapp.data.repository.UserRepository
import com.ahuynh.muzimusicapp.ui.base.bottom_sheet.SortName
import com.ahuynh.muzimusicapp.ui.base.viewmodel.BaseViewModel
import com.ahuynh.muzimusicapp.utils.Response
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

    fun createSinger(name: String, avatar: File) {
        isLoading.postValue(true)
        parentJob = viewModelScope.launch {
            val result = singerRepository.createSinger(name, avatar)
            if (result is Response.Success) {
                mess = result.data.message

            } else if (result is Response.Failure) {
                mess = result.errorMessage
            }
            createSingerStatus.postValue(result is Response.Success)
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
            if (result is Response.Success) {
                mess = result.data.message

            } else if (result is Response.Failure) {
                mess = result.errorMessage
            }
            updateSingerStatus.postValue(result is Response.Success)
        }
        registerEventParentJobFinish()
    }

    fun deleteSinger(id: Long) {
        isLoading.postValue(true)
        parentJob = viewModelScope.launch {
            val result = singerRepository.deleteSinger(id)
            if (result is Response.Success) {
                mess = result.data.message

            } else if (result is Response.Failure) {
                mess = result.errorMessage
            }
            deleteSingerStatus.postValue(result is Response.Success)
        }

        registerEventParentJobFinish()
    }

    fun changeAvatar(id: Long, file: File) {
        isLoading.postValue(true)
        parentJob = viewModelScope.launch {
            val result = singerRepository.changeAvatar(id, file)
            if (result is Response.Success) {
                avatar.postValue(result.data.data.avatar)
            } else if (result is Response.Failure) {
                mess = result.errorMessage

            }
            registerEventParentJobFinish()


        }

    }


}
