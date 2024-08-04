package com.ahuynh.muzimusicapp.data.api

import com.ahuynh.muzimusicapp.data.model.response.MessageResponse
import com.ahuynh.muzimusicapp.data.model.response.NotificationResponseCount
import com.ahuynh.muzimusicapp.data.model.response.NotificationResponseData
import com.ahuynh.muzimusicapp.data.model.response.NotificationResponseDataList
import com.ahuynh.muzimusicapp.utils.Constants
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

interface NotificationAPI {


    @GET("${Constants.API_VERSION}/notification/all")
    suspend fun getAllNotifications(): Response<NotificationResponseDataList>

    @GET("${Constants.API_VERSION}/notification/{id}")
    suspend fun getNotificationById(
        @Path("id") id: Long
    ): Response<NotificationResponseData>

    @PUT("${Constants.API_VERSION}/notification/{id}/read")
    suspend fun markNotificationAsRead(
        @Path("id") id: Long
    ): Response<NotificationResponseData>

    @PUT("${Constants.API_VERSION}/notification/read-all")
    suspend fun markAllNotificationsAsRead(
    ): Response<NotificationResponseDataList>

    @DELETE("${Constants.API_VERSION}/notification/{id}")
    suspend fun deleteNotification(
        @Path("id") id: Long
    ): Response<MessageResponse>

    @DELETE("${Constants.API_VERSION}/notification")
    suspend fun deleteAllNotification(
    ): Response<MessageResponse>

    @GET("${Constants.API_VERSION}/notification/count")
    suspend fun countUnreadNotification(
    ): Response<NotificationResponseCount>



}