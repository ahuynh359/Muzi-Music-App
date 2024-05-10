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

    const val BASE_URL = "https://fcm.googleapis.com"
    const val SERVER_KEY =
        "AAAAckRVZ2U:APA91bEgDSDnglbcJbHO5Kok0KTgGGjlrocBtdSdhRwkEteU2zWcz6PU7zBbRZ6iFZG8hLetLPnr5UDhKYvIKMq_8QBqmq2WEs4S36dTrOGskCKXgamXtxaDdVsFYH4U9LCfrN8AYRLm"
    const val CONTENT_TYPE="application/json"
    const val TOPIC = "myTopic"
    var FCM_KEY = ""


}