package com.ahuynh.muzimusicapp.data.api

import com.ahuynh.muzimusicapp.data.model.response.ListSingerResponse
import com.ahuynh.muzimusicapp.data.model.response.ListSongResponse
import com.ahuynh.muzimusicapp.data.model.response.SearchResponse
import com.ahuynh.muzimusicapp.data.model.response.SongResponse
import com.ahuynh.muzimusicapp.utils.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface SingerAPI {

    @GET("${Constants.API_VERSION}/singer/new")
    suspend fun getNewSingers(): Response<ListSingerResponse>



}