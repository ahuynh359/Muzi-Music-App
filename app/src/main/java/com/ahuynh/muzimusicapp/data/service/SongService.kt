package com.ahuynh.muzimusicapp.data.service

import com.ahuynh.muzimusicapp.data.api.SongAPI
import com.ahuynh.muzimusicapp.data.model.Song
import com.ahuynh.muzimusicapp.data.model.User
import com.ahuynh.muzimusicapp.data.model.response.SearchJson
import com.ahuynh.muzimusicapp.data.model.response.toListSong
import com.ahuynh.muzimusicapp.data.model.response.toListUser
import com.ahuynh.muzimusicapp.data.service.base.BaseRemoteService
import com.ahuynh.muzimusicapp.utils.Response
import javax.inject.Inject

class SongService @Inject constructor(
    private val songAPI: SongAPI
) : BaseRemoteService() {
    //ok
    suspend fun getNewSongs(): List<Song> {
        val result = callApi { songAPI.getNewSongs() }
        return if (result is Response.Success) {
            result.data.data.toListSong()
        } else {
            arrayListOf()
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
            arrayListOf()
        }
    }

    suspend fun searchSong(str: String): SearchJson? {
        val result = callApi { songAPI.searchSong(str) }
        return if (result is Response.Success) {
            result.data.data
        } else {
            null
        }
    }


}