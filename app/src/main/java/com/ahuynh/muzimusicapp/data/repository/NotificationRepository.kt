package com.ahuynh.muzimusicapp.data.repository

import com.ahuynh.muzimusicapp.data.model.response.MessageResponse
import com.ahuynh.muzimusicapp.data.model.response.NotificationResponseCount
import com.ahuynh.muzimusicapp.data.model.response.NotificationResponseData
import com.ahuynh.muzimusicapp.data.model.response.NotificationResponseDataList
import com.ahuynh.muzimusicapp.data.service.remote.NotificationRemoteService
import com.ahuynh.muzimusicapp.di.IoDispatcher
import com.ahuynh.muzimusicapp.utils.NetworkResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class NotificationRepository @Inject constructor(
    private val notificationRemoteService: NotificationRemoteService,
    @IoDispatcher private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {

    suspend fun getAllNotifications(): NetworkResult<NotificationResponseDataList> {
        return withContext(dispatcher) {
            notificationRemoteService.getAllNotifications()
        }
    }

    suspend fun getNotificationById(id: Long): NetworkResult<NotificationResponseData> {
        return withContext(dispatcher) {
            notificationRemoteService.getNotificationById(id)
        }
    }

    suspend fun markNotificationAsRead(id: Long): NetworkResult<NotificationResponseData> {
        return withContext(dispatcher) {
            notificationRemoteService.markNotificationAsRead(id)
        }
    }

    suspend fun markAllNotificationsAsRead(): NetworkResult<NotificationResponseDataList> {
        return withContext(dispatcher) {
            notificationRemoteService.markAllNotificationsAsRead()
        }
    }

    suspend fun deleteNotification(id: Long): NetworkResult<MessageResponse> {
        return withContext(dispatcher) {
            notificationRemoteService.deleteNotification(id)
        }
    }

    suspend fun deleteAllNotifications(): NetworkResult<MessageResponse> {
        return withContext(dispatcher) {
            notificationRemoteService.deleteAllNotifications()
        }
    }

    suspend fun countUnreadNotification(): NetworkResult<NotificationResponseCount> {
        return withContext(dispatcher) {
            notificationRemoteService.countUnreadNotification()
        }
    }


}