package com.ahuynh.muzimusicapp.data.repository

import com.ahuynh.muzimusicapp.data.model.Playlist
import com.ahuynh.muzimusicapp.data.model.request.PlaylistRequest
import com.ahuynh.muzimusicapp.data.model.response.ApiResponse
import com.ahuynh.muzimusicapp.data.service.PlaylistService
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

    suspend fun getAllPlaylist(id : Long): List<Playlist> {
        return withContext(dispatcher) {
            playlistService.getAllPlaylist(id)
        }
    }

    suspend fun addPlaylist(playlistRequest: PlaylistRequest) : Response<ApiResponse>{
        return withContext(dispatcher) {
            playlistService.addPlaylist(playlistRequest)
        }
    }

    suspend fun deletePlaylist(id: Long): Response<ApiResponse> {
        return withContext(dispatcher) {
            playlistService.deletePlaylist(id)
        }
    }


}