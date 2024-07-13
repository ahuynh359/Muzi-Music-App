package com.ahuynh.muzimusicapp.data.repository

import com.ahuynh.muzimusicapp.data.model.Album
import com.ahuynh.muzimusicapp.data.model.Song
import com.ahuynh.muzimusicapp.data.model.request.UpdateAlbumRequest
import com.ahuynh.muzimusicapp.data.model.response.AlbumResponse
import com.ahuynh.muzimusicapp.data.model.response.AlbumResponseData
import com.ahuynh.muzimusicapp.data.model.response.MessageResponse
import com.ahuynh.muzimusicapp.data.model.response.TypeResponseData
import com.ahuynh.muzimusicapp.data.service.remote.AlbumService
import com.ahuynh.muzimusicapp.di.IoDispatcher
import com.ahuynh.muzimusicapp.utils.Response
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import javax.inject.Inject

class AlbumRepository @Inject constructor(
    private val albumService: AlbumService,
    @IoDispatcher private val dispatcher: CoroutineDispatcher = Dispatchers.IO


) {

    suspend fun getNewAlbums(): List<Album> {
        return withContext(dispatcher) {
            albumService.getNewAlbums()
        }
    }

    suspend fun getAlbumById(id: Long): Album? {
        return withContext(dispatcher) {
            albumService.getAlbumById(id)
        }
    }

    suspend fun getSongsFromAlbum(id: Long): List<Song> {
        return withContext(dispatcher) {
            albumService.getSongsFromAlbum(id)
        }
    }

    suspend fun createAlbum(name: String, avatar: File): Response<AlbumResponseData> {

        return withContext(dispatcher) {
            albumService.createAlbum(name, avatar)
        }
    }

    suspend fun deleteAlbum(id: Long): Response<MessageResponse> {

        return withContext(dispatcher) {
            albumService.deleteAlbum(id)
        }
    }

    suspend fun updateAlbum(updateAlbumRequest: UpdateAlbumRequest): Response<AlbumResponseData> {

        return withContext(dispatcher) {
            albumService.updateAlbum(updateAlbumRequest)
        }
    }

    suspend fun updateAvatar(id: Long, avatar: File): Response<AlbumResponseData> {

        return withContext(dispatcher) {
            albumService.updateAvatar(id, avatar)
        }
    }


}