package com.ahuynh.muzimusicapp.data.api

import com.ahuynh.muzimusicapp.data.model.response.ListTypeResponse
import com.ahuynh.muzimusicapp.utils.Constants
import retrofit2.Response
import retrofit2.http.GET


interface TypeAPI {


    @GET("${Constants.API_VERSION}/type")
    suspend fun getAllType(): Response<ListTypeResponse>



}