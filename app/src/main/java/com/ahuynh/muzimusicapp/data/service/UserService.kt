package com.ahuynh.muzimusicapp.data.service

import com.ahuynh.muzimusicapp.data.api.UserAPI
import com.ahuynh.muzimusicapp.data.model.Song
import com.ahuynh.muzimusicapp.data.model.User
import com.ahuynh.muzimusicapp.data.model.response.ApiResponse
import com.ahuynh.muzimusicapp.data.model.response.toListSong
import com.ahuynh.muzimusicapp.data.service.base.BaseRemoteService
import com.ahuynh.muzimusicapp.utils.Response
import okhttp3.MultipartBody
import javax.inject.Inject

class UserService @Inject constructor(
    private val userAPI: UserAPI
) : BaseRemoteService() {


    suspend fun isUserLoveSong(id: Long, songId: Long): Boolean {
        val result = callApi { userAPI.isUserLoveSong(id, songId) }
        return if (result is Response.Success) {
            result.data.data
        } else {
            false
        }
    }

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

    suspend fun changeAvatar(id: Long,file : MultipartBody.Part): User? {
        val result = callApi { userAPI.changeAvatar(id,file) }
        return if (result is Response.Success) {
            result.data.data.toUser()
        } else {
            null
        }
    }




}
