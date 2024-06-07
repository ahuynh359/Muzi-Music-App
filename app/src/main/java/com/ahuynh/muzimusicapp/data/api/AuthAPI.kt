package com.ahuynh.muzimusicapp.data.api

import com.ahuynh.muzimusicapp.data.model.response.ApiResponse
import com.ahuynh.muzimusicapp.data.model.request.LoginRequest
import com.ahuynh.muzimusicapp.data.model.request.SignUpRequest
import com.ahuynh.muzimusicapp.utils.Constants.API_VERSION
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface AuthAPI {
    @POST("$API_VERSION/auth/signup")
    suspend fun signup(@Body signUpRequest: SignUpRequest): Response<ApiResponse>

    @POST("$API_VERSION/auth/login")
    suspend fun login(@Body loginRequest: LoginRequest): Response<ApiResponse>

    @GET("$API_VERSION/auth/verifyEmail/{token}")
    suspend fun verifyEmail(@Path("token") token : String): Response<ApiResponse>


}