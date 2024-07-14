package com.ahuynh.muzimusicapp.data.repository

import com.ahuynh.muzimusicapp.data.database.entity.SongEntity
import com.ahuynh.muzimusicapp.data.model.Song
import com.ahuynh.muzimusicapp.data.model.response.IsLoveSongResponse
import com.ahuynh.muzimusicapp.data.model.response.LoveSongResponse
import com.ahuynh.muzimusicapp.data.model.response.MessageResponse
import com.ahuynh.muzimusicapp.data.model.response.SearchResponse
import com.ahuynh.muzimusicapp.data.service.local.SongLocalService
import com.ahuynh.muzimusicapp.data.service.remote.SongRemoteService
import com.ahuynh.muzimusicapp.di.IoDispatcher
import com.ahuynh.muzimusicapp.utils.Response
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SongRepository @Inject constructor(
    private val songRemoteService: SongRemoteService,
    private val songLocalService: SongLocalService,
    @IoDispatcher private val dispatcher: CoroutineDispatcher = Dispatchers.IO


) {

    suspend fun getNewSongs(): List<Song> {
        return withContext(dispatcher) {
            songRemoteService.getNewSongs()
        }
    }

    suspend fun getTop10(): List<Song> {
        return withContext(dispatcher) {
            songRemoteService.getTop10()
        }
    }

    suspend fun getSongById(id: Long): Song? {
        return withContext(dispatcher) {
            songRemoteService.getSongById(id)
        }
    }


    suspend fun loveSong(songId: Long): Response<MessageResponse> {
        return withContext(dispatcher) {
            songRemoteService.loveSong(songId)
        }
    }


    suspend fun listen(songId: Long): Response<MessageResponse> {
        return withContext(dispatcher) {
            songRemoteService.listen(songId)
        }
    }

    suspend fun isUserLoveSong(songId: Long): Response<IsLoveSongResponse> {
        return withContext(dispatcher) {
            songRemoteService.isUserLoveSong(songId)
        }
    }

    suspend fun searchSong(str: String): SearchResponse? {
        return withContext(dispatcher) {
            songRemoteService.searchSong(str)
        }
    }

    suspend fun getLoveSong(): Response<LoveSongResponse> {
        return withContext(dispatcher) {
            songRemoteService.getLoveSong()
        }
    }

    suspend fun getSongByListen(): List<Song> {
        return withContext(dispatcher) {
            songRemoteService.getSongByListen()
        }
    }

    suspend fun insertSong(song: SongEntity) {
        return withContext(dispatcher) {
            songLocalService.insertSong(song)
        }
    }

    suspend fun getRecentSongs(): List<SongEntity> {
        return songLocalService.getRecentSongs()
    }

    suspend fun clearRecentSongs() {
        return withContext(dispatcher) {
            songLocalService.clearRecentSongs()
        }

    }


}