package com.ahuynh.muzimusicapp.data.service.remote

import com.ahuynh.muzimusicapp.data.api.SingerAPI
import com.ahuynh.muzimusicapp.data.model.Singer
import com.ahuynh.muzimusicapp.data.model.Song
import com.ahuynh.muzimusicapp.data.model.response.IsLoveSongResponse
import com.ahuynh.muzimusicapp.data.model.response.LoveSingerResponse
import com.ahuynh.muzimusicapp.data.model.response.MessageResponse
import com.ahuynh.muzimusicapp.data.model.response.toListSinger
import com.ahuynh.muzimusicapp.data.model.response.toListSong
import com.ahuynh.muzimusicapp.data.service.base.BaseRemoteService
import com.ahuynh.muzimusicapp.utils.Response
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SingerService @Inject constructor(
    private val singerAPI: SingerAPI
) : BaseRemoteService() {
    suspend fun getNewSingers(): List<Singer> {
        val result = callApi { singerAPI.getNewSingers() }
        return if (result is Response.Success) {
            result.data.data.toListSinger()
        } else {
            arrayListOf()
        }
    }

    suspend fun getSongsOfSinger(id : Long): List<Song> {
        val result = callApi { singerAPI.getSongsOfSinger(id) }
        return if (result is Response.Success) {
            result.data.data.toListSong()
        } else {
            arrayListOf()
        }
    }

    suspend fun getLoveSinger(): List<Singer> {
        val result = callApi { singerAPI.getLoveSinger() }
        return if (result is Response.Success) {
            result.data.data.toListSinger()
        } else {
            arrayListOf()
        }
    }
    suspend fun isUserLoveSinger(id : Long): Response<LoveSingerResponse> {
       return callApi { singerAPI.isUserLoveSinger(id) }
    }

    suspend fun loveOrUnloveSinger(id: Long) :  Response<MessageResponse> {
        return callApi { singerAPI.loveOrUnloveSinger(id) }

    }
}