package com.ahuynh.muzimusicapp.data.service.remote

import com.ahuynh.muzimusicapp.data.api.AuthAPI
import com.ahuynh.muzimusicapp.data.model.request.EmailRequest
import com.ahuynh.muzimusicapp.data.model.request.LoginRequest
import com.ahuynh.muzimusicapp.data.model.request.ResetPasswordRequest
import com.ahuynh.muzimusicapp.data.model.request.SignUpRequest
import com.ahuynh.muzimusicapp.data.model.response.LoginResponse
import com.ahuynh.muzimusicapp.data.model.response.MessageResponse
import com.ahuynh.muzimusicapp.data.model.response.UserResponseData
import com.ahuynh.muzimusicapp.data.service.base.BaseRemoteService
import com.ahuynh.muzimusicapp.utils.NetworkResult
import javax.inject.Inject

class AuthRemoteService @Inject constructor(
    private val authAPI: AuthAPI
) : BaseRemoteService() {
    suspend fun signup(signUpRequest: SignUpRequest): NetworkResult<UserResponseData> {
        return callApi { authAPI.signup(signUpRequest) }
    }

    suspend fun login(loginRequest: LoginRequest): NetworkResult<LoginResponse> {
        return callApi { authAPI.login(loginRequest) }
    }

    suspend fun sendEmail(emailRequest: EmailRequest): NetworkResult<MessageResponse> {
        return callApi { authAPI.sendEmail(emailRequest) }
    }

    suspend fun changePassword(resetPasswordRequest: ResetPasswordRequest): NetworkResult<MessageResponse> {
        return callApi { authAPI.changePassword(resetPasswordRequest) }
    }


}