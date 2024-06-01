package com.ahuynh.muzimusicapp.data_api.repository

import com.ahuynh.muzimusicapp.data_api.model.ApiResponse
import com.ahuynh.muzimusicapp.data_api.model.request.LoginRequest
import com.ahuynh.muzimusicapp.data_api.model.request.SignUpRequest
import com.ahuynh.muzimusicapp.data_api.service.UserService
import com.ahuynh.muzimusicapp.di.IoDispatcher
import com.ahuynh.muzimusicapp.utils.Response
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject


class UserRepository @Inject constructor(
    private val userService: UserService,
    @IoDispatcher private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {

    suspend fun signup(signUpRequest: SignUpRequest): Response<ApiResponse> {
        return withContext(dispatcher) {
            userService.signup(signUpRequest)
        }
    }


    suspend fun login(loginRequest: LoginRequest): Response<ApiResponse> {
        return withContext(dispatcher) {
            userService.login(loginRequest)
        }
    }

    suspend fun verifyEmail(token: String): Response<ApiResponse> {
        return withContext(dispatcher) {
            userService.verifyEmail(token)
        }
    }
}