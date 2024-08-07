package com.ahuynh.muzimusicapp.data.model.response

import com.ahuynh.muzimusicapp.data.model.Song

data class ListenOfDayResponse(
    val day: String,
    val listen: Int
)

data class SongListen(
    val song : SongResponse,
    val listenDetail : List<ListenOfDayResponse>
){
    fun toSong() : Song {
        return song.toSong()
    }
}

data class SongListenResponseDataList(
    val message : String,
    val data : List<SongListen>

)