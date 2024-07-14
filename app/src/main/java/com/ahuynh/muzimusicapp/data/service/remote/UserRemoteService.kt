package com.ahuynh.muzimusicapp.data.service.remote

import com.ahuynh.muzimusicapp.data.api.UserAPI
import com.ahuynh.muzimusicapp.data.model.User
import com.ahuynh.muzimusicapp.data.model.request.AddUserRequest
import com.ahuynh.muzimusicapp.data.model.request.ChangePasswordRequest
import com.ahuynh.muzimusicapp.data.model.request.UpdateUserRequest
import com.ahuynh.muzimusicapp.data.model.response.MessageResponse
import com.ahuynh.muzimusicapp.data.model.response.UserResponseData
import com.ahuynh.muzimusicapp.data.model.response.toListUser
import com.ahuynh.muzimusicapp.data.service.base.BaseRemoteService
import com.ahuynh.muzimusicapp.ui.base.bottom_sheet.SortName
import com.ahuynh.muzimusicapp.utils.Response
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import javax.inject.Inject

class UserRemoteService @Inject constructor(
    private val userAPI: UserAPI
) : BaseRemoteService() {


    suspend fun getCurrentUser(): User? {
        val result = callApi { userAPI.getCurrentUser() }
        return if (result is Response.Success) {
            result.data.data.toUser()
        } else {
            null
        }
    }

    suspend fun changeAvatar(id : Long, file: File): Response<UserResponseData> {
        return callApi {
            val imageFileRequestBody =
                file.asRequestBody("image/*".toMediaTypeOrNull())
            userAPI.changeAvatar(
                id,
                MultipartBody.Part.createFormData(
                    "avatar",
                    file.name,
                    imageFileRequestBody
                )
            )


        }

    }

    suspend fun changePassword(changePasswordRequest: ChangePasswordRequest): Response<MessageResponse> {
        return callApi { userAPI.changePassword(changePasswordRequest) }
    }

    suspend fun getAllUsers(sortName: SortName): List<User> {
        val result = callApi { userAPI.getAllUsers(sortName) }
        return if (result is Response.Success) {
            result.data.data.toListUser()
        } else {
            arrayListOf()
        }
    }

    suspend fun getUserById(id: Long) : User? {
        val result = callApi { userAPI.getUserById(id) }
        return if (result is Response.Success) {
            result.data.data.toUser()
        } else {
          null
        }

    }

    suspend fun createUser(addUserRequest: AddUserRequest): Response<UserResponseData> {
        return callApi { userAPI.createUser(addUserRequest) }
    }

    suspend fun deleteUser(id: Long)  : Response<MessageResponse>{
        return callApi { userAPI.deleteUser(id) }

    }

    suspend fun lockOrUnlockUser(id: Long)  : Response<UserResponseData>{
        return callApi { userAPI.lockOrUnlockUser(id) }

    }

    suspend fun updateUser(updateUserRequest: UpdateUserRequest)  : Response<UserResponseData>{
        return callApi { userAPI.updateUser(updateUserRequest) }

    }
}
