package com.ahuynh.muzimusicapp.data.api

import com.ahuynh.muzimusicapp.data.model.request.PlaylistRequest
import com.ahuynh.muzimusicapp.data.model.response.ApiResponse
import com.ahuynh.muzimusicapp.data.model.response.ListPlaylistResponse
import com.ahuynh.muzimusicapp.data.model.response.ListSongResponse
import com.ahuynh.muzimusicapp.data.model.response.MessageResponse
import com.ahuynh.muzimusicapp.data.model.response.PlaylistResponseData
import com.ahuynh.muzimusicapp.utils.Constants
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface PlaylistAPI {


    @GET("${Constants.API_VERSION}/playlist/all/{id}")
    suspend fun getAllPlaylist(@Path("id") id: Long):
            Response<ListPlaylistResponse>


    @GET("${Constants.API_VERSION}/playlist/{id}/get/song")
    suspend fun getAllSongFromPlaylist(@Path("id") id: Long):
            Response<ListSongResponse>

    @POST("${Constants.API_VERSION}/playlist/create")
    suspend fun addPlaylist(@Body playlistRequest: PlaylistRequest): Response<PlaylistResponseData>

    @DELETE("${Constants.API_VERSION}/playlist/{id}")
    suspend fun deletePlaylist(@Path("id") id: Long): Response<MessageResponse>


}