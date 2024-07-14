package com.ahuynh.muzimusicapp.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ahuynh.muzimusicapp.data.database.entity.SingerEntity
import com.ahuynh.muzimusicapp.data.database.entity.SongEntity

@Dao
interface SongDao {

    @Query("SELECT * FROM songentity ORDER BY listenAt DESC LIMIT 10")
    suspend fun getRecentSongs(): List<SongEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSong(song: SongEntity)

    @Query("DELETE FROM songentity")
    suspend fun deleteAll()


}