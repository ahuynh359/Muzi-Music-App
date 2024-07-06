package com.ahuynh.muzimusicapp.data.repository

import com.ahuynh.muzimusicapp.data.model.Song
import com.ahuynh.muzimusicapp.data.model.response.IsLoveSongResponse
import com.ahuynh.muzimusicapp.data.model.response.LoveSongResponse
import com.ahuynh.muzimusicapp.data.model.response.MessageResponse
import com.ahuynh.muzimusicapp.data.model.response.ListSearchResponse
import com.ahuynh.muzimusicapp.data.model.response.SearchResponse
import com.ahuynh.muzimusicapp.data.service.remote.SongService
import com.ahuynh.muzimusicapp.di.IoDispatcher
import com.ahuynh.muzimusicapp.utils.Response
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SongRepository @Inject constructor(
    private val songService: SongService,
    @IoDispatcher private val dispatcher: CoroutineDispatcher = Dispatchers.IO


) {

    suspend fun getNewSongs(): List<Song> {
        return withContext(dispatcher) {
            songService.getNewSongs()
        }
    }

    suspend fun getTop10(): List<Song> {
        return withContext(dispatcher) {
            songService.getTop10()
        }
    }

    suspend fun getSongById(id: Long): Song? {
        return withContext(dispatcher) {
            songService.getSongById(id)
        }
    }


    suspend fun loveSong( songId : Long): Response<MessageResponse> {
        return withContext(dispatcher) {
            songService.loveSong(songId)
        }
    }


    suspend fun listen( songId : Long): Response<MessageResponse> {
        return withContext(dispatcher) {
            songService.listen(songId)
        }
    }

    suspend fun isUserLoveSong(songId : Long): Response<IsLoveSongResponse> {
        return withContext(dispatcher) {
            songService.isUserLoveSong(songId)
        }
    }
    suspend fun searchSong(str: String): SearchResponse? {
        return withContext(dispatcher) {
            songService.searchSong(str)
        }
    }

    suspend fun getLoveSong(): Response<LoveSongResponse> {
        return withContext(dispatcher) {
            songService.getLoveSong()
        }
    }

    suspend fun getSongByListen(): List<Song> {
        return withContext(dispatcher) {
            songService.getSongByListen()
        }
    }



}