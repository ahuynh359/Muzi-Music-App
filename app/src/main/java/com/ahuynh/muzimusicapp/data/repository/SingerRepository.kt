package com.ahuynh.muzimusicapp.data.repository

import com.ahuynh.muzimusicapp.data.model.Singer
import com.ahuynh.muzimusicapp.data.model.Song
import com.ahuynh.muzimusicapp.data.model.request.UpdateSingerRequest
import com.ahuynh.muzimusicapp.data.model.response.LoveSingerResponse
import com.ahuynh.muzimusicapp.data.model.response.MessageResponse
import com.ahuynh.muzimusicapp.data.model.response.SingerResponseData
import com.ahuynh.muzimusicapp.data.service.remote.SingerRemoteService
import com.ahuynh.muzimusicapp.di.IoDispatcher
import com.ahuynh.muzimusicapp.ui.base.bottom_sheet.SortName
import com.ahuynh.muzimusicapp.utils.NetworkResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import javax.inject.Inject

class SingerRepository @Inject constructor(
    private val singerService: SingerRemoteService,
    @IoDispatcher private val dispatcher: CoroutineDispatcher = Dispatchers.IO


) {

    suspend fun getAllSingers(sortName: SortName): List<Singer> {
        return withContext(dispatcher) {
            singerService.getAllSingers(sortName)
        }
    }



    suspend fun getSongsOfSinger(id : Long): List<Song> {
        return withContext(dispatcher) {
            singerService.getSongsOfSinger(id)
        }
    }

    suspend fun getLoveSinger(): List<Singer> {
        return withContext(dispatcher) {
            singerService.getLoveSinger()
        }
    }

    suspend fun isUserLoveSinger(id : Long): NetworkResult<LoveSingerResponse> {
        return withContext(dispatcher) {
            singerService.isUserLoveSinger(id)
        }
    }

    suspend fun loveOrUnloveSinger(id: Long) : NetworkResult<MessageResponse> {
        return withContext(dispatcher) {
            singerService.loveOrUnloveSinger(id)
        }

    }

    suspend fun createSinger(name: String, avatar: File): NetworkResult<SingerResponseData> {
        return withContext(dispatcher) {
            singerService.createSinger(name,avatar)
        }
    }




    suspend fun deleteSinger(id : Long): NetworkResult<MessageResponse> {

        return withContext(dispatcher) {
            singerService.deleteSinger(id)
        }
    }

    suspend fun updateSinger(updateSingerRequest: UpdateSingerRequest): NetworkResult<SingerResponseData> {

        return withContext(dispatcher) {
            singerService.updateSinger(updateSingerRequest)
        }
    }

    suspend fun changeAvatar(id: Long, file: File): NetworkResult<SingerResponseData> {
        return withContext(dispatcher) {
            singerService.changeAvatar(id , file)
        }
    }

    suspend fun getSingerById(id: Long): Singer? {
        return withContext(dispatcher) {
            singerService.getSingerById(id)
        }

    }

}
