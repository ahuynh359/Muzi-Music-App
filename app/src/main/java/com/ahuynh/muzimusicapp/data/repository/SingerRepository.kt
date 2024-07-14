package com.ahuynh.muzimusicapp.data.repository

import com.ahuynh.muzimusicapp.data.model.Singer
import com.ahuynh.muzimusicapp.data.model.Song
import com.ahuynh.muzimusicapp.data.model.response.LoveSingerResponse
import com.ahuynh.muzimusicapp.data.model.response.MessageResponse
import com.ahuynh.muzimusicapp.data.service.remote.SingerRemoteService
import com.ahuynh.muzimusicapp.di.IoDispatcher
import com.ahuynh.muzimusicapp.utils.Response
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SingerRepository @Inject constructor(
    private val singerService: SingerRemoteService,
    @IoDispatcher private val dispatcher: CoroutineDispatcher = Dispatchers.IO


) {

    suspend fun getNewSingers(): List<Singer> {
        return withContext(dispatcher) {
            singerService.getNewSingers()
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

    suspend fun isUserLoveSinger(id : Long): Response<LoveSingerResponse> {
        return withContext(dispatcher) {
            singerService.isUserLoveSinger(id)
        }
    }

    suspend fun loveOrUnloveSinger(id: Long) : Response<MessageResponse> {
        return withContext(dispatcher) {
            singerService.loveOrUnloveSinger(id)
        }

    }
}
