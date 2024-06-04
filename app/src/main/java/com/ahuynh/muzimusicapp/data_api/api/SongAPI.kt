package com.ahuynh.muzimusicapp.data_api.api

import com.ahuynh.muzimusicapp.data_api.model.response.AlbumResponse
import com.ahuynh.muzimusicapp.data_api.model.response.ListAlbumResponse
import com.ahuynh.muzimusicapp.data_api.model.response.ListSongResponse
import com.ahuynh.muzimusicapp.data_api.model.response.ListUserResponse
import com.ahuynh.muzimusicapp.data_api.model.response.SongResponse
import com.ahuynh.muzimusicapp.utils.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface SongAPI {
    @GET("${Constants.API_VERSION}/song/{id}")
    suspend fun getSongById(@Path("id") id: Long): Response<SongResponse>

    @GET("${Constants.API_VERSION}/song")
    suspend fun getAllSong(): Response<ListSongResponse>


    @GET("${Constants.API_VERSION}/song/singer/{id}")
    suspend fun getSingerFromSongById(@Path("id") id: Long): Response<ListUserResponse>



}