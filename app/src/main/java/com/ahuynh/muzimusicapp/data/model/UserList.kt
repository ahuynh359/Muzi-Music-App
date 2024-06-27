package com.ahuynh.muzimusicapp.data.model

data class UserList(
     val name: UserListName
)
enum class UserListName{
    PLAYLIST, LOVESONG, LOVESINGER
}