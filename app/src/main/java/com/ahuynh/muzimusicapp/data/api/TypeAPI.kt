package com.ahuynh.muzimusicapp.data.api

import com.ahuynh.muzimusicapp.data.model.response.SongResponseData
import com.ahuynh.muzimusicapp.data.model.response.TypeResponseData
import com.ahuynh.muzimusicapp.data.model.response.SongResponse
import com.ahuynh.muzimusicapp.utils.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path


interface TypeAPI {


    @GET("${Constants.API_VERSION}/type/all")
    suspend fun getAllType(): Response<TypeResponseData>

    @GET("${Constants.API_VERSION}/type/{id}/songs")
    suspend fun getSongFromType(@Path("id") id : Long): Response<SongResponseData>


}