package com.ahuynh.muzimusicapp.data.model

import androidx.annotation.DrawableRes

data class ItemMenu(val title : String,  @DrawableRes val drawableRes : Int, val type : ItemMenuName)

enum class ItemMenuName{
    LOVE, PLAYLIST,ALBUM,SINGER,SHARE,REPLY,COPY,DELETE,EDIT,LOCK, UNLOVE
}