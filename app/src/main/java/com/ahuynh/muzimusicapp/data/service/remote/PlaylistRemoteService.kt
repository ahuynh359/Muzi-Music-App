package com.ahuynh.muzimusicapp.data.service.remote

import com.ahuynh.muzimusicapp.data.api.PlaylistAPI
import com.ahuynh.muzimusicapp.data.model.Playlist
import com.ahuynh.muzimusicapp.data.model.Song
import com.ahuynh.muzimusicapp.data.model.request.PlaylistRequest
import com.ahuynh.muzimusicapp.data.model.response.MessageResponse
import com.ahuynh.muzimusicapp.data.model.response.PlaylistResponseJson
import com.ahuynh.muzimusicapp.data.model.response.toListSong
import com.ahuynh.muzimusicapp.data.model.response.toPlaylistResponse
import com.ahuynh.muzimusicapp.data.service.base.BaseRemoteService
import com.ahuynh.muzimusicapp.utils.Response
import javax.inject.Inject

class PlaylistRemoteService @Inject constructor(
    private val playlistAPI: PlaylistAPI
) : BaseRemoteService() {
    suspend fun getAllPlaylist(): List<Playlist> {
        val result = callApi { playlistAPI.getAllPlaylist() }
        return if (result is Response.Success) {
            result.data.data.toPlaylistResponse()
        } else {
            arrayListOf()
        }
    }

    suspend fun addPlaylist(playlistRequest: PlaylistRequest): Response<PlaylistResponseJson> {
        return callApi { playlistAPI.addPlaylist(playlistRequest) }
    }

    suspend fun getAllSongFromPlaylist(id: Long): List<Song> {
        val result = callApi { playlistAPI.getAllSongFromPlaylist(id) }
        return if (result is Response.Success) {
            result.data.data.toListSong()
        } else {
            arrayListOf()
        }
    }

    suspend fun getAllSongsNotFromPlaylist(id: Long): List<Song> {
        val result = callApi { playlistAPI.getAllSongsNotFromPlaylist(id) }
        return if (result is Response.Success) {
            result.data.data.toListSong()
        } else {
            arrayListOf()
        }
    }


    suspend fun deletePlaylist(id: Long): Response<MessageResponse> {
        return callApi { playlistAPI.deletePlaylist(id) }
    }

    suspend fun updatePlaylist(
        playlistRequest: PlaylistRequest,
        id: Long
    ): Response<PlaylistResponseJson> {
        return callApi { playlistAPI.updatePlaylist(playlistRequest, id) }

    }

    suspend fun addSongToPlaylist(playlistId: Long, songId: Long): Response<MessageResponse> {
        return callApi { playlistAPI.addSongToPlaylist(playlistId, songId) }
    }

}