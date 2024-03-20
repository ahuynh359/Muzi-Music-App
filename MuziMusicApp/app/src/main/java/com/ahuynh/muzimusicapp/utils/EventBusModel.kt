package com.ahuynh.muzimusicapp.utils

import com.ahuynh.muzimusicapp.model.Song

class EventBusModel {

    data class SongListEvent(val songList: ArrayList<Song>)

    data class MusicPlayingEvent(val isPlaying: Boolean)
    data class SongInfoEvent(val song: Song?)
    data class AudioSessionIdEvent(val sessionId: Int)
    class RequestSongEvent()


}