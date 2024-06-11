package com.ahuynh.muzimusicapp.data.service

import com.ahuynh.muzimusicapp.data.api.AuthAPI
import com.ahuynh.muzimusicapp.data.model.response.ApiResponse
import com.ahuynh.muzimusicapp.data.model.request.LoginRequest
import com.ahuynh.muzimusicapp.data.model.request.SignUpRequest
import com.ahuynh.muzimusicapp.data.model.response.LoginResponse
import com.ahuynh.muzimusicapp.data.model.response.MessageResponse
import com.ahuynh.muzimusicapp.data.service.base.BaseRemoteService
import com.ahuynh.muzimusicapp.utils.Response
import javax.inject.Inject

class AuthService @Inject constructor(
    private val authAPI: AuthAPI
) : BaseRemoteService() {
    suspend fun signup(signUpRequest: SignUpRequest): Response<MessageResponse> {
        return callApi { authAPI.signup(signUpRequest) }
    }




    suspend fun login(loginRequest: LoginRequest): Response<LoginResponse> {
        return callApi { authAPI.login(loginRequest) }
    }


    suspend fun resendOtp(email: String): Response<MessageResponse> {
        return callApi { authAPI.resendOtp(email) }
    }

}