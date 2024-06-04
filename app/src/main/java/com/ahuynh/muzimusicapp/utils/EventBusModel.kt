package com.ahuynh.muzimusicapp.utils

import com.ahuynh.muzimusicapp.data.model.SongOld
import com.ahuynh.muzimusicapp.data_api.model.Song

class EventBusModel {
    //Update button play pause
    data class MusicPlayingEvent(val isPlaying: Boolean)

    //Update Playlist
    data class SongListEvent(val songOldList: ArrayList<Song>)

    //Update info for song
    data class SongInfoEvent(val songOld: Song?)
    data class AudioSessionIdEvent(val sessionId: Int)

    //Update seek bar with time
    data class MusicTimeSeekEvent(val timeMillis: Long)

    //Update text view duration
    data class MusicTimeEvent(val timeMillis: Long, val duration: Long)


    class RequestSongEvent()
    class ClearMusic()


}