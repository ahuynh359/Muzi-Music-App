package com.ahuynh.muzimusicapp.data.service.remote


import com.ahuynh.muzimusicapp.data.api.NotificationAPI
import com.ahuynh.muzimusicapp.data.model.response.MessageResponse
import com.ahuynh.muzimusicapp.data.model.response.NotificationResponseCount
import com.ahuynh.muzimusicapp.data.model.response.NotificationResponseData
import com.ahuynh.muzimusicapp.data.model.response.NotificationResponseDataList
import com.ahuynh.muzimusicapp.data.service.base.BaseRemoteService
import com.ahuynh.muzimusicapp.utils.NetworkResult
import javax.inject.Inject

class NotificationRemoteService @Inject constructor(
    private val notificationAPI: NotificationAPI
) : BaseRemoteService() {

    suspend fun getAllNotifications(): NetworkResult<NotificationResponseDataList> {
        return callApi { notificationAPI.getAllNotifications() }
    }

    suspend fun getNotificationById(id: Long): NetworkResult<NotificationResponseData> {
        return callApi { notificationAPI.getNotificationById(id) }
    }

    suspend fun markNotificationAsRead(id: Long): NetworkResult<NotificationResponseData> {
        return callApi { notificationAPI.markNotificationAsRead(id) }
    }

    suspend fun markAllNotificationsAsRead(): NetworkResult<NotificationResponseDataList> {
        return callApi { notificationAPI.markAllNotificationsAsRead() }
    }

    suspend fun deleteNotification(id: Long): NetworkResult<MessageResponse> {
        return callApi { notificationAPI.deleteNotification(id) }
    }

    suspend fun deleteAllNotifications(): NetworkResult<MessageResponse> {
        return callApi { notificationAPI.deleteAllNotification() }
    }

    suspend fun countUnreadNotification(): NetworkResult<NotificationResponseCount> {
        return callApi { notificationAPI.countUnreadNotification() }
    }
}