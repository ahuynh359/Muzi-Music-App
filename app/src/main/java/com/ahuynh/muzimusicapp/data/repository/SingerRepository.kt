package com.ahuynh.muzimusicapp.data.repository

import com.ahuynh.muzimusicapp.data.model.Singer
import com.ahuynh.muzimusicapp.data.service.SingerService
import com.ahuynh.muzimusicapp.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SingerRepository @Inject constructor(
    private val singerService: SingerService,
    @IoDispatcher private val dispatcher: CoroutineDispatcher = Dispatchers.IO


) {

    suspend fun getNewSingers(): List<Singer> {
        return withContext(dispatcher) {
            singerService.getNewSingers()
        }
    }
}
