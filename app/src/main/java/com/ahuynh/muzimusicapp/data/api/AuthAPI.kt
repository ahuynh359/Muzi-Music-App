package com.ahuynh.muzimusicapp.data.api

import com.ahuynh.muzimusicapp.data.model.request.EmailRequest
import com.ahuynh.muzimusicapp.data.model.request.LoginRequest
import com.ahuynh.muzimusicapp.data.model.request.ResendOtpRequest
import com.ahuynh.muzimusicapp.data.model.request.ResetPasswordRequest
import com.ahuynh.muzimusicapp.data.model.request.SignUpRequest
import com.ahuynh.muzimusicapp.data.model.response.LoginResponse
import com.ahuynh.muzimusicapp.data.model.response.MessageResponse
import com.ahuynh.muzimusicapp.data.model.response.UserResponseData
import com.ahuynh.muzimusicapp.utils.Constants.API_VERSION
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthAPI {
    @POST("$API_VERSION/auth/signup")
    suspend fun signup(@Body signUpRequest: SignUpRequest): Response<UserResponseData>

    @POST("$API_VERSION/auth/login")
    suspend fun login(@Body loginRequest: LoginRequest): Response<LoginResponse>

    @POST("$API_VERSION/auth/email")
    suspend fun sendEmail(@Body emailRequest: EmailRequest): Response<MessageResponse>

    @POST("$API_VERSION/auth/reset")
    suspend fun changePassword(@Body resetPasswordRequest: ResetPasswordRequest): Response<MessageResponse>



}