package com.ahuynh.muzimusicapp.data.service.remote

import com.ahuynh.muzimusicapp.data.api.SongAPI
import com.ahuynh.muzimusicapp.data.model.Song
import com.ahuynh.muzimusicapp.data.model.response.IsLoveSongResponse
import com.ahuynh.muzimusicapp.data.model.response.LoveSongResponse
import com.ahuynh.muzimusicapp.data.model.response.MessageResponse
import com.ahuynh.muzimusicapp.data.model.response.ListSearchResponse
import com.ahuynh.muzimusicapp.data.model.response.SearchResponse
import com.ahuynh.muzimusicapp.data.model.response.toListSong
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


    suspend fun loveSong( songId : Long) : Response<MessageResponse>{
        return callApi { songAPI.loveSong(songId) }
    }



    suspend fun isUserLoveSong( songId : Long) : Response<IsLoveSongResponse>{
        return callApi { songAPI.isUserLoveSong(songId) }
    }
    suspend fun searchSong(str: String): SearchResponse? {
        val result = callApi { songAPI.searchSong(str) }
        return if (result is Response.Success) {
            result.data.data
        } else {
            null
        }
    }

    suspend fun getLoveSong(): Response<LoveSongResponse> {
        return callApi { songAPI.getLoveSong() }
    }

    suspend fun getSongByListen(): List<Song> {
        val result = callApi { songAPI.getSongByListen() }
        return if (result is Response.Success) {
            result.data.data.toListSong()
        } else {
            arrayListOf()
        }
    }



}