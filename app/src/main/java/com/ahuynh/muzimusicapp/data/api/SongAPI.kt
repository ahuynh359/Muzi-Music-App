package com.ahuynh.muzimusicapp.data.api

import com.ahuynh.muzimusicapp.data.model.response.ListSingerResponse
import com.ahuynh.muzimusicapp.data.model.response.ListSongResponse
import com.ahuynh.muzimusicapp.data.model.response.LoveSongResponse
import com.ahuynh.muzimusicapp.data.model.response.MessageResponse
import com.ahuynh.muzimusicapp.data.model.response.SearchResponse
import com.ahuynh.muzimusicapp.data.model.response.SongResponse
import com.ahuynh.muzimusicapp.utils.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface SongAPI {

    @GET("${Constants.API_VERSION}/song/new")
    suspend fun getNewSongs(): Response<ListSongResponse>

    @GET("${Constants.API_VERSION}/song/{id}")
    suspend fun getSongById(@Path("id") id: Long): Response<SongResponse>




    @GET("${Constants.API_VERSION}/song/singer/{id}")
    suspend fun getSingerFromSongById(@Path("id") id: Long): Response<ListSingerResponse>

    @GET("${Constants.API_VERSION}/song/search")
    suspend fun searchSong(@Query("query")  query : String): Response<SearchResponse>

    @POST("${Constants.API_VERSION}/song/love/{userId}/{songId}")
    suspend fun loveSong(@Path("userId") userId : Long ,@Path("songId") songId : Long) : Response<MessageResponse>

    @POST("${Constants.API_VERSION}/song/unlove/{userId}/{songId}")
    suspend fun unloveSong(@Path("userId") userId : Long ,@Path("songId") songId : Long) : Response<MessageResponse>

    @GET("${Constants.API_VERSION}/song/is-user-love-song/{userId}/{songId}")
    suspend fun isUserLoveSong(@Path("userId") userId : Long ,@Path("songId") songId : Long): Response<LoveSongResponse>
}