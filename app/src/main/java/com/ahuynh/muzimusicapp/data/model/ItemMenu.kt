package com.ahuynh.muzimusicapp.data.model

import androidx.annotation.DrawableRes

data class ItemMenu(val title: String, @DrawableRes val drawableRes: Int, val type: ItemMenuName)

enum class ItemMenuName {
    LOVE, PLAYLIST, COPY, DELETE, EDIT, LOCK, LANGUAGE, VI, US
}