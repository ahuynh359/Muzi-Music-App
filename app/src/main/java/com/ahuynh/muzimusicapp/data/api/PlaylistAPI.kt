package com.ahuynh.muzimusicapp.data.api

import com.ahuynh.muzimusicapp.data.model.request.PlaylistRequest
import com.ahuynh.muzimusicapp.data.model.response.ApiResponse
import com.ahuynh.muzimusicapp.data.model.response.ListPlaylistResponse
import com.ahuynh.muzimusicapp.utils.Constants
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface PlaylistAPI {


    @GET("${Constants.API_VERSION}/playlist/get-all/{userId}")
    suspend fun getAllPlaylist(@Path("userId") userId: Long):
            Response<ListPlaylistResponse>

    @POST("${Constants.API_VERSION}/playlist")
    suspend fun addPlaylist(@Body playlistRequest: PlaylistRequest): Response<ApiResponse>


}