package com.ahuynh.muzimusicapp.data.repository

import com.ahuynh.muzimusicapp.data.model.ApiResponse
import com.ahuynh.muzimusicapp.data.service.UserService
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
    suspend fun isUserLoveSong(id: Long, songId: Long): Response<ApiResponse> {
        return withContext(dispatcher) {
            userService.isUserLoveSong(id, songId)
        }
    }


}
