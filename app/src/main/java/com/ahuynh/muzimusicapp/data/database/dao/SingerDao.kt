package com.ahuynh.muzimusicapp.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ahuynh.muzimusicapp.data.database.entity.SingerEntity
import com.ahuynh.muzimusicapp.data.database.entity.TypeEntity

@Dao
interface SingerDao {

    @Query("SELECT * FROM singerentity ")
    suspend fun getAllSingers(): List<SingerEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSinger(singerEntity: SingerEntity)

    @Delete
    suspend fun deleteSinger(singerEntity: SingerEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertListSinger(singers: List<SingerEntity>)
}