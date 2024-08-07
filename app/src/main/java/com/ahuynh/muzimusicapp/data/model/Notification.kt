package com.ahuynh.muzimusicapp.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Notification (
    val id : Long,
    val title : String,
    val content : String,
    val status : String,
    val type : String,
    val songId : Long,
    val commentId : Long ?= null,
    val time : String,
    val user : User,


) : Parcelable {

}
