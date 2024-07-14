package com.ahuynh.muzimusicapp.data.repository

import com.ahuynh.muzimusicapp.data.model.Song
import com.ahuynh.muzimusicapp.data.model.Type
import com.ahuynh.muzimusicapp.data.model.response.MessageResponse
import com.ahuynh.muzimusicapp.data.model.response.TypeResponseData
import com.ahuynh.muzimusicapp.data.service.remote.TypeRemoteService
import com.ahuynh.muzimusicapp.di.IoDispatcher
import com.ahuynh.muzimusicapp.utils.Response
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import javax.inject.Inject

class TypeRepository @Inject constructor(
    private val typeRemoteService: TypeRemoteService,
    @IoDispatcher private val dispatcher: CoroutineDispatcher = Dispatchers.IO


) {

    suspend fun getAllType(): List<Type> {
        return withContext(dispatcher) {
            typeRemoteService.getAllType()
        }
    }

    suspend fun getTypeById(id : Long): Type? {
        return withContext(dispatcher) {
            typeRemoteService.getTypeById(id)
        }
    }
    suspend fun getSongFromType(id: Long): List<Song> {
        return withContext(dispatcher) {
            typeRemoteService.getSongFromType(id)
        }
    }

    suspend fun createType(name: String, avatar: File): Response<TypeResponseData> {

        return withContext(dispatcher) {
            typeRemoteService.createType(name, avatar)
        }
    }

    suspend fun deleteType(id : Long): Response<MessageResponse> {

        return withContext(dispatcher) {
            typeRemoteService.deleteType(id)
        }
    }

    suspend fun updateType(id : Long , name: String, avatar: File): Response<TypeResponseData> {

        return withContext(dispatcher) {
            typeRemoteService.updateType(id , name, avatar)
        }
    }

}