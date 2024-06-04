package com.ahuynh.muzimusicapp.data_api.service

import com.ahuynh.muzimusicapp.data_api.api.AlbumAPI
import com.ahuynh.muzimusicapp.data_api.api.TypeAPI
import com.ahuynh.muzimusicapp.data_api.model.Album
import com.ahuynh.muzimusicapp.data_api.model.Song
import com.ahuynh.muzimusicapp.data_api.model.Type
import com.ahuynh.muzimusicapp.data_api.model.response.AlbumResponse
import com.ahuynh.muzimusicapp.data_api.model.response.toListAlbum
import com.ahuynh.muzimusicapp.data_api.model.response.toListSong
import com.ahuynh.muzimusicapp.data_api.model.response.toListType
import com.ahuynh.muzimusicapp.data_api.service.base.BaseRemoteService
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


}