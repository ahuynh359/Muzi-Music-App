package com.ahuynh.muzimusicapp.data.api

import com.ahuynh.muzimusicapp.data.model.request.ForgotPasswordRequest
import com.ahuynh.muzimusicapp.data.model.request.LoginRequest
import com.ahuynh.muzimusicapp.data.model.request.ResendOtpRequest
import com.ahuynh.muzimusicapp.data.model.request.ResetPasswordRequest
import com.ahuynh.muzimusicapp.data.model.request.SignUpRequest
import com.ahuynh.muzimusicapp.data.model.response.LoginResponse
import com.ahuynh.muzimusicapp.data.model.response.MessageResponse
import com.ahuynh.muzimusicapp.utils.Constants.API_VERSION
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthAPI {
    @POST("$API_VERSION/auth/signup")
    suspend fun signup(@Body signUpRequest: SignUpRequest): Response<MessageResponse>

    @POST("$API_VERSION/auth/login")
    suspend fun login(@Body loginRequest: LoginRequest): Response<LoginResponse>

    @POST("$API_VERSION/auth/forgot")
    suspend fun forgotPassword(@Body forgotPasswordRequest: ForgotPasswordRequest): Response<MessageResponse>

    @POST("$API_VERSION/auth/reset")
    suspend fun changePassword(@Body resetPasswordRequest: ResetPasswordRequest): Response<MessageResponse>

    @POST("$API_VERSION/auth/resend")
    suspend fun resendOtp(@Body resendOtpRequest: ResendOtpRequest): Response<MessageResponse>


}