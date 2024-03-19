package com.ahuynh.muzimusicapp.utils

import com.ahuynh.muzimusicapp.model.Song

class EventBusModel {
    data class SongListEvent(val songList: ArrayList<Song>)
}