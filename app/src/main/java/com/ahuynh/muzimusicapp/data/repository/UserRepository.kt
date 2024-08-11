package com.ahuynh.muzimusicapp.data.repository

import com.ahuynh.muzimusicapp.data.model.User
import com.ahuynh.muzimusicapp.data.model.request.AddUserRequest
import com.ahuynh.muzimusicapp.data.model.request.ChangePasswordRequest
import com.ahuynh.muzimusicapp.data.model.request.UpdateUserRequest
import com.ahuynh.muzimusicapp.data.model.response.MessageResponse
import com.ahuynh.muzimusicapp.data.model.response.UserResponseData
import com.ahuynh.muzimusicapp.data.service.remote.UserRemoteService
import com.ahuynh.muzimusicapp.di.IoDispatcher
import com.ahuynh.muzimusicapp.ui.base.bottom_sheet.SortName
import com.ahuynh.muzimusicapp.utils.NetworkResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val userRemoteService: UserRemoteService,
    @IoDispatcher private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {



    suspend fun changeAvatar(id: Long, file: File): NetworkResult<UserResponseData> {
        return withContext(dispatcher) {
            userRemoteService.changeAvatar(id, file)
        }
    }

    suspend fun changePassword(changePasswordRequest: ChangePasswordRequest): NetworkResult<MessageResponse> {
        return withContext(dispatcher) {
            userRemoteService.changePassword(changePasswordRequest)
        }
    }

    suspend fun deleteUser(id: Long): NetworkResult<MessageResponse> {
        return withContext(dispatcher) {
            userRemoteService.deleteUser(id)
        }
    }

    suspend fun getAllUsers(sort : SortName): List<User> {
        return withContext(dispatcher) {
            userRemoteService.getAllUsers(sort)
        }
    }

    suspend fun getUserById(id: Long): User? {
        return withContext(dispatcher) {
            userRemoteService.getUserById(id)
        }

    }

    suspend fun createUser(addUserRequest: AddUserRequest): NetworkResult<UserResponseData> {

        return withContext(dispatcher) {
            userRemoteService.createUser(addUserRequest)
        }
    }

    suspend fun lockOrUnlockUser(id: Long): NetworkResult<UserResponseData> {
        return withContext(dispatcher) {
            userRemoteService.lockOrUnlockUser(id)
        }
    }

    suspend fun updateUser(updateUserRequest: UpdateUserRequest): NetworkResult<UserResponseData> {
        return withContext(dispatcher) {
            userRemoteService.updateUser(updateUserRequest)
        }
    }

    suspend fun updateToken(token: String): NetworkResult<UserResponseData> {
        return withContext(dispatcher) {
            userRemoteService.updateToken(token)
        }
    }


}



