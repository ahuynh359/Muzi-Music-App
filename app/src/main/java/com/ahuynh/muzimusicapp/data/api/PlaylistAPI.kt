package com.ahuynh.muzimusicapp.data.api

import com.ahuynh.muzimusicapp.data.model.request.LoginRequest
import com.ahuynh.muzimusicapp.data.model.request.PlaylistRequest
import com.ahuynh.muzimusicapp.data.model.response.AlbumResponse
import com.ahuynh.muzimusicapp.data.model.response.ListAlbumResponse
import com.ahuynh.muzimusicapp.data.model.response.ListPlaylistResponse
import com.ahuynh.muzimusicapp.data.model.response.ListSongResponse
import com.ahuynh.muzimusicapp.data.model.response.PlaylistResponse
import com.ahuynh.muzimusicapp.utils.Constants
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface PlaylistAPI {


    @POST("${Constants.API_VERSION}/playlist")
    suspend fun getAllPlaylist(@Body playlistRequest: PlaylistRequest):
            Response<ListPlaylistResponse>



}