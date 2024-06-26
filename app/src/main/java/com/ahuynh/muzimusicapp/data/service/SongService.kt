package com.ahuynh.muzimusicapp.data.service

import android.os.Message
import com.ahuynh.muzimusicapp.data.api.SongAPI
import com.ahuynh.muzimusicapp.data.model.Song
import com.ahuynh.muzimusicapp.data.model.User
import com.ahuynh.muzimusicapp.data.model.request.PlaylistRequest
import com.ahuynh.muzimusicapp.data.model.response.LoveSongResponse
import com.ahuynh.muzimusicapp.data.model.response.MessageResponse
import com.ahuynh.muzimusicapp.data.model.response.PlaylistResponseData
import com.ahuynh.muzimusicapp.data.model.response.SearchJson
import com.ahuynh.muzimusicapp.data.model.response.toListSinger
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
//    suspend fun getSingerFromSongById(id: Long): List<User> {
//        val result = callApi { songAPI.getSingerFromSongById(id) }
//        return if (result is Response.Success) {
//            result.data.data.toListSinger()
//        } else {
//            arrayListOf()
//        }
//    }

    suspend fun searchSong(str: String): SearchJson? {
        val result = callApi { songAPI.searchSong(str) }
        return if (result is Response.Success) {
            result.data.data
        } else {
            null
        }
    }

    suspend fun loveSong(userId: Long, songId : Long) : Response<MessageResponse>{
        return callApi { songAPI.loveSong(userId,songId) }
    }

    suspend fun unloveSong(userId: Long, songId : Long) : Response<MessageResponse>{
        return callApi { songAPI.unloveSong(userId,songId) }
    }

    suspend fun isUserLoveSong(userId: Long, songId : Long) : Response<LoveSongResponse>{
        return callApi { songAPI.isUserLoveSong(userId,songId) }
    }



}