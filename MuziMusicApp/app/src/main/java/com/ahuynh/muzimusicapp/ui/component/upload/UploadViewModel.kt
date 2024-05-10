package com.ahuynh.muzimusicapp.ui.component.upload

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ahuynh.muzimusicapp.data.model.Notification
import com.ahuynh.muzimusicapp.data.model.SongPost
import com.ahuynh.muzimusicapp.data.repository.NotificationRepository
import com.ahuynh.muzimusicapp.data.repository.SongRepository
import com.ahuynh.muzimusicapp.ui.base.BaseViewModel
import com.ahuynh.muzimusicapp.utils.Response
import com.ahuynh.muzimusicapp.utils.helper.SharePreferencesHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class UploadViewModel @Inject constructor(
    private val repository: SongRepository,
    private val notificationRepository: NotificationRepository,
    private val sharePreferencesHelper: SharePreferencesHelper
) : BaseViewModel() {
    var songName = ""
    var singerName = ""
    var lyrics = ""
    var songFile: File? = null
    var imageFile: File? = null
    var addImage = MutableLiveData<String>()
    var addFileMp3 = MutableLiveData<String>()
    var addSongStatus = MutableLiveData<Boolean>()
    var setNotification = MutableLiveData<Boolean>()
    lateinit var song: SongPost

    fun addImageAndFile() {
        isLoading.postValue(true)
        parentJob = viewModelScope.launch {
            val imageDeferred = async { repository.addImageToFirebaseStorage(imageFile!!) }
            val fileDeferred = async { repository.addFileToFirebaseStorage(songFile!!) }

            val imageResponse = imageDeferred.await()
            val fileResponse = fileDeferred.await()

            if (imageResponse is Response.Success && fileResponse is Response.Success) {
                addImage.postValue(imageResponse.data)
                addFileMp3.postValue(fileResponse.data)

            } else {

                if (imageResponse is Response.Failure) {
                    message.postValue(imageResponse.errorMessage)
                }
                if (fileResponse is Response.Failure) {
                    message.postValue(fileResponse.errorMessage)
                }
            }


        }
        registerEventParentJobFinish()

    }

    fun setUnread() {
        viewModelScope.launch {
            sharePreferencesHelper.setUnreadNoti(sharePreferencesHelper.getUnreadNoti() + 1)
        }
    }

    fun addSong(song: SongPost) {
        isLoading.postValue(true)
        parentJob = viewModelScope.launch {
            val response = repository.addSong(song)
            if (response is Response.Success) {
                addSongStatus.postValue(response.data)
            } else if (response is Response.Failure) {
                message.postValue(response.errorMessage)
            }


        }
        registerEventParentJobFinish()
    }

    fun setUnreadNoti(newNotification: Notification, doc: String) {

        isLoading.postValue(true)
        viewModelScope.launch {
            val response = notificationRepository.updateNotification(newNotification, doc)
            if (response is Response.Success) {
                setNotification.postValue(response.data)
            } else if (response is Response.Failure) {
                message.postValue(response.errorMessage)
            }
        }
        registerEventParentJobFinish()
    }


}