package com.ahuynh.muzimusicapp.data.repository

import com.ahuynh.muzimusicapp.data.model.request.ForgotPasswordRequest
import com.ahuynh.muzimusicapp.data.model.request.LoginRequest
import com.ahuynh.muzimusicapp.data.model.request.ResetPasswordRequest
import com.ahuynh.muzimusicapp.data.model.request.SignUpRequest
import com.ahuynh.muzimusicapp.data.model.response.LoginResponse
import com.ahuynh.muzimusicapp.data.model.response.MessageResponse
import com.ahuynh.muzimusicapp.data.service.remote.AuthRemoteService
import com.ahuynh.muzimusicapp.di.IoDispatcher
import com.ahuynh.muzimusicapp.utils.Response
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject


class AuthRepository @Inject constructor(
    private val authRemoteService: AuthRemoteService,
    @IoDispatcher private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {

    suspend fun signup(signUpRequest: SignUpRequest): Response<MessageResponse> {
        return withContext(dispatcher) {
            authRemoteService.signup(signUpRequest)
        }
    }


    suspend fun login(loginRequest: LoginRequest): Response<LoginResponse> {
        return withContext(dispatcher) {
            authRemoteService.login(loginRequest)
        }
    }

    suspend fun forgotPassword(forgotPasswordRequest: ForgotPasswordRequest): Response<MessageResponse> {
        return withContext(dispatcher) {
            authRemoteService.forgotPassword(forgotPasswordRequest)
        }
    }

    suspend fun changePassword(resetPasswordRequest: ResetPasswordRequest): Response<MessageResponse> {
        return withContext(dispatcher) {
            authRemoteService.changePassword(resetPasswordRequest)
        }
    }




}