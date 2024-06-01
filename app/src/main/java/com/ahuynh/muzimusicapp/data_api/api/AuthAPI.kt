package com.ahuynh.muzimusicapp.data_api.api

import com.ahuynh.muzimusicapp.data_api.model.ApiResponse
import com.ahuynh.muzimusicapp.data_api.model.request.LoginRequest
import com.ahuynh.muzimusicapp.data_api.model.request.SignUpRequest
import com.ahuynh.muzimusicapp.utils.Constants.API_VERSION
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthAPI {
    @POST("$API_VERSION/auth/signup")
    suspend fun signup(@Body signUpRequest: SignUpRequest): Response<ApiResponse>

    @POST("$API_VERSION/auth/login")
    suspend fun login(@Body loginRequest: LoginRequest): Response<ApiResponse>
}