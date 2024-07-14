package com.ahuynh.muzimusicapp.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ahuynh.muzimusicapp.data.database.entity.AlbumEntity
import com.ahuynh.muzimusicapp.data.database.entity.SingerEntity
import com.ahuynh.muzimusicapp.data.database.entity.SongEntity

@Dao
interface AlbumDao {

    @Query("SELECT * FROM albumentity")
    suspend fun getAllAlbum(): List<AlbumEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAlbum(albumEntity: AlbumEntity)

    @Delete
    suspend fun deleteAlbum(albumEntity: AlbumEntity)
}