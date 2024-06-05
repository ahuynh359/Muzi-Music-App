package com.ahuynh.muzimusicapp.data.service

import com.ahuynh.muzimusicapp.data.api.AuthAPI
import com.ahuynh.muzimusicapp.data.model.ApiResponse
import com.ahuynh.muzimusicapp.data.model.request.LoginRequest
import com.ahuynh.muzimusicapp.data.model.request.SignUpRequest
import com.ahuynh.muzimusicapp.data.service.base.BaseRemoteService
import com.ahuynh.muzimusicapp.utils.Response
import javax.inject.Inject

class AuthService @Inject constructor(
    private val authAPI: AuthAPI
) : BaseRemoteService() {
    suspend fun signup(signUpRequest: SignUpRequest): Response<ApiResponse> {
        return callApi { authAPI.signup(signUpRequest) }
    }

    suspend fun login(loginRequest: LoginRequest): Response<ApiResponse> {
        return callApi { authAPI.login(loginRequest) }
    }

    suspend fun verifyEmail(token: String): Response<ApiResponse> {
        return callApi { authAPI.verifyEmail(token) }
    }
}