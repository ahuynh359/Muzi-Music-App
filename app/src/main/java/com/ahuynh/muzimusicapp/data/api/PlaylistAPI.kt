package com.ahuynh.muzimusicapp.data.api

import com.ahuynh.muzimusicapp.data.model.request.PlaylistRequest
import com.ahuynh.muzimusicapp.data.model.response.SongResponseData
import com.ahuynh.muzimusicapp.data.model.response.MessageResponse
import com.ahuynh.muzimusicapp.data.model.response.PlaylistResponse
import com.ahuynh.muzimusicapp.data.model.response.PlaylistResponseData
import com.ahuynh.muzimusicapp.data.model.response.PlaylistResponseJson
import com.ahuynh.muzimusicapp.utils.Constants
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface PlaylistAPI {


    @GET("${Constants.API_VERSION}/playlist/all")
    suspend fun getAllPlaylist():
            Response<PlaylistResponseData>


    @GET("${Constants.API_VERSION}/playlist/songs/{id}")
    suspend fun getAllSongFromPlaylist(@Path("id") id: Long):
            Response<SongResponseData>

    @POST("${Constants.API_VERSION}/playlist/create")
    suspend fun addPlaylist(@Body playlistRequest: PlaylistRequest): Response<PlaylistResponseJson>

    @DELETE("${Constants.API_VERSION}/playlist/{id}")
    suspend fun deletePlaylist(@Path("id") id: Long): Response<MessageResponse>

    @PUT("${Constants.API_VERSION}/playlist/{id}")
    suspend fun updatePlaylist(
        @Body playlistRequest: PlaylistRequest,
        @Path("id") id: Long
    ): Response<PlaylistResponseJson>

    @POST("${Constants.API_VERSION}/playlist/{playlistId}/song/{songId}")
    suspend fun addSongToPlaylist(@Path("playlistId") playlistId: Long, @Path("songId") songId: Long): Response<MessageResponse>


    @GET("${Constants.API_VERSION}/playlist/songs/not/{id}")
    suspend fun getAllSongsNotFromPlaylist(@Path("id") id: Long):
            Response<SongResponseData>


}