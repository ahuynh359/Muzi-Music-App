package com.ahuynh.muzimusicapp.data.service

import com.ahuynh.muzimusicapp.data.api.TypeAPI
import com.ahuynh.muzimusicapp.data.model.Song
import com.ahuynh.muzimusicapp.data.model.Type
import com.ahuynh.muzimusicapp.data.model.response.toListSong
import com.ahuynh.muzimusicapp.data.model.response.toListType
import com.ahuynh.muzimusicapp.data.service.base.BaseRemoteService
import com.ahuynh.muzimusicapp.utils.Response
import javax.inject.Inject


class TypeService @Inject constructor(
    private val typeAPI: TypeAPI
) : BaseRemoteService() {
    suspend fun getAllType(): List<Type> {
        val result = callApi { typeAPI.getAllType() }
        return if (result is Response.Success) {
            result.data.data.toListType()
        } else {
            emptyList()
        }
    }
    suspend fun getSongFromType(id : Long): List<Song> {
        val result = callApi { typeAPI.getSongFromType(id) }
        return if (result is Response.Success) {
            result.data.data.toListSong()
        } else {
            emptyList()
        }
    }


}