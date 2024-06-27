package com.ahuynh.muzimusicapp.data.model

data class SettingItem(val name : SettingName)

enum class SettingName{
    LANGUAGE, THEME, SECURITY
}