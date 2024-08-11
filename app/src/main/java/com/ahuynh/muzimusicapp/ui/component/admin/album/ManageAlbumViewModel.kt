package com.ahuynh.muzimusicapp.ui.component.admin.album

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ahuynh.muzimusicapp.data.model.Album
import com.ahuynh.muzimusicapp.data.model.User
import com.ahuynh.muzimusicapp.data.model.request.UpdateAlbumRequest
import com.ahuynh.muzimusicapp.data.repository.AlbumRepository
import com.ahuynh.muzimusicapp.data.repository.UserRepository
import com.ahuynh.muzimusicapp.ui.base.bottom_sheet.SortName
import com.ahuynh.muzimusicapp.ui.base.viewmodel.BaseViewModel
import com.ahuynh.muzimusicapp.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class ManageAlbumViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val albumRepository: AlbumRepository
) : BaseViewModel() {
    val sortOrder = MutableLiveData<String>("Sort")
    var userList = MutableLiveData<List<User>>()
    var deleteAlbumStatus = MutableLiveData<Boolean?>()
    var createAlbumStatus = MutableLiveData<Boolean?>()
    var updateAlbumStatus = MutableLiveData<Boolean?>()
    var user = MutableLiveData<User>()
    var mess: String? = null
    var avatar = MutableLiveData<String>()
    var albumList = MutableLiveData<List<Album>>()
    var album = MutableLiveData<Album>()

    fun getNewAlbums() {
        isLoading.postValue(true)
        parentJob = viewModelScope.launch {
            albumList.postValue(albumRepository.getAllAlbums(SortName.NEW))

        }
        registerEventParentJobFinish()
    }

    fun createAlbum(name: String, avatar: File) {
        isLoading.postValue(true)
        parentJob = viewModelScope.launch {
            val result = albumRepository.createAlbum(name, avatar)
            if (result is NetworkResult.Success) {
                mess = result.data.message

            } else if (result is NetworkResult.Failure) {
                mess = result.errorMessage.message
            }
            createAlbumStatus.postValue(result is NetworkResult.Success)
        }
        registerEventParentJobFinish()
    }

    fun getAlbumById(id: Long) {
        isLoading.postValue(true)
        parentJob = viewModelScope.launch {
            val result = albumRepository.getAlbumById(id)
            result?.let {
                album.postValue(it)
            }

        }
        registerEventParentJobFinish()
    }

    fun updateAlbum(id: Long, str: String) {
        isLoading.postValue(true)
        parentJob = viewModelScope.launch {
            val updateAlbumRequest = UpdateAlbumRequest(id, str)
            val result = albumRepository.updateAlbum(updateAlbumRequest)
            if (result is NetworkResult.Success) {
                mess = result.data.message

            } else if (result is NetworkResult.Failure) {
                mess = result.errorMessage.message
            }
            updateAlbumStatus.postValue(result is NetworkResult.Success)
        }
        registerEventParentJobFinish()
    }

    fun updateAvatar(id: Long, file: File) {
        isLoading.postValue(true)
        parentJob = viewModelScope.launch {
            val result = albumRepository.updateAvatar(id, file)
            if (result is NetworkResult.Success) {
                mess = result.data.message
                avatar.postValue(result.data.data.avatar)

            } else if (result is NetworkResult.Failure) {
                mess = result.errorMessage.message
            }
        }
        registerEventParentJobFinish()
    }

    fun deleteAlbum(id: Long) {
        isLoading.postValue(true)
        parentJob = viewModelScope.launch {
            val result = albumRepository.deleteAlbum(id)
            if (result is NetworkResult.Success) {
                mess = result.data.message

            } else if (result is NetworkResult.Failure) {
                mess = result.errorMessage.message
            }
            deleteAlbumStatus.postValue(result is NetworkResult.Success)
        }

        registerEventParentJobFinish()
    }


}
