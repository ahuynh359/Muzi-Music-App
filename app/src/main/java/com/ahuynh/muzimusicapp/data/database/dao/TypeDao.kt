package com.ahuynh.muzimusicapp.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ahuynh.muzimusicapp.data.database.entity.SongEntity
import com.ahuynh.muzimusicapp.data.database.entity.TypeEntity

@Dao
interface TypeDao {

    @Query("SELECT * FROM typeentity ")
    suspend fun getAllTypes(): List<TypeEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertType(typeEntity: TypeEntity)

    @Delete
    suspend fun deleteType(typeEntity: TypeEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertListType(types: List<TypeEntity>)
}