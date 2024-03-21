package com.ahuynh.muzimusicapp.utils

import com.ahuynh.muzimusicapp.model.Song

class EventBusModel {

    //Update Playlist
    data class SongListEvent(val songList: ArrayList<Song>)

    //Update button play pause
    data class MusicPlayingEvent(val isPlaying: Boolean)

    //Update info for song
    data class SongInfoEvent(val song: Song?)
    data class AudioSessionIdEvent(val sessionId: Int)

    //Update seek bar with time
    data class MusicTimeSeekEvent(val timeMillis: Long)

    //Update text view duration
    data class MusicTimeEvent(val timeMillis: Long, val duration: Long)

    class RequestSongEvent()


}