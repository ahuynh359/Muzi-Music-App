package com.ahuynh.muzimusicapp.data.api

import com.ahuynh.muzimusicapp.data.model.response.ListSongResponse
import com.ahuynh.muzimusicapp.data.model.response.ListTypeResponse
import com.ahuynh.muzimusicapp.data.model.response.SongResponse
import com.ahuynh.muzimusicapp.utils.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path


interface TypeAPI {


    @GET("${Constants.API_VERSION}/type/all")
    suspend fun getAllType(): Response<ListTypeResponse>

    @GET("${Constants.API_VERSION}/type/{id}/songs")
    suspend fun getSongFromType(@Path("id") id : Long): Response<ListSongResponse>


}