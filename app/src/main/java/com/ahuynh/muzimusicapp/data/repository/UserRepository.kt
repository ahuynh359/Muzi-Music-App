package com.ahuynh.muzimusicapp.data.repository

import com.ahuynh.muzimusicapp.data.model.Song
import com.ahuynh.muzimusicapp.data.model.User
import com.ahuynh.muzimusicapp.data.model.request.SignUpRequest
import com.ahuynh.muzimusicapp.data.model.response.ApiResponse
import com.ahuynh.muzimusicapp.data.model.response.MessageResponse
import com.ahuynh.muzimusicapp.data.service.UserService
import com.ahuynh.muzimusicapp.di.IoDispatcher
import com.ahuynh.muzimusicapp.utils.Response
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MultipartBody
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val userService: UserService,
    @IoDispatcher private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    suspend fun isUserLoveSong(id: Long, songId: Long): Boolean {
        return withContext(dispatcher) {
            userService.isUserLoveSong(id, songId)
        }
    }


    suspend fun loveOrUnlove(userId: Long, songId: Long): Response<ApiResponse> {
        return withContext(dispatcher) {
            userService.loveOrUnlove(userId, songId)
        }
    }

    suspend fun getLoveSong(id: Long): List<Song> {
        return withContext(dispatcher) {
            userService.getLoveSong(id)
        }
    }

    suspend fun getUserById(id: Long): User? {
        return withContext(dispatcher) {
            userService.getUserById(id)
        }
    }

    suspend fun changeAvatar(id: Long, file: MultipartBody.Part): User? {
        return withContext(dispatcher) {
            userService.changeAvatar(id, file)
        }
    }





}
