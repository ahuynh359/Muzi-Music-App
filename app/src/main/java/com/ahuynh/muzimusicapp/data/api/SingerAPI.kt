package com.ahuynh.muzimusicapp.data.api

import com.ahuynh.muzimusicapp.data.model.response.SingerResponseData
import com.ahuynh.muzimusicapp.data.model.response.SongResponseData
import com.ahuynh.muzimusicapp.data.model.response.ListSearchResponse
import com.ahuynh.muzimusicapp.data.model.response.LoveSingerResponse
import com.ahuynh.muzimusicapp.data.model.response.LoveSongResponse
import com.ahuynh.muzimusicapp.data.model.response.MessageResponse
import com.ahuynh.muzimusicapp.data.model.response.SongResponse
import com.ahuynh.muzimusicapp.utils.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query


interface SingerAPI {

    @GET("${Constants.API_VERSION}/singer/new")
    suspend fun getNewSingers(): Response<SingerResponseData>

    @GET("${Constants.API_VERSION}/singer/{id}/songs")
    suspend fun getSongsOfSinger(@Path("id") id: Long): Response<SongResponseData>

    @GET("${Constants.API_VERSION}/singer/love")
    suspend fun getLoveSinger(): Response<SingerResponseData>


    @GET("${Constants.API_VERSION}/singer/love/{id}")
    suspend fun isUserLoveSinger(@Path("id") id: Long): Response<LoveSingerResponse>

    @POST("${Constants.API_VERSION}/singer/love-or-unlove/{id}")
    suspend fun loveOrUnloveSinger(@Path("id") id: Long): Response<MessageResponse>


}