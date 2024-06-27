package com.ahuynh.muzimusicapp.data.service.remote

import com.ahuynh.muzimusicapp.data.api.PlaylistAPI
import com.ahuynh.muzimusicapp.data.model.Playlist
import com.ahuynh.muzimusicapp.data.model.Song
import com.ahuynh.muzimusicapp.data.model.request.PlaylistRequest
import com.ahuynh.muzimusicapp.data.model.response.MessageResponse
import com.ahuynh.muzimusicapp.data.model.response.PlaylistResponseData
import com.ahuynh.muzimusicapp.data.model.response.toListSong
import com.ahuynh.muzimusicapp.data.model.response.toPlaylistResponse
import com.ahuynh.muzimusicapp.data.service.base.BaseRemoteService
import com.ahuynh.muzimusicapp.utils.Response
import javax.inject.Inject

class PlaylistService @Inject constructor(
    private val playlistAPI: PlaylistAPI
) : BaseRemoteService() {
    suspend fun getAllPlaylist(id : Long): List<Playlist> {
        val result = callApi { playlistAPI.getAllPlaylist(id) }
        return if (result is Response.Success) {
            result.data.data.toPlaylistResponse()
        } else {
            arrayListOf()
        }
    }

    suspend fun addPlaylist(playlistRequest: PlaylistRequest) : Response<PlaylistResponseData>{
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


    suspend fun deletePlaylist(id: Long) : Response<MessageResponse>{
        return callApi { playlistAPI.deletePlaylist(id) }
    }

}