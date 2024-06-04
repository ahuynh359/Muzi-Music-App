package com.ahuynh.muzimusicapp.data_api.service

import com.ahuynh.muzimusicapp.data_api.api.SongAPI
import com.ahuynh.muzimusicapp.data_api.model.Song
import com.ahuynh.muzimusicapp.data_api.model.User
import com.ahuynh.muzimusicapp.data_api.model.response.toListSong
import com.ahuynh.muzimusicapp.data_api.model.response.toListUser
import com.ahuynh.muzimusicapp.data_api.service.base.BaseRemoteService
import com.ahuynh.muzimusicapp.utils.Response
import javax.inject.Inject

class SongService @Inject constructor(
    private val songAPI: SongAPI
) : BaseRemoteService() {
    suspend fun getAllSong(): List<Song> {
        val result = callApi { songAPI.getAllSong() }
        return if (result is Response.Success) {
            result.data.data.toListSong()
        } else {
            emptyList()
        }
    }

    suspend fun getSongById(id: Long): Song? {
        val result = callApi { songAPI.getSongById(id) }
        if (result is Response.Success) {
            return result.data.toSong()
        } else {
            return null
        }
    }
    suspend fun getSingerFromSongById(id: Long): List<User> {
        val result = callApi { songAPI.getSingerFromSongById(id) }
        return if (result is Response.Success) {
            result.data.data.toListUser()
        } else {
            emptyList()
        }
    }


}