package com.ahuynh.muzimusicapp.data.api

import com.ahuynh.muzimusicapp.data.model.response.IsLoveSongResponse
import com.ahuynh.muzimusicapp.data.model.response.SingerResponseData
import com.ahuynh.muzimusicapp.data.model.response.SongResponseData
import com.ahuynh.muzimusicapp.data.model.response.LoveSongResponse
import com.ahuynh.muzimusicapp.data.model.response.MessageResponse
import com.ahuynh.muzimusicapp.data.model.response.ListSearchResponse
import com.ahuynh.muzimusicapp.data.model.response.SongResponse
import com.ahuynh.muzimusicapp.utils.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface SongAPI {

    @GET("${Constants.API_VERSION}/song/new")
    suspend fun getNewSongs(): Response<SongResponseData>

    @GET("${Constants.API_VERSION}/song/{id}")
    suspend fun getSongById(@Path("id") id: Long): Response<SongResponse>


    @GET("${Constants.API_VERSION}/song/search")
    suspend fun searchSong(@Query("query")  query : String): Response<ListSearchResponse>

    @PUT("${Constants.API_VERSION}/song/love-or-unlove/{songId}")
    suspend fun loveSong(@Path("songId") songId : Long) : Response<MessageResponse>

    @GET("${Constants.API_VERSION}/song/is-love-song/{songId}")
    suspend fun isUserLoveSong(@Path("songId") songId : Long): Response<IsLoveSongResponse>

    @GET("${Constants.API_VERSION}/song/love")
    suspend fun getLoveSong(): Response<LoveSongResponse>
}