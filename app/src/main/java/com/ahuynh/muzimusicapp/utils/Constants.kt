package com.ahuynh.muzimusicapp.utils

import android.graphics.Color


object Constants {


    val SINGER: String = "singer"
    val ALBUM: String = "album"
    val colorsTopSong = listOf(
        Color.rgb(47,148,240),
        Color.rgb(56,202,147),
        Color.rgb(227,121,68)
    )
    const val TYPE: String = "type"
    const val COMMENT: String = "comment"
    const val SONG_ID: String = "song_id"
    const val COMMENT_ID: String = "comment_id"
    const val NOTIFICATION: String = "notification"
    const val NOTIFICATION_ID = 101
    const val PERMISSION_REQUEST_ID = 1

    const val SONG = "song"
    const val SONG_LIST = "song_list"
    const val DATA = "data"
    const val ACTION = "action"

    var IS_SHUFFLE = false
    var IS_REPEAT = false
    var INTENT_ACTION = "intent_service"

    const val BASE_URL = "http://192.168.101.15:8080"
    const val API_VERSION = "/api/v1"



}