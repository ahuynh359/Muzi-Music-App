package com.ahuynh.muzimusicapp.data.api

import com.ahuynh.muzimusicapp.data.model.response.ListSongResponse
import com.ahuynh.muzimusicapp.data.model.response.ListUserResponse
import com.ahuynh.muzimusicapp.data.model.response.SearchResponse
import com.ahuynh.muzimusicapp.data.model.response.SongResponse
import com.ahuynh.muzimusicapp.utils.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface SongAPI {
    //ok
    @GET("${Constants.API_VERSION}/core-service/song/all")
    suspend fun getAllSong(): Response<ListSongResponse>

    @GET("${Constants.API_VERSION}/song/{id}")
    suspend fun getSongById(@Path("id") id: Long): Response<SongResponse>




    @GET("${Constants.API_VERSION}/song/singer/{id}")
    suspend fun getSingerFromSongById(@Path("id") id: Long): Response<ListUserResponse>

    @GET("${Constants.API_VERSION}/core-service/song/search")
    suspend fun searchSong(@Query("str") str: String): Response<SearchResponse>


}