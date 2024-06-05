package com.ahuynh.muzimusicapp.data.repository

import com.ahuynh.muzimusicapp.data.model.Type
import com.ahuynh.muzimusicapp.data.service.TypeService
import com.ahuynh.muzimusicapp.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TypeRepository @Inject constructor(
    private val typeService: TypeService,
    @IoDispatcher private val dispatcher: CoroutineDispatcher = Dispatchers.IO


) {

    suspend fun getAllType(): List<Type> {
        return withContext(dispatcher) {
            typeService.getAllType()
        }
    }


}