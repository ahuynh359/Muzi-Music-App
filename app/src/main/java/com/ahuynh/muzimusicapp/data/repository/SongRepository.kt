package com.ahuynh.muzimusicapp.data.repository

import com.ahuynh.muzimusicapp.data.model.Song
import com.ahuynh.muzimusicapp.data.model.User
import com.ahuynh.muzimusicapp.data.model.response.SearchJson
import com.ahuynh.muzimusicapp.data.model.response.SearchResponse
import com.ahuynh.muzimusicapp.data.service.SongService
import com.ahuynh.muzimusicapp.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SongRepository @Inject constructor(
    private val songService: SongService,
    @IoDispatcher private val dispatcher: CoroutineDispatcher = Dispatchers.IO


) {
    //ok
    suspend fun getNewSongs(): List<Song> {
        return withContext(dispatcher) {
            songService.getNewSongs()
        }
    }

    suspend fun getSongById(id: Long): Song? {
        return withContext(dispatcher) {
            songService.getSongById(id)
        }
    }



    suspend fun searchSong(str: String): SearchJson? {
        return withContext(dispatcher) {
            songService.searchSong(str)
        }
    }


}