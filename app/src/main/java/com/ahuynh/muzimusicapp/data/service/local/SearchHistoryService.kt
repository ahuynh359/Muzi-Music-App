package com.ahuynh.muzimusicapp.data.service.local

import com.ahuynh.muzimusicapp.data.database.dao.SearchHistoryDAO
import com.ahuynh.muzimusicapp.data.database.entity.SearchHistoryEntity
import javax.inject.Inject

class SearchHistoryService @Inject constructor(
    private val searchHistoryDAO: SearchHistoryDAO
) {

    suspend fun getAllSearchHistory(): List<SearchHistoryEntity> {
        return searchHistoryDAO.getAllSearchKeywordHistory()
    }


    suspend fun insert(searchHistoryEntity: SearchHistoryEntity) {
        return searchHistoryDAO.insert(searchHistoryEntity)
    }

    suspend fun deleteAll() {
        searchHistoryDAO.deleteAll()
    }

}