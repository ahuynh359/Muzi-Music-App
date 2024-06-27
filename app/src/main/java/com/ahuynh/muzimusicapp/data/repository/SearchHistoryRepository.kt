package com.ahuynh.muzimusicapp.data.repository

import com.ahuynh.muzimusicapp.data.database.entity.SearchHistoryEntity
import com.ahuynh.muzimusicapp.data.service.local.SearchHistoryService
import com.ahuynh.muzimusicapp.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SearchHistoryRepository @Inject constructor(
    private val localService: SearchHistoryService,
    @IoDispatcher private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {

    suspend fun getAllSearchHistory(): List<SearchHistoryEntity> {
        return withContext(dispatcher) {
            localService.getAllSearchHistory()
        }
    }

    suspend fun insert(searchHistoryEntity: SearchHistoryEntity) {
        withContext(dispatcher) {
            localService.insert(searchHistoryEntity)
        }
    }

    suspend fun deleteAll() {
        withContext(dispatcher) {
            localService.deleteAll()
        }
    }
}