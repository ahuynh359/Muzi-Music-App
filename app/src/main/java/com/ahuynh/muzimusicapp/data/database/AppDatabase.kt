package com.ahuynh.muzimusicapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.ahuynh.muzimusicapp.data.database.dao.AlbumDao
import com.ahuynh.muzimusicapp.data.database.dao.SearchHistoryDao
import com.ahuynh.muzimusicapp.data.database.dao.SingerDao
import com.ahuynh.muzimusicapp.data.database.dao.SongDao
import com.ahuynh.muzimusicapp.data.database.dao.TypeDao
import com.ahuynh.muzimusicapp.data.database.entity.AlbumEntity
import com.ahuynh.muzimusicapp.data.database.entity.SearchHistoryEntity
import com.ahuynh.muzimusicapp.data.database.entity.SingerEntity
import com.ahuynh.muzimusicapp.data.database.entity.SongEntity
import com.ahuynh.muzimusicapp.data.database.entity.TypeEntity
import java.util.Date

@Database(
    entities = [
        SearchHistoryEntity::class,
        AlbumEntity::class,
        SingerEntity::class,
        SongEntity::class,
        TypeEntity::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun searchHistoryDao(): SearchHistoryDao
    abstract fun songDao(): SongDao
    abstract fun singerDao(): SingerDao
    abstract fun typeDao(): TypeDao
    abstract fun albumDao(): AlbumDao


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