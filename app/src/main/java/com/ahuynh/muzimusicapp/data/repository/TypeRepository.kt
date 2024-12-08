package com.ahuynh.muzimusicapp.data.repository

import com.ahuynh.muzimusicapp.data.model.Song
import com.ahuynh.muzimusicapp.data.model.Type
import com.ahuynh.muzimusicapp.data.model.request.UpdateTypeRequest
import com.ahuynh.muzimusicapp.data.model.response.MessageResponse
import com.ahuynh.muzimusicapp.data.model.response.TypeResponseData
import com.ahuynh.muzimusicapp.data.service.remote.TypeRemoteService
import com.ahuynh.muzimusicapp.di.IoDispatcher
import com.ahuynh.muzimusicapp.ui.base.bottom_sheet.SortName
import com.ahuynh.muzimusicapp.utils.NetworkResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import javax.inject.Inject

class TypeRepository @Inject constructor(
    private val typeRemoteService: TypeRemoteService,
    @IoDispatcher private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {

    suspend fun getAllTypes(sortName: SortName, page : Int, size : Int): List<Type> {
        return withContext(dispatcher) {
            typeRemoteService.getAllTypes(sortName, page, size)
        }
    }

    suspend fun getTypeById(id: Long): Type? {
        return withContext(dispatcher) {
            typeRemoteService.getTypeById(id)
        }
    }

    suspend fun getSongFromType(id: Long): List<Song> {
        return withContext(dispatcher) {
            typeRemoteService.getSongFromType(id)
        }
    }

    suspend fun createType(name: String, avatar: File): NetworkResult<TypeResponseData> {

        return withContext(dispatcher) {
            typeRemoteService.createType(name, avatar)
        }
    }

    suspend fun deleteType(id: Long): NetworkResult<MessageResponse> {

        return withContext(dispatcher) {
            typeRemoteService.deleteType(id)
        }
    }

    suspend fun updateType(updateTypeRequest: UpdateTypeRequest): NetworkResult<TypeResponseData> {

        return withContext(dispatcher) {
            typeRemoteService.updateType(updateTypeRequest)
        }
    }

    suspend fun changeAvatar(id: Long, file: File): NetworkResult<TypeResponseData> {
        return withContext(dispatcher) {
            typeRemoteService.changeAvatar(id, file)
        }
    }

}