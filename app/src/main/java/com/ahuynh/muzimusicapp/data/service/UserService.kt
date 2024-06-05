package com.ahuynh.muzimusicapp.data.service

import com.ahuynh.muzimusicapp.data.api.UserAPI
import com.ahuynh.muzimusicapp.data.model.ApiResponse
import com.ahuynh.muzimusicapp.data.service.base.BaseRemoteService
import com.ahuynh.muzimusicapp.utils.Response
import javax.inject.Inject

class UserService @Inject constructor(
    private val userAPI: UserAPI
) : BaseRemoteService() {

    suspend fun isUserLoveSong(id: Long, songId: Long): Response<ApiResponse> {
        return callApi { userAPI.isUserLoveSong(id, songId) }

    }
}
