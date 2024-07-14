package com.ahuynh.muzimusicapp.data.repository

import com.ahuynh.muzimusicapp.data.model.Playlist
import com.ahuynh.muzimusicapp.data.model.Song
import com.ahuynh.muzimusicapp.data.model.request.PlaylistRequest
import com.ahuynh.muzimusicapp.data.model.response.MessageResponse
import com.ahuynh.muzimusicapp.data.model.response.PlaylistResponseJson
import com.ahuynh.muzimusicapp.data.service.remote.PlaylistRemoteService
import com.ahuynh.muzimusicapp.di.IoDispatcher
import com.ahuynh.muzimusicapp.utils.Response
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PlaylistRepository @Inject constructor(
    private val playlistRemoteService: PlaylistRemoteService,
    @IoDispatcher private val dispatcher: CoroutineDispatcher = Dispatchers.IO


) {

    suspend fun getAllPlaylist(): List<Playlist> {
        return withContext(dispatcher) {
            playlistRemoteService.getAllPlaylist()
        }
    }

    suspend fun addPlaylist(playlistRequest: PlaylistRequest) : Response<PlaylistResponseJson>{
        return withContext(dispatcher) {
            playlistRemoteService.addPlaylist(playlistRequest)
        }
    }

    suspend fun deletePlaylist(id: Long): Response<MessageResponse> {
        return withContext(dispatcher) {
            playlistRemoteService.deletePlaylist(id)
        }
    }

    suspend fun getAllSongFromPlaylist(id: Long): List<Song> {
        return withContext(dispatcher) {
            playlistRemoteService.getAllSongFromPlaylist(id)
        }
    }

    suspend fun getAllSongsNotFromPlaylist(id: Long): List<Song> {
        return withContext(dispatcher) {
            playlistRemoteService.getAllSongsNotFromPlaylist(id)
        }
    }

    suspend fun updatePlaylist(playlistRequest: PlaylistRequest, id: Long): Response<PlaylistResponseJson> {
        return withContext(dispatcher) {
            playlistRemoteService.updatePlaylist(playlistRequest,id)
        }
    }

    suspend fun addSongToPlaylist(playlistId: Long, songId: Long): Response<MessageResponse>{
        return withContext(dispatcher) {
            playlistRemoteService.addSongToPlaylist(playlistId,songId)
        }
    }


}