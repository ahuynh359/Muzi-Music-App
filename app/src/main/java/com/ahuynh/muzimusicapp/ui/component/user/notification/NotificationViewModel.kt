package com.ahuynh.muzimusicapp.ui.component.user.notification

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ahuynh.muzimusicapp.data.model.Notification
import com.ahuynh.muzimusicapp.data.model.Singer
import com.ahuynh.muzimusicapp.data.model.Song
import com.ahuynh.muzimusicapp.data.model.response.toListNotification
import com.ahuynh.muzimusicapp.data.repository.NotificationRepository
import com.ahuynh.muzimusicapp.data.repository.SingerRepository
import com.ahuynh.muzimusicapp.ui.base.viewmodel.BaseViewModel
import com.ahuynh.muzimusicapp.utils.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotificationViewModel @Inject constructor(
    private val notificationRepository: NotificationRepository
) : BaseViewModel() {

    val markNotificationAsReadStatus = MutableLiveData<Boolean?>()
    val deleteNotificationStatus = MutableLiveData<Boolean?>()
    var notificationList = MutableLiveData<List<Notification>>()
    var unreadCount = MutableLiveData<Int>()
    var currentNotification = MutableLiveData<Notification>()
    var mess: String? = null

    fun getAllNotifications() {
        isLoading.postValue(true)
        parentJob = viewModelScope.launch {
            val result = notificationRepository.getAllNotifications()
            if (result is Response.Success) {
                notificationList.postValue(result.data.data.toListNotification())
            } else if (result is Response.Failure) {
                mess = result.errorMessage
            }
        }
        registerEventParentJobFinish()
    }

    fun getNotificationById(id: Long) {
        isLoading.postValue(true)
        parentJob = viewModelScope.launch {
            val result = notificationRepository.getNotificationById(id)
            if (result is Response.Success) {
                currentNotification.postValue(result.data.data.toNotification())
            } else if (result is Response.Failure) {
                mess = result.errorMessage
            }
        }
        registerEventParentJobFinish()
    }

    fun markNotificationAsRead(id: Long) {
        isLoading.postValue(true)
        parentJob = viewModelScope.launch {
            val result = notificationRepository.markNotificationAsRead(id)
            if (result is Response.Success) {
                getAllNotifications()
            } else if (result is Response.Failure) {
                mess = result.errorMessage
            }
            markNotificationAsReadStatus.postValue(result is Response.Success)
        }
        registerEventParentJobFinish()
    }

    fun markAllNotificationsAsRead() {
        isLoading.postValue(true)
        parentJob = viewModelScope.launch {
            val result = notificationRepository.markAllNotificationsAsRead()
            if (result is Response.Success) {
                getAllNotifications()
            } else if (result is Response.Failure) {
                mess = result.errorMessage
            }
        }
        registerEventParentJobFinish()
    }

    fun deleteNotification(id: Long) {
        isLoading.postValue(true)
        parentJob = viewModelScope.launch {
            val result = notificationRepository.deleteNotification(id)
            if (result is Response.Success) {
                getAllNotifications() // Refresh the list after deletion
            } else if (result is Response.Failure) {
                mess = result.errorMessage
            }
            deleteNotificationStatus.postValue(result is Response.Success)
        }
        registerEventParentJobFinish()
    }

    fun deleteAllNotifications() {
        isLoading.postValue(true)
        parentJob = viewModelScope.launch {
            val result = notificationRepository.deleteAllNotifications()
            if (result is Response.Success) {
                Log.d("ABC","Delete ALl")
                getAllNotifications() // Refresh the list after deletion
            } else if (result is Response.Failure) {
                mess = result.errorMessage
            }
        }
        registerEventParentJobFinish()
    }

    fun countUnreadNotifications() {
        isLoading.postValue(true)
        parentJob = viewModelScope.launch {
            val result = notificationRepository.countUnreadNotification()
            if (result is Response.Success) {
                unreadCount.postValue(result.data.data.count)
            } else if (result is Response.Failure) {
                mess = result.errorMessage
            }
        }
        registerEventParentJobFinish()
    }
}