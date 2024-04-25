package com.ahuynh.muzimusicapp.utils

import com.ahuynh.muzimusicapp.data.model.Song

object Constants {
    enum class SortingOrder {
        ASCENDING,
        DESCENDING
    }

    //Property in firebase
    const val NAME  = "name"
    const val SONG = "song"
    const val PLAYLIST = "playlist"





    const val NOTIFICATION_ID = 101

    const val PERMISSION_REQUEST_ID = 1


    const val SONG_LIST = "song list"
    const val DATA = "data"
    const val ACTION = "action"

    var IS_SHUFFLE = false
    var IS_REPEAT = false
    var INTENT_ACTION = "intent_service"

    var SONG_LIST_DATA : List<Song> = listOf()



}