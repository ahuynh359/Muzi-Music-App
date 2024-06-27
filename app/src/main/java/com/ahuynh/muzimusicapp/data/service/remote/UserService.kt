package com.ahuynh.muzimusicapp.data.service.remote

import com.ahuynh.muzimusicapp.data.api.UserAPI
import com.ahuynh.muzimusicapp.data.model.Song
import com.ahuynh.muzimusicapp.data.model.User
import com.ahuynh.muzimusicapp.data.model.request.ChangePasswordRequest
import com.ahuynh.muzimusicapp.data.model.response.ApiResponse
import com.ahuynh.muzimusicapp.data.model.response.MessageResponse
import com.ahuynh.muzimusicapp.data.model.response.UserResponseData
import com.ahuynh.muzimusicapp.data.model.response.toListSong
import com.ahuynh.muzimusicapp.data.service.base.BaseRemoteService
import com.ahuynh.muzimusicapp.utils.Response
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import javax.inject.Inject

class UserService @Inject constructor(
    private val userAPI: UserAPI
) : BaseRemoteService() {


    suspend fun loveOrUnlove(userId: Long, songId: Long): Response<ApiResponse> {
        return callApi { userAPI.loveOrUnlove(userId, songId) }
    }


    suspend fun getLoveSong(id: Long): List<Song> {
        val result = callApi { userAPI.getLoveSong(id) }
        return if (result is Response.Success) {
            result.data.data.toListSong()
        } else {
            arrayListOf()
        }
    }

    suspend fun getUserById(id: Long): User? {
        val result = callApi { userAPI.getUserById(id) }
        return if (result is Response.Success) {
            result.data.data.toUser()
        } else {
            null
        }
    }

    suspend fun changeAvatar(id: Long, file: File): Response<UserResponseData> {
        return callApi {
            val imageFileRequestBody =
                file.asRequestBody("image/*".toMediaTypeOrNull())
            userAPI.changeAvatar(
                id, MultipartBody.Part.createFormData(
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


}
