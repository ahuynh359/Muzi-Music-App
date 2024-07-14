package com.ahuynh.muzimusicapp.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ahuynh.muzimusicapp.data.database.entity.SearchHistoryEntity

@Dao
interface SearchHistoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(searchHistoryEntity: SearchHistoryEntity)

    @Query("SELECT * FROM SearchHistoryEntity ORDER BY time DESC LIMIT 10")
    suspend fun getAllSearchKeywordHistory() : List<SearchHistoryEntity>

    @Query("DELETE FROM SearchHistoryEntity")
    suspend fun deleteAll()
}