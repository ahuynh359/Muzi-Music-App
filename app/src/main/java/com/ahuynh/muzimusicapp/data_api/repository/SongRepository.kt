package com.ahuynh.muzimusicapp.data_api.repository

import com.ahuynh.muzimusicapp.data_api.model.Song
import com.ahuynh.muzimusicapp.data_api.service.SongService
import com.ahuynh.muzimusicapp.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SongRepository @Inject constructor(
    private val songService: SongService,
    @IoDispatcher private val dispatcher: CoroutineDispatcher = Dispatchers.IO


) {

    suspend fun getAllSong(): List<Song> {
        return withContext(dispatcher) {
            songService.getAllSong()
        }
    }

    suspend fun getSongById(id: Long): Song? {
        return withContext(dispatcher) {
            songService.getSongById(id)
        }
    }




}