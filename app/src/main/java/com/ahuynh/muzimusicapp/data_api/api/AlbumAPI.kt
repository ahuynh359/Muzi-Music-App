package com.ahuynh.muzimusicapp.data_api.api

import com.ahuynh.muzimusicapp.data.model.SongOld
import com.ahuynh.muzimusicapp.data_api.model.response.AlbumResponse
import com.ahuynh.muzimusicapp.data_api.model.response.ListAlbumResponse
import com.ahuynh.muzimusicapp.data_api.model.response.ListSongResponse
import com.ahuynh.muzimusicapp.utils.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface AlbumAPI {
    @GET("${Constants.API_VERSION}/album/{id}")
    suspend fun getAlbumById(@Path("id") id: Long): Response<AlbumResponse>

    @GET("${Constants.API_VERSION}/album")
    suspend fun getAllAlbum(): Response<ListAlbumResponse>

    @GET("${Constants.API_VERSION}/album/songs/{id}")
    suspend fun getSongsFromAlbum(@Path("id") id: Long): Response<ListSongResponse>


}