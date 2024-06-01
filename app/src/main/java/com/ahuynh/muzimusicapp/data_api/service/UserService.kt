package com.ahuynh.muzimusicapp.data_api.service

import com.ahuynh.muzimusicapp.data_api.api.AuthAPI
import com.ahuynh.muzimusicapp.data_api.model.ApiResponse
import com.ahuynh.muzimusicapp.data_api.model.request.LoginRequest
import com.ahuynh.muzimusicapp.data_api.model.request.SignUpRequest
import com.ahuynh.muzimusicapp.data_api.service.base.BaseRemoteService
import com.ahuynh.muzimusicapp.utils.Response
import javax.inject.Inject

class UserService @Inject constructor(
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