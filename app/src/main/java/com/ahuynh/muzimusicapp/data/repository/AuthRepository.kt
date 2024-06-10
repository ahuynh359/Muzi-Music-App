package com.ahuynh.muzimusicapp.data.repository

import com.ahuynh.muzimusicapp.data.model.response.ApiResponse
import com.ahuynh.muzimusicapp.data.model.request.LoginRequest
import com.ahuynh.muzimusicapp.data.model.request.SignUpRequest
import com.ahuynh.muzimusicapp.data.model.response.LoginResponse
import com.ahuynh.muzimusicapp.data.model.response.MessageResponse
import com.ahuynh.muzimusicapp.data.service.AuthService
import com.ahuynh.muzimusicapp.di.IoDispatcher
import com.ahuynh.muzimusicapp.utils.Response
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject


class AuthRepository @Inject constructor(
    private val authService: AuthService,
    @IoDispatcher private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {

    suspend fun signup(signUpRequest: SignUpRequest): Response<MessageResponse> {
        return withContext(dispatcher) {
            authService.signup(signUpRequest)
        }
    }


    suspend fun login(loginRequest: LoginRequest): Response<LoginResponse> {
        return withContext(dispatcher) {
            authService.login(loginRequest)
        }
    }

    suspend fun verifyEmail(token: String): Response<ApiResponse> {
        return withContext(dispatcher) {
            authService.verifyEmail(token)
        }
    }
}