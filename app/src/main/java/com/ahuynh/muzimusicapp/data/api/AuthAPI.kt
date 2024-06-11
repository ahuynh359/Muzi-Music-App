package com.ahuynh.muzimusicapp.data.api

import com.ahuynh.muzimusicapp.data.model.request.LoginRequest
import com.ahuynh.muzimusicapp.data.model.request.SignUpRequest
import com.ahuynh.muzimusicapp.data.model.response.LoginResponse
import com.ahuynh.muzimusicapp.data.model.response.MessageResponse
import com.ahuynh.muzimusicapp.utils.Constants.API_VERSION
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path

interface AuthAPI {
    @POST("$API_VERSION/user-service/auth/signup")
    suspend fun signup(@Body signUpRequest: SignUpRequest): Response<MessageResponse>

    @POST("$API_VERSION/user-service/auth/login")
    suspend fun login(@Body loginRequest: LoginRequest): Response<LoginResponse>


    @POST("$API_VERSION/user-service/auth/resend/{email}")
    suspend fun resendOtp(@Path("email") email: String): Response<MessageResponse>


}