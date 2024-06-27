package com.ahuynh.muzimusicapp.data.service.remote

import com.ahuynh.muzimusicapp.data.api.SingerAPI
import com.ahuynh.muzimusicapp.data.model.Singer
import com.ahuynh.muzimusicapp.data.model.response.toListSinger
import com.ahuynh.muzimusicapp.data.service.base.BaseRemoteService
import com.ahuynh.muzimusicapp.utils.Response
import javax.inject.Inject

class SingerService @Inject constructor(
    private val singerAPI: SingerAPI
) : BaseRemoteService() {
    //ok
    suspend fun getNewSingers(): List<Singer> {
        val result = callApi { singerAPI.getNewSingers() }
        return if (result is Response.Success) {
            result.data.data.toListSinger()
        } else {
            arrayListOf()
        }
    }
}