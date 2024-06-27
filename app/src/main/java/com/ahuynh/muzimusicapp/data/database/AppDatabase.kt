package com.ahuynh.muzimusicapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.ahuynh.muzimusicapp.data.database.dao.SearchHistoryDAO
import com.ahuynh.muzimusicapp.data.database.entity.SearchHistoryEntity
import java.util.Date
@Database(
    entities = [
        SearchHistoryEntity::class,
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun searchHistoryDao(): SearchHistoryDAO
}

class Converters {

    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }
}