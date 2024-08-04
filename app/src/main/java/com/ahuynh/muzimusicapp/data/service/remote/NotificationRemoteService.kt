package com.ahuynh.muzimusicapp.data.service.remote


import com.ahuynh.muzimusicapp.data.api.NotificationAPI
import com.ahuynh.muzimusicapp.data.model.response.*
import com.ahuynh.muzimusicapp.data.service.base.BaseRemoteService
import com.ahuynh.muzimusicapp.utils.Response
import javax.inject.Inject

class NotificationRemoteService @Inject constructor(
    private val notificationAPI: NotificationAPI
) : BaseRemoteService() {

    suspend fun getAllNotifications(): Response<NotificationResponseDataList> {
        return callApi { notificationAPI.getAllNotifications() }
    }

    suspend fun getNotificationById(id: Long): Response<NotificationResponseData> {
        return callApi { notificationAPI.getNotificationById(id) }
    }

    suspend fun markNotificationAsRead(id: Long): Response<NotificationResponseData> {
        return callApi { notificationAPI.markNotificationAsRead(id) }
    }

    suspend fun markAllNotificationsAsRead(): Response<NotificationResponseDataList> {
        return callApi { notificationAPI.markAllNotificationsAsRead() }
    }

    suspend fun deleteNotification(id: Long): Response<MessageResponse> {
        return callApi { notificationAPI.deleteNotification(id) }
    }

    suspend fun deleteAllNotifications(): Response<MessageResponse> {
        return callApi { notificationAPI.deleteAllNotification() }
    }

    suspend fun countUnreadNotification(): Response<NotificationResponseCount> {
        return callApi { notificationAPI.countUnreadNotification() }
    }
}