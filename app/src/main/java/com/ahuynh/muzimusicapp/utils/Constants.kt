package com.ahuynh.muzimusicapp.utils

import androidx.lifecycle.MutableLiveData
import com.ahuynh.muzimusicapp.data.model.Notification
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
    const val NOTIFICATION = "notification"





    const val NOTIFICATION_ID = 101

    const val PERMISSION_REQUEST_ID = 1


    const val SONG_LIST = "song list"
    const val DATA = "data"
    const val ACTION = "action"

    var IS_SHUFFLE = false
    var IS_REPEAT = false
    var INTENT_ACTION = "intent_service"

    var SONG_LIST_DATA : List<Song> = listOf()
     var CURRENT_NOTI : Notification ?= null
    const val SERVER_KEY =
        "AAAAckRVZ2U:APA91bF8V7ubgXC3LbRs7nZhCDx_cZwhLwiWr4yu73bhHMyUxqe5t8imATGR1loHTUAJ5WcxMpnLniO-Y3WUrG-VzN1nS2CCnDp55v8siIxby3deZgmHi6NVkHWC5bgZ5xgdicKvuaEW"
    const val TOPIC = "music"
    var FCM_KEY = ""
    var TOKEN_13 = "diBIkAYPSnSS_SEbQqNgun:APA91bH_BjpNpVLHO62bhYCquB4m_xaoT7URuLLNEq_8FEcrFJJqXL_t1Bz9qC1aWaV_MQjtVdOA-NOEaVmCGOwty8E_envmRjaNvdcGUH5I25pFqpg-NQA506gwOBlOd9W6R_EkvAFM"
    var TOKEN_14 = "chFH3YaxRzOFKqvnq4Wp8N:APA91bGSQakR8PEqrWm-sPZ_8QT9q-XWcNhGXHPqJhz9X9lVXXXWpnsT-z3JgiBeEQnmkBCkFrrHPYoC4V9uJbOxx_t3kNWwG3FbuXoLQ_-aDRKuzHVo1FGAF44NyjLgX4ffE5qHv9s5"
    var updateNoti = MutableLiveData<Boolean>(false)

    var ACCESS_TOKEN = ""
    const val BASE_URL = "http://192.168.239.210:8080"
    const val API_VERSION = "/api/v1"

}