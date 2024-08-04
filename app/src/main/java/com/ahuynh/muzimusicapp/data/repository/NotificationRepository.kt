package com.ahuynh.muzimusicapp.data.repository

import com.ahuynh.muzimusicapp.data.model.Album
import com.ahuynh.muzimusicapp.data.model.Song
import com.ahuynh.muzimusicapp.data.model.request.UpdateAlbumRequest
import com.ahuynh.muzimusicapp.data.model.response.AlbumResponseData
import com.ahuynh.muzimusicapp.data.model.response.MessageResponse
import com.ahuynh.muzimusicapp.data.model.response.NotificationResponseCount
import com.ahuynh.muzimusicapp.data.model.response.NotificationResponseData
import com.ahuynh.muzimusicapp.data.model.response.NotificationResponseDataList
import com.ahuynh.muzimusicapp.data.service.remote.AlbumRemoteService
import com.ahuynh.muzimusicapp.data.service.remote.NotificationRemoteService
import com.ahuynh.muzimusicapp.di.IoDispatcher
import com.ahuynh.muzimusicapp.ui.base.bottom_sheet.SortName
import com.ahuynh.muzimusicapp.utils.Response
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import javax.inject.Inject

class NotificationRepository @Inject constructor(
    private val notificationRemoteService: NotificationRemoteService,
    @IoDispatcher private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {

    suspend fun getAllNotifications(): Response<NotificationResponseDataList> {
        return withContext(dispatcher) {
            notificationRemoteService.getAllNotifications()
        }
    }

    suspend fun getNotificationById(id: Long): Response<NotificationResponseData> {
        return withContext(dispatcher) {
            notificationRemoteService.getNotificationById(id)
        }
    }

    suspend fun markNotificationAsRead(id: Long): Response<NotificationResponseData> {
        return withContext(dispatcher) {
            notificationRemoteService.markNotificationAsRead(id)
        }
    }

    suspend fun markAllNotificationsAsRead(): Response<NotificationResponseDataList> {
        return withContext(dispatcher) {
            notificationRemoteService.markAllNotificationsAsRead()
        }
    }

    suspend fun deleteNotification(id: Long): Response<MessageResponse> {
        return withContext(dispatcher) {
            notificationRemoteService.deleteNotification(id)
        }
    }

    suspend fun deleteAllNotifications(): Response<MessageResponse> {
        return withContext(dispatcher) {
            notificationRemoteService.deleteAllNotifications()
        }
    }

    suspend fun countUnreadNotification(): Response<NotificationResponseCount> {
        return withContext(dispatcher) {
            notificationRemoteService.countUnreadNotification()
        }
    }


}