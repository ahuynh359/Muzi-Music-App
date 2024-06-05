package com.ahuynh.muzimusicapp.data.api

import com.ahuynh.muzimusicapp.data.model.ApiResponse
import com.ahuynh.muzimusicapp.utils.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface UserAPI {
    @GET("${Constants.API_VERSION}/user/{id}/isLoveSong/{songId}")
    suspend fun isUserLoveSong(@Path("id") id: Long,@Path("songId") songId: Long): Response<ApiResponse>




}