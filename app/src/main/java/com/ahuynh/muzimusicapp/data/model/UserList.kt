package com.ahuynh.muzimusicapp.data.model

import androidx.annotation.DrawableRes

data class UserList(
    @DrawableRes val image : Int,
     val name: UserListName
)
enum class UserListName{
    PLAYLIST, LOVESONG, LOVESINGER
}