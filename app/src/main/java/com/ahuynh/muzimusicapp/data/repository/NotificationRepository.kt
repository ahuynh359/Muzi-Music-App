package com.ahuynh.muzimusicapp.data.repository

import com.ahuynh.muzimusicapp.data.model.Notification
import com.ahuynh.muzimusicapp.di.IoDispatcher
import com.ahuynh.muzimusicapp.utils.Constants
import com.ahuynh.muzimusicapp.utils.Response
import com.google.firebase.firestore.CollectionReference
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Singleton
class NotificationRepository @Inject constructor(
    @Named(Constants.NOTIFICATION) private val notificationColRef: CollectionReference,
    @IoDispatcher private val dispatcher: CoroutineDispatcher,
) {
    suspend fun getNotification(): Response<Notification> {
        return withContext(dispatcher) {
            try {
                val noti = notificationColRef.get().await().toObjects(Notification::class.java)
                Response.Success(noti[0])
            } catch (e: Exception) {
                Response.Failure(e.message ?: "Unknown error")
            }
        }
    }

    suspend fun updateNotification(newNoti: Notification, documentId: String): Response<Boolean> {
        return withContext(dispatcher) {
            try {
                val documentRef = notificationColRef.document(documentId)

                val updatedData = mapOf(
                    "count" to newNoti.count, "data" to newNoti.data
                )
                documentRef.update(updatedData).await()

                Response.Success(true)

            } catch (e: Exception) {
                Response.Failure(e.message ?: "Unknown error")
            }
        }
    }
}