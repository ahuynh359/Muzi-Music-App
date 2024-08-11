package com.ahuynh.muzimusicapp.data.repository

import com.ahuynh.muzimusicapp.data.database.entity.SongEntity
import com.ahuynh.muzimusicapp.data.model.Song
import com.ahuynh.muzimusicapp.data.model.request.UpdateSongRequest
import com.ahuynh.muzimusicapp.data.model.response.IsLoveSongResponse
import com.ahuynh.muzimusicapp.data.model.response.LoveSongResponse
import com.ahuynh.muzimusicapp.data.model.response.MessageResponse
import com.ahuynh.muzimusicapp.data.model.response.SearchResponse
import com.ahuynh.muzimusicapp.data.model.response.SongListen
import com.ahuynh.muzimusicapp.data.model.response.SongResponseData
import com.ahuynh.muzimusicapp.data.service.local.SongLocalService
import com.ahuynh.muzimusicapp.data.service.remote.SongRemoteService
import com.ahuynh.muzimusicapp.di.IoDispatcher
import com.ahuynh.muzimusicapp.ui.base.bottom_sheet.SortName
import com.ahuynh.muzimusicapp.utils.NetworkResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import javax.inject.Inject

class SongRepository @Inject constructor(
    private val songRemoteService: SongRemoteService,
    private val songLocalService: SongLocalService,
    @IoDispatcher private val dispatcher: CoroutineDispatcher = Dispatchers.IO


) {

    suspend fun getAllSongs(sortName: SortName): List<Song> {
        return withContext(dispatcher) {
            songRemoteService.getAllSongs(sortName)
        }
    }

    suspend fun getTop10(): List<Song> {
        return withContext(dispatcher) {
            songRemoteService.getTop10()
        }
    }

    suspend fun getTop3(): List<SongListen> {
        return withContext(dispatcher) {
            songRemoteService.getTop3()
        }
    }

    suspend fun getSongById(id: Long): Song? {
        return withContext(dispatcher) {
            songRemoteService.getSongById(id)
        }
    }


    suspend fun loveSong(songId: Long): NetworkResult<MessageResponse> {
        return withContext(dispatcher) {
            songRemoteService.loveSong(songId)
        }
    }


    suspend fun listen(songId: Long): NetworkResult<MessageResponse> {
        return withContext(dispatcher) {
            songRemoteService.listen(songId)
        }
    }

    suspend fun isUserLoveSong(songId: Long): NetworkResult<IsLoveSongResponse> {
        return withContext(dispatcher) {
            songRemoteService.isUserLoveSong(songId)
        }
    }

    suspend fun searchSong(str: String): SearchResponse? {
        return withContext(dispatcher) {
            songRemoteService.searchSong(str)
        }
    }

    suspend fun getLoveSong(): NetworkResult<LoveSongResponse> {
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

    suspend fun createSong(
        name: String,
        avatar: File,
        file: File,
        lyrics: String,
        albumId: Long,
        singerId: Set<Long>,
        typeId: Set<Long>
    ) : NetworkResult<SongResponseData> {
        return withContext(dispatcher) {
            songRemoteService.createSong(name, avatar, file, lyrics, albumId, singerId, typeId)
        }
    }

    suspend fun changeAvatar(id: Long, file: File): NetworkResult<SongResponseData> {
        return withContext(dispatcher) {
            songRemoteService.changeAvatar(id , file)
        }
    }

    suspend fun uploadMusic(id: Long, file: File): NetworkResult<SongResponseData> {
        return withContext(dispatcher) {
            songRemoteService.uploadMusic(id , file)
        }
    }

    suspend fun updateSong(updateSongRequest: UpdateSongRequest): NetworkResult<SongResponseData> {
        return withContext(dispatcher) {
            songRemoteService.updateSong(updateSongRequest)
        }
    }

    suspend fun deleteSong(id: Long): NetworkResult<MessageResponse> {
        return withContext(dispatcher) {
            songRemoteService.deleteSong(id)
        }
    }


}