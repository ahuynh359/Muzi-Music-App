package com.ahuynh.muzimusicapp.data.api

import com.ahuynh.muzimusicapp.data.model.response.ApiResponse
import com.ahuynh.muzimusicapp.data.model.response.CheckLoveSongResponse
import com.ahuynh.muzimusicapp.data.model.response.ListSongResponse
import com.ahuynh.muzimusicapp.utils.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path

interface UserAPI {
    @GET("${Constants.API_VERSION}/user/{id}/is-love-song/{songId}")
    suspend fun isUserLoveSong(
        @Path("id") id: Long,
        @Path("songId") songId: Long
    ): Response<CheckLoveSongResponse>

    @Multipart
    @PUT("${Constants.API_VERSION}/user/love-or-unlove")
    suspend fun loveOrUnlove(
        @Part("userId") userId: Long,
        @Part("songId") songId: Long
    ): Response<ApiResponse>

    @GET("${Constants.API_VERSION}/user/{id}/get-love-song")
    suspend fun getLoveSong(
        @Path("id") id: Long,
    ): Response<ListSongResponse>


}