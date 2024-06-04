package com.ahuynh.muzimusicapp.data_api.api

import com.ahuynh.muzimusicapp.data_api.model.response.AlbumResponse
import com.ahuynh.muzimusicapp.data_api.model.response.ListAlbumResponse
import com.ahuynh.muzimusicapp.data_api.model.response.ListSongResponse
import com.ahuynh.muzimusicapp.data_api.model.response.ListTypeResponse
import com.ahuynh.muzimusicapp.utils.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path


interface TypeAPI {


    @GET("${Constants.API_VERSION}/type")
    suspend fun getAllType(): Response<ListTypeResponse>



}