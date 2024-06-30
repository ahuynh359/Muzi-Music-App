package com.ahuynh.muzimusicapp.data.repository

import com.ahuynh.muzimusicapp.data.model.Playlist
import com.ahuynh.muzimusicapp.data.model.Song
import com.ahuynh.muzimusicapp.data.model.request.PlaylistRequest
import com.ahuynh.muzimusicapp.data.model.response.MessageResponse
import com.ahuynh.muzimusicapp.data.model.response.PlaylistResponseData
import com.ahuynh.muzimusicapp.data.model.response.PlaylistResponseJson
import com.ahuynh.muzimusicapp.data.service.remote.PlaylistService
import com.ahuynh.muzimusicapp.di.IoDispatcher
import com.ahuynh.muzimusicapp.utils.Response
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PlaylistRepository @Inject constructor(
    private val playlistService: PlaylistService,
    @IoDispatcher private val dispatcher: CoroutineDispatcher = Dispatchers.IO


) {

    suspend fun getAllPlaylist(): List<Playlist> {
        return withContext(dispatcher) {
            playlistService.getAllPlaylist()
        }
    }

    suspend fun addPlaylist(playlistRequest: PlaylistRequest) : Response<PlaylistResponseJson>{
        return withContext(dispatcher) {
            playlistService.addPlaylist(playlistRequest)
        }
    }

    suspend fun deletePlaylist(id: Long): Response<MessageResponse> {
        return withContext(dispatcher) {
            playlistService.deletePlaylist(id)
        }
    }

    suspend fun getAllSongFromPlaylist(id: Long): List<Song> {
        return withContext(dispatcher) {
            playlistService.getAllSongFromPlaylist(id)
        }
    }

    suspend fun updatePlaylist(playlistRequest: PlaylistRequest, id: Long): Response<PlaylistResponseJson> {
        return withContext(dispatcher) {
            playlistService.updatePlaylist(playlistRequest,id)
        }
    }


}