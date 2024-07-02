package com.ahuynh.muzimusicapp.data.model

import androidx.annotation.DrawableRes

data class UserList(
    val title : String,
    @DrawableRes val image : Int,
     val type: UserListName
)
enum class UserListName{
    PLAYLIST, LOVESONG, LOVESINGER
}