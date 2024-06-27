package com.ahuynh.muzimusicapp.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity
class SearchHistoryEntity(
    @PrimaryKey
    val keyword: String,
    val time: Date ?= null
)