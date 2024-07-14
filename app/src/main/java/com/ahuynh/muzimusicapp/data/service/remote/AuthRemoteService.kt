package com.ahuynh.muzimusicapp.data.service.remote

import com.ahuynh.muzimusicapp.data.api.AuthAPI
import com.ahuynh.muzimusicapp.data.model.request.ForgotPasswordRequest
import com.ahuynh.muzimusicapp.data.model.request.LoginRequest
import com.ahuynh.muzimusicapp.data.model.request.ResendOtpRequest
import com.ahuynh.muzimusicapp.data.model.request.ResetPasswordRequest
import com.ahuynh.muzimusicapp.data.model.request.SignUpRequest
import com.ahuynh.muzimusicapp.data.model.response.LoginResponse
import com.ahuynh.muzimusicapp.data.model.response.MessageResponse
import com.ahuynh.muzimusicapp.data.service.base.BaseRemoteService
import com.ahuynh.muzimusicapp.utils.Response
import javax.inject.Inject

class AuthRemoteService @Inject constructor(
    private val authAPI: AuthAPI
) : BaseRemoteService() {
    suspend fun signup(signUpRequest: SignUpRequest): Response<MessageResponse> {
        return callApi { authAPI.signup(signUpRequest) }
    }

    suspend fun login(loginRequest: LoginRequest): Response<LoginResponse> {
        return callApi { authAPI.login(loginRequest) }
    }

    suspend fun forgotPassword(forgotPasswordRequest: ForgotPasswordRequest): Response<MessageResponse> {
        return callApi { authAPI.forgotPassword(forgotPasswordRequest) }
    }

    suspend fun changePassword(resetPasswordRequest: ResetPasswordRequest): Response<MessageResponse> {
        return callApi { authAPI.changePassword(resetPasswordRequest) }
    }



    suspend fun resendOtp(resendOtpRequest: ResendOtpRequest): Response<MessageResponse> {
        return callApi { authAPI.resendOtp(resendOtpRequest) }
    }

}