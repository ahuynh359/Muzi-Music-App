package com.ahuynh.muzimusicapp.data.repository

import android.os.Message
import com.ahuynh.muzimusicapp.data.model.Song
import com.ahuynh.muzimusicapp.data.model.Type
import com.ahuynh.muzimusicapp.data.model.request.AddUserRequest
import com.ahuynh.muzimusicapp.data.model.response.MessageResponse
import com.ahuynh.muzimusicapp.data.model.response.TypeResponseData
import com.ahuynh.muzimusicapp.data.model.response.UserResponseData
import com.ahuynh.muzimusicapp.data.service.remote.TypeService
import com.ahuynh.muzimusicapp.di.IoDispatcher
import com.ahuynh.muzimusicapp.utils.Response
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
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

    suspend fun getTypeById(id : Long): Type? {
        return withContext(dispatcher) {
            typeService.getTypeById(id)
        }
    }
    suspend fun getSongFromType(id: Long): List<Song> {
        return withContext(dispatcher) {
            typeService.getSongFromType(id)
        }
    }

    suspend fun createType(name: String, avatar: File): Response<TypeResponseData> {

        return withContext(dispatcher) {
            typeService.createType(name, avatar)
        }
    }

    suspend fun deleteType(id : Long): Response<MessageResponse> {

        return withContext(dispatcher) {
            typeService.deleteType(id)
        }
    }

    suspend fun updateType(id : Long , name: String, avatar: File): Response<TypeResponseData> {

        return withContext(dispatcher) {
            typeService.updateType(id , name, avatar)
        }
    }

}